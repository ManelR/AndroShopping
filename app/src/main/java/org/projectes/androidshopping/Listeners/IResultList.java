package org.projectes.androidshopping.Listeners;

import java.util.ArrayList;

/**
 * Created by mrr on 12/05/15.
 */
public interface IResultList<T> {
    //Implementar el que ha de fer quan la petició a la base de dades va bé.
    public void onSuccess(ArrayList<T> obj);
    //Implementar el que ha de fer si va malament.
    public void onFail(String missatgeError);
}