package com.example.projectf2f3.entitades;

import java.io.Serializable;


// els productes que tindrem al restaurant guardarem nom, descripcio, preu i la foto
public class MenuVo  implements Serializable {

    private String Nombre;
    private String Descripcion;
    private String Precio;
    private String foto;

    // construtor
    public MenuVo(String nombre, String descripcion, String precio, String foto) {
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Precio = precio;
        this.foto = foto;
    }

    public MenuVo() {

    }

    // getter i setter

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
