package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 01/06/15.
 */
public class Venta extends DAObjectBase {
    private int data;
    private String client;
    private float preu;
    private int quantitat;
    private String nomProducte;

    public Venta() {
        super();
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public String getNomProducte() {
        return nomProducte;
    }

    public void setNomProducte(String nomProducte) {
        this.nomProducte = nomProducte;
    }
}
