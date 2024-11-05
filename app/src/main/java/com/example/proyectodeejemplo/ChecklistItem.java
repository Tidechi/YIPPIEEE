package com.example.proyectodeejemplo;

public class ChecklistItem {
    public ChecklistItem() {

    }

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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public ChecklistItem(int id, String fecha, String texto, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.texto = texto;
        this.estado = estado;
    }

    private int id;
    private String fecha;
    private String texto;
    private boolean estado;
}
