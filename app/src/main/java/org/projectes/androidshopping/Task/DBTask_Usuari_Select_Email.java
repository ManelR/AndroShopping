package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOUsuaris;
import org.projectes.androidshopping.DAObject.Usuari;
import org.projectes.androidshopping.Listeners.IResult;

/**
 * Created by mrr on 31/05/15.
 */
public class DBTask_Usuari_Select_Email extends AsyncTask<Object, Integer, Usuari> {
    private IResult<Usuari> listener;
    private Context context;
    @Override
    protected Usuari doInBackground(Object... params) {
        Usuari result = null;
        this.context = (Context)params[Constants.POSICIO_CONTEXT_BBDD_TASK];
        DAOUsuaris BBDD = new DAOUsuaris(this.context);
        String email = (String)params[1];
        result = BBDD.selectByEmail(email);
        return result;
    }

    public void onPostExecute(Usuari result){
        if (this.listener != null){
            //Cal comprovar al listener si és null o no.
            this.listener.onSuccess(result);
        }
    }

    public void setResultListener(IResult<Usuari> listener){
        this.listener = listener;
    }
}
