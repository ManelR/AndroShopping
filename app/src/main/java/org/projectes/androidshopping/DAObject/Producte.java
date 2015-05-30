package org.projectes.androidshopping.DAObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mrr on 12/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producte {
    @JsonIgnore public int id;
    @JsonProperty("id") public int id_remot;
    @JsonProperty("nombre") public String nombre;
    @JsonProperty("descripcion") public String descripcion;
    @JsonProperty("precio") public float precio;
    @JsonProperty("activo") public boolean activo;
    @JsonProperty("stock") public int stock;
    @JsonProperty("foto") public String image;
    @JsonIgnore public List<Tag> DB_tags;
    @JsonProperty("tags") public List<String> WS_tags;
    @JsonIgnore public int deleted;

    public Producte(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getWS_tags() {
        return WS_tags;
    }

    public void setWS_tags(List<String> WS_tags) {
        this.WS_tags = WS_tags;
    }

    public List<Tag> getDB_tags() {
        return DB_tags;
    }

    public void setDB_tags(List<Tag> DB_tags) {
        this.DB_tags = DB_tags;
    }

    public int getId_remot() {
        return id_remot;
    }

    public void setId_remot(int id_remot) {
        this.id_remot = id_remot;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}