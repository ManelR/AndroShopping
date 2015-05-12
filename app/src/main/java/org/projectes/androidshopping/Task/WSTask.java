package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.projectes.androidshopping.DAObject.Product;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.WS.WSConnector;

import java.io.IOException;

/**
 * Created by mrr on 29/04/15.
 */
public class WSTask extends AsyncTask<Object, Integer, Message> {
    private Message Mess;
    private Context context;
    private ObjectMapper objMapper;
    private Product result;
    private IResult<Message> listener;

    @Override
    protected Message doInBackground(Object... params) {
        WSConnector WS = new WSConnector();
        this.context = (Context)params[0];
        this.Mess = null;
        try {
            Mess = WS.get((String)params[1], null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Mess;
    }

    public void onPostExecute(Message message){
        if (this.listener != null){
            if (message.what == 200){
                listener.onSuccess(message);
            }else{
                listener.onFail("Error HTTP");
            }
        }
    }

    public void setResultListener(IResult<Message> listener){
        this.listener = listener;
    }
}