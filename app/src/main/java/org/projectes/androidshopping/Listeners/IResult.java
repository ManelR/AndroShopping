package org.projectes.androidshopping.Listeners;

/**
 * Created by mrr on 12/05/15.
 */
public interface IResult<T>{
    //Implementar el que ha de fer quan la petició a la base de dades va bé.
    public void onSuccess(T IRresult);
    //Implementar el que ha de fer si va malament.
    public void onFail(String missatgeError);
}