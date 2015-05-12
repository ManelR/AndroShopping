package org.projectes.androidshopping.WS;

import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by mrr on 29/04/15.
 */
public class WSConnector {

    public WSConnector(){

    }

    public Message get(String url, List<NameValuePair> parametros) throws IllegalStateException, IOException {
        BufferedReader in = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        //Afegir parametres si és necessari
        if (parametros != null){
            request.setEntity(new UrlEncodedFormEntity(parametros));
        }

        //Create local HTTP context
        HttpContext localContext = new BasicHttpContext();

        //Bind custom cookie store to the local context
        //localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        HttpResponse response = client.execute(request, localContext);
        InputStreamReader ix = new InputStreamReader(response.getEntity().getContent());
        in = new BufferedReader(ix, 4096);
        String accum = "";
        String line = "";
        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null){
            accum += line + NL;
            Log.i("--WS--", "WS Received: " + accum.length() + ".");
        }

        Log.i("--WS--", "Mensaje: " + accum);
        Message resp = new Message();
        resp.what = response.getStatusLine().getStatusCode();
        resp.obj = accum;
        return resp;
    }
}
