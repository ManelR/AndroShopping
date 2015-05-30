package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOWS_Data;
import org.projectes.androidshopping.DAObject.Product;
import org.projectes.androidshopping.DAObject.WS_Data;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.WS.JacksonJSONHelper;
import org.projectes.androidshopping.WS.WSConnector;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
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
    private List<Product> productes;
    private JsonNode jsonTree;
    private Date date;
    private DateFormat formatter;
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
                //TODO Comparar si s'ha d'actualitzar
                this.flag = 1;
                if(this.unixTimeUpdate > updateTime.getDate()){
                    //TODO Actualitzar productes i data
                }
            }else{
                //TODO no hi ha data anterior, cal guardar.
                this.flag = 2;
                updateTime = new WS_Data(this.unixTimeUpdate, "producte");
                DBTime.insertWS_Data(updateTime);

            }

        } catch (IOException e){
            e.printStackTrace();

        }

        try {
            this.Mess = WS.get((String)params[Constants.POSICIO_URL_WSTask], null);
            this.mapper = JacksonJSONHelper.Initialize();
            this.productes = this.mapper.readValue(
                    (String)this.Mess.obj,
                    mapper.getTypeFactory().constructCollectionType(
                            List.class, Product.class));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.Mess;
    }

    public void onPostExecute(Message message){
        if (this.listener != null){
            if (message != null && message.what == 200){
                listener.onSuccess(message);
                //Toast.makeText(this.context, productes.get(2).getNombre(), Toast.LENGTH_LONG).show();
                //Toast.makeText(this.context, this.jsonTree.get("last_update").textValue(), Toast.LENGTH_LONG).show();
                Log.d("UNIX TIME:", Long.toString(this.unixTimeUpdate));
                Toast.makeText(this.context, Integer.toString(this.flag), Toast.LENGTH_LONG).show();
            }else{
                listener.onFail("Error HTTP");
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
}