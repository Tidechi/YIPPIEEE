package com.example.proyectodeejemplo;

public class Usuario {
    public int id;
    public String nombre;
    public String signo;
    public String cumple;
    public String colorfav;

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

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }

    public String getCumple() {
        return cumple;
    }

    public void setCumple(String cumple) {
        this.cumple = cumple;
    }

    public String getcolorfav() {
        return colorfav;
    }

    public void setcolorfav(String colorfav) {
        this.colorfav = colorfav;
    }

    public Usuario(int id, String nombre, String signo, String cumple, String colorfav) {
        this.id = id;
        this.nombre = nombre;
        this.signo = signo;
        this.cumple = cumple;
        this.colorfav = colorfav;
    }

    public Usuario(int id, String nombre){
        this.id = id;
        this.nombre = nombre;

    }


}
