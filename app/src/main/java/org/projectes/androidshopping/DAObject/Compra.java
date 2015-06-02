package org.projectes.androidshopping.DAObject;

import java.util.ArrayList;

/**
 * Created by alloveras on 1/06/15.
 */

public class Compra extends DAObjectBase{

    private int date;
    private float price;
    private Producte producteShow;
    private ArrayList<Producte> lProductes = null;

    public Compra(){
        lProductes = new ArrayList<Producte>();
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

    public ArrayList<Producte> getlProductes() {
        return lProductes;
    }

    public void setlProductes(ArrayList<Producte> lProductes) {
        this.lProductes = lProductes;
    }

    public Producte getProducteShow() {
        return producteShow;
    }

    public void setProducteShow(Producte auxProducte) {
        producteShow = new Producte();
        producteShow.setQuantitat(auxProducte.getQuantitat());
        producteShow.setPrecio(auxProducte.getPrecio());
        producteShow.setNombre(auxProducte.getNombre());
    }


}
