package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 30/05/15.
 */
public class Tag {
    private int id;
     private String nom;

    public Tag() {
    }

    public Tag(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
