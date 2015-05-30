package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOProductes;
import org.projectes.androidshopping.DAO.DAOTags;
import org.projectes.androidshopping.DAO.DAOWS_Data;
import org.projectes.androidshopping.DAObject.Producte;
import org.projectes.androidshopping.DAObject.Tag;
import org.projectes.androidshopping.DAObject.WS_Data;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;
import org.projectes.androidshopping.WS.JacksonJSONHelper;
import org.projectes.androidshopping.WS.WSConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mrr on 29/04/15.
 * AsyncTask encarregada de recuperar la informació del WS
 */
public class WSTask extends AsyncTask<Object, Integer, Message> {
    private Message Mess;
    private Context context;
    private IResult<Message> listener;
    private ObjectMapper mapper;
    private JsonNode jsonTree;
    private List<Producte> productes;
    private long unixTimeUpdate;
    private int flag;

    @Override
    protected Message doInBackground(Object... params) {
        WSConnector WS = new WSConnector();
        this.context = (Context)params[Constants.POSICIO_CONTEXT_WSTask];
        this.Mess = null;


        //Primer és necessari consultar si s'ha modificat el contingut
        try{

            //Obtenim la data de LastUpdate del WS
            this.Mess = WS.get(Constants.URL_UPDATE, null);
            this.mapper = JacksonJSONHelper.Initialize();
            this.jsonTree = this.mapper.readTree((String)this.Mess.obj);
            this.unixTimeUpdate = getUnixTimestamp(this.jsonTree.get("last_update").textValue());

            //Recuperar dades BBDD
            DAOWS_Data DBTime = new DAOWS_Data(this.context);
            WS_Data updateTime = DBTime.selectBynomTaula("producte");

            //Comprovem si cal fer actualització
            if (updateTime != null){
                this.flag = 1;
                if(this.unixTimeUpdate > updateTime.getDate()){
                    this.flag = 2;
                    getProductWS();
                    updateTime.setDate(this.unixTimeUpdate);
                    DBTime.update(updateTime);
                    actualitzarProductes();
                }
            }else{
                this.flag = 2;
                updateTime = new WS_Data(this.unixTimeUpdate, "producte");
                DBTime.insert(updateTime);
                getProductWS();
                insertAllProducts();
            }

        } catch (IOException e){
            e.printStackTrace();

        }
        return this.Mess;
    }

    private void actualitzarProductes() {
        DAOProductes BBDDProductes = new DAOProductes(this.context);
        DAOTags BBDDTag = new DAOTags(this.context);
        long id_product = -1;
        List<Producte> productesDB = BBDDProductes.selectAll();
        boolean nTrobat = false;
        for (int i = 0; i<this.productes.size(); i++){
            Producte aux = BBDDProductes.selectByRemoteID(this.productes.get(i).getId_remot());
            if (aux != null){
                Producte update = this.productes.get(i);
                update.setId(aux.getId());
                update.setDeleted(0);
                BBDDProductes.update(update);
                //Borrar els tags del producte
                BBDDTag.deleteProducte_TagByIDProduct(aux.getId());
                insertAllTags(BBDDTag, aux.getId(), i);
            }else{
                id_product = BBDDProductes.insert(this.productes.get(i));
                insertAllTags(BBDDTag, id_product, i);
            }
        }
        for (int i = 0; i < productesDB.size(); i++){
            nTrobat = productesDB.get(i).getId_remot() < 0;
            for (int j = 0; j < this.productes.size() && !nTrobat; j++){
                if (productesDB.get(i).getId_remot() == this.productes.get(j).getId_remot()){
                    nTrobat = true;
                }
            }
            if (!nTrobat){
                BBDDProductes.deleteById(productesDB.get(i).getId());
                BBDDTag.deleteProducte_TagByIDProduct(productesDB.get(i).getId());
            }
        }
    }

    private void insertAllProducts() {
        DAOProductes BBDDProductes = new DAOProductes(this.context);
        DAOTags BBDDTAG = new DAOTags(this.context);
        long id_product = -1;
        for (int i = 0; i < this.productes.size(); i++){
            id_product = BBDDProductes.insert(this.productes.get(i));
            insertAllTags(BBDDTAG, id_product, i);
        }
    }

    private void insertAllTags(DAOTags BBDDTAG, long id_producte, int indexProduct){
        int i = indexProduct;
        for (int j = 0; j <this.productes.get(i).getWS_tags().size(); j++){
            //Comprovar si existeix
            Tag tagActual = BBDDTAG.selectByName(this.productes.get(i).getWS_tags().get(j));
            if (tagActual == null){
                tagActual = new Tag();
                tagActual.setNom(this.productes.get(i).getWS_tags().get(j));
                tagActual.setId((int)BBDDTAG.insertTag(tagActual));
                Log.d("TAG INSERT:", tagActual.getNom());
            }
            BBDDTAG.insertProducte_Tag(tagActual.getId(), (int)id_producte);
        }
    }

    public void onPostExecute(Message message){
        if (this.listener != null){
            if (message != null && message.what == 200){
                listener.onSuccess(message);
                Log.d("UNIX TIME:", Long.toString(this.unixTimeUpdate));
            }else{
                listener.onFail(this.context.getString(R.string.errorHTTP));
            }
        }
    }

    public void setResultListener(IResult<Message> listener){
        this.listener = listener;
    }

    private long getUnixTimestamp(String lastUpdate){
        try{
            DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = dFormat.parse(lastUpdate);
            return date.getTime()/1000;
        }catch(java.text.ParseException en){
            return 0;
        }
    }

    private void getProductWS(){
        WSConnector WS = new WSConnector();
        try {
            this.Mess = WS.get(Constants.URL_PRODUCTES, null);
            this.mapper = JacksonJSONHelper.Initialize();
            this.productes = this.mapper.readValue(
                    (String)this.Mess.obj,
                    mapper.getTypeFactory().constructCollectionType(
                            List.class, Producte.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}