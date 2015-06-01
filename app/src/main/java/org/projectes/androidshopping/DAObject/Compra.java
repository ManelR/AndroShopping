package org.projectes.androidshopping.DAObject;

import java.util.LinkedList;

/**
 * Created by alloveras on 1/06/15.
 */

public class Compra extends DAObjectBase{

    private int date;
    private float price;
    private Producte producteShow;
    private LinkedList<Producte> lProductes = null;

    public Compra(){
        lProductes = new LinkedList<Producte>();
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LinkedList<Producte> getlProductes() {
        return lProductes;
    }

    public void setlProductes(LinkedList<Producte> lProductes) {
        this.lProductes = lProductes;
    }

    public Producte getProducteShow() {
        return producteShow;
    }

    public void setProducteShow(Producte auxProducte) {
        this.producteShow = producteShow;
    }

}
