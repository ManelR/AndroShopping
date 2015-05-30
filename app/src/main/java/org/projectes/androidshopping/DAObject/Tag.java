package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 30/05/15.
 */
public class Tag extends DAObjectBase{

    private String nom;

    public Tag() {
        super();
    }

    public Tag(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
