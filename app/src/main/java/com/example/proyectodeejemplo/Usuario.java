package com.example.proyectodeejemplo;

public class Usuario {
    public int id;
    public String nombre;
    public String signo;
    public String cumple;
    public String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Usuario(int id, String nombre, String signo, String cumple, String color) {
        this.id = id;
        this.nombre = nombre;
        this.signo = signo;
        this.cumple = cumple;
        this.color = color;
    }

    public Usuario(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }


}
