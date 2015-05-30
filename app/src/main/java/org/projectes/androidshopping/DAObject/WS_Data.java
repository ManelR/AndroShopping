package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 30/05/15.
 */
public class WS_Data {
    private int id;
    private long date;
    private String nomTaula;

    public WS_Data() {
    }

    public WS_Data(long date, String nomTaula) {
        this.date = date;
        this.nomTaula = nomTaula;
    }

    public WS_Data(int id, long date, String nomTaula) {
        this.id = id;
        this.date = date;
        this.nomTaula = nomTaula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getNomTaula() {
        return nomTaula;
    }

    public void setNomTaula(String nomTaula) {
        this.nomTaula = nomTaula;
    }
}
