package com.fgb.ventaya.Entity;

public class Publicacion {
    String title, image, description,precio;

    public Publicacion() {
    }

    public Publicacion(String title, String image, String description, String precio) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.precio = precio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

