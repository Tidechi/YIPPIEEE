package com.example.proyectodeejemplo;

public class Mood {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    private String fecha;
    private String estado;

    public Mood(int id, String fecha, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
    }

}
