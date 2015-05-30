package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 31/05/15.
 */
public class DAObjectBase {
    protected int id;

    public DAObjectBase() {
    }

    public DAObjectBase(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
