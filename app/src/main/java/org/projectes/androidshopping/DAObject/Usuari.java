package org.projectes.androidshopping.DAObject;

/**
 * Created by mrr on 30/05/15.
 */
public class Usuari extends DAObjectBase {
    private String email;
    private String hash_pass;
    private String pass;
    private int genere;
    private String nom;
    private int edat;
    private int rol;
    private int logged_in;
    private int deleted;

    public Usuari() {
        super();
    }

    public Usuari(String email, String pass, int genere, String nom, int edat, int rol, int logged_in) {
        super();
        this.email = email;
        this.pass = pass;
        this.genere = genere;
        this.nom = nom;
        this.edat = edat;
        this.rol = rol;
        this.logged_in = logged_in;
    }

    public Usuari(int id, String email, String hash_pass, int genere, String nom, int edat, int rol, int logged_in) {
        super();
        this.id = id;
        this.email = email;
        this.hash_pass = hash_pass;
        this.genere = genere;
        this.nom = nom;
        this.edat = edat;
        this.rol = rol;
        this.logged_in = logged_in;
    }

    public Usuari(int id, String email, String pass, int genere, String nom, int edat, int rol, int logged_in, int deleted) {
        super();
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.genere = genere;
        this.nom = nom;
        this.edat = edat;
        this.rol = rol;
        this.deleted = deleted;
        this.logged_in = logged_in;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash_pass() {
        return hash_pass;
    }

    public void setHash_pass(String hash_pass) {
        this.hash_pass = hash_pass;
    }

    public int getGenere() {
        return genere;
    }

    public void setGenere(int genere) {
        this.genere = genere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getLogged_in() {
        return logged_in;
    }

    public void setLogged_in(int logged_in) {
        this.logged_in = logged_in;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
