package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOBase;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;

/**
 * Created by mrr on 30/05/15.
 */
public class DBTask_Base_Modify<T extends DAOBase<E>, E> extends AsyncTask<Object, Integer, Boolean> {
    private Context context;
    private IResult<Boolean> listener;

    @Override
    protected Boolean doInBackground(Object... params) {
        Boolean result = new Boolean(false);
        this.context = (Context)params[Constants.POSICIO_CONTEXT_BBDD_TASK];
        int tipus = (int) params[Constants.POSICIO_TIPUS_BBDD_TASK];
        T BBDD = (T) params[Constants.POSICIO_BBDD_TASK];
        E element = (E) params[Constants.POSICIO_ELEMENT_TASK];
        if (BBDD == null)Log.d("NULL:", "Es null");
        else Log.d("NULL:", "No es null");
        switch (tipus){
            case Constants.BBDD_INSERT:
                if(BBDD.insert(element) > 0){
                    result = true;
                }
                break;
            case Constants.BBDD_DELETE:
                BBDD.delete(element);
                result = true;
                break;
            case Constants.BBDD_EDIT:
                BBDD.update(element);
                result = true;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    public void onPostExecute(Boolean result){
        if (this.listener != null){
            if (result){
                listener.onSuccess(result);
            }else{
                listener.onFail(this.context.getString(R.string.errorBaseDades));
            }
        }
    }

    public void setResultListener(IResult<Boolean> listener){
        this.listener = listener;
    }

}
