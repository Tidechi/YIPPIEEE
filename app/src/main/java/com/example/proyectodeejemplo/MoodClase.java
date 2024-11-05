package com.example.proyectodeejemplo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoodClase {
    private int ID;
    private String Fecha;
    private int Estado;

    public MoodClase(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFecha() {             // se vería así: miercoles 26/09/2018 05:30 p.m.
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy hh:mm  a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public MoodClase(int ID, String Fecha, int Estado){
        this.ID= ID;
        this.Fecha= Fecha;
        this.Estado= Estado;

    }

}
