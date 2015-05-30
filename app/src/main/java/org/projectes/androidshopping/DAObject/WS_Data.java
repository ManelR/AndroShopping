package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 30/05/15.
 */
public class WS_Data extends DAObjectBase{
    private long date;
    private String nomTaula;

    public WS_Data() {
        super();
    }

    public WS_Data(long date, String nomTaula) {
        super();
        this.date = date;
        this.nomTaula = nomTaula;
    }

    public WS_Data(int id, long date, String nomTaula) {
        super();
        this.id = id;
        this.date = date;
        this.nomTaula = nomTaula;
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
