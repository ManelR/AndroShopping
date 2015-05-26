package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAObject.Product;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.WS.JacksonJSONHelper;
import org.projectes.androidshopping.WS.WSConnector;

import java.io.IOException;
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

    @Override
    protected Message doInBackground(Object... params) {
        WSConnector WS = new WSConnector();
        this.context = (Context)params[Constants.POSICIO_CONTEXT_WSTask];
        this.Mess = null;
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
            if (message.what == 200){
                //listener.onSuccess(message);
                Toast.makeText(this.context, productes.get(2).getNombre(), Toast.LENGTH_LONG).show();
            }else{
                listener.onFail("Error HTTP");
            }
        }
    }

    public void setResultListener(IResult<Message> listener){
        this.listener = listener;
    }
}