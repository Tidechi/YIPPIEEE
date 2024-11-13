package com.example.proyectodeejemplo;

public class Recordatorio {
    public Recordatorio(int id, int tiponota, int sticker, String fecha, String texto) {
        this.id = id;
        this.tiponota = tiponota;
        this.sticker = sticker;
        this.fecha = fecha;
        this.texto = texto;
    }

    public Recordatorio(int tiponota, int sticker, String fecha, String texto) {
        this.tiponota = tiponota;
        this.sticker = sticker;
        this.fecha = fecha;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiponota() {
        return tiponota;
    }

    public void setTiponota(int tiponota) {
        this.tiponota = tiponota;
    }

    public int getSticker() {
        return sticker;
    }

    public void setSticker(int sticker) {
        this.sticker = sticker;
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

    private int id;
    private int tiponota;
    private int sticker;
    private String fecha;
    private String texto;
}
