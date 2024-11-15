package com.example.proyectodeejemplo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateManager {
    //Esta clase es pa recuperar el día, mes, año, y hora actual nomas
    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Santiago"));

        return dateFormat.format(cal.getTime());
    }

    public String getMes() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Santiago"));

        return dateFormat.format(cal.getTime());
    }

    public String getMesLetras() {
        String nombreMes = "";
        String mes = getMes();
        if (mes.equals("1")) {
            nombreMes = "Enero";
        } else if (mes.equals("2")) {
            nombreMes = "Febrero";
        } else if (mes.equals("3")) {
            nombreMes = "Marzo";
        } else if (mes.equals("4")) {
            nombreMes = "Abril";
        } else if (mes.equals("5")) {
            nombreMes = "Mayo";
        } else if (mes.equals("6")) {
            nombreMes = "Junio";
        } else if (mes.equals("7")) {
            nombreMes = "Julio";
        } else if (mes.equals("8")) {
            nombreMes = "Agosto";
        } else if (mes.equals("9")) {
            nombreMes = "Septiembre";
        } else if (mes.equals("10")) {
            nombreMes = "Octubre";
        } else if (mes.equals("11")) {
            nombreMes = "Noviembre";
        } else if (mes.equals("12")) {
            nombreMes = "Diciembre";
        }

        return nombreMes;
    }


    public String getDia() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Santiago"));

        return dateFormat.format(cal.getTime());
    }
}