package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOBase;
import org.projectes.androidshopping.DAObject.DAObjectBase;
import org.projectes.androidshopping.Listeners.IResultList;
import org.projectes.androidshopping.R;

import java.util.ArrayList;

/**
 * Created by mrr on 31/05/15.
 */
public class DBTask_Base_SelectAllNotDeleted<T extends DAOBase<E>, E extends DAObjectBase> extends AsyncTask<Object, Integer, Boolean> {
    private Context context;
    private IResultList<E> listener;
    private ArrayList<E> elements;

    @Override
    protected Boolean doInBackground(Object... params) {
        Boolean result = new Boolean(false);
        this.context = (Context)params[Constants.POSICIO_CONTEXT_BBDD_TASK];
        int tipus = (int) params[Constants.POSICIO_TIPUS_BBDD_TASK];
        T BBDD = (T) params[Constants.POSICIO_BBDD_TASK];
        if (tipus == Constants.BBDD_SELECT_ID){
            elements = BBDD.selectAllNotDeleted();
            result = true;
        }
        return result;
    }

    public void onPostExecute(Boolean result){
        if (this.listener != null){
            if (elements != null && result){
                listener.onSuccess(elements);
            }else{
                listener.onFail(this.context.getString(R.string.errorBaseDades));
            }
        }
    }

    public void setResultListener(IResultList<E> listener){
        this.listener = listener;
    }

}
