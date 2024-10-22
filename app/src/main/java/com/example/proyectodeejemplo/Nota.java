package com.example.proyectodeejemplo;

public class Nota {
    public Nota(int id, String fecha, String titulo, String texto, int design) {
        this.id = id;
        this.fecha = fecha;
        this.titulo = titulo;
        this.texto = texto;
        this.design = design;
    }

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getDesign() {
        return design;
    }

    public void setDesign(int design) {
        this.design = design;
    }

    private String fecha;
    private String titulo;
    private String texto;
    private int design;
}

