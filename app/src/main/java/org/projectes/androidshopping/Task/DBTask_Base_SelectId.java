package org.projectes.androidshopping.Task;

import android.content.Context;
import android.os.AsyncTask;

import org.projectes.androidshopping.Constants.Constants;
import org.projectes.androidshopping.DAO.DAOBase;
import org.projectes.androidshopping.DAObject.DAObjectBase;
import org.projectes.androidshopping.Listeners.IResult;
import org.projectes.androidshopping.R;

/**
 * Created by mrr on 31/05/15.
 */
public class DBTask_Base_SelectId<T extends DAOBase<E>, E extends DAObjectBase> extends AsyncTask<Object, Integer, E> {
    private Context context;
    private IResult<E> listener;

    @Override
    protected E doInBackground(Object... params) {
        E result = null;
        this.context = (Context)params[Constants.POSICIO_CONTEXT_BBDD_TASK];
        int tipus = (int) params[Constants.POSICIO_TIPUS_BBDD_TASK];
        T BBDD = (T) params[Constants.POSICIO_BBDD_TASK];
        E element = (E) params[Constants.POSICIO_ELEMENT_TASK];
        if (tipus == Constants.BBDD_SELECT_ID){
            result = BBDD.selectByID((long) element.getId());
        }
        return result;
    }

    public void onPostExecute(E result){
        if (this.listener != null){
            if (result != null){
                listener.onSuccess(result);
            }else{
                listener.onFail(this.context.getString(R.string.errorBaseDades));
            }
        }
    }

    public void setResultListener(IResult<E> listener){
        this.listener = listener;
    }
}
