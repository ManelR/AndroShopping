package org.projectes.androidshopping.DAObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mrr on 12/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("id") public int id;
    @JsonProperty("nombre") public String nombre;
    @JsonProperty("descripcion") public String descripcion;
    @JsonProperty("precio") public float precio;
    @JsonProperty("activo") public boolean activo;
    @JsonProperty("stock") public int stock;
    @JsonProperty("foto") public String image;
    @JsonProperty("tags") public List<String> tags;

    public Product(){

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
