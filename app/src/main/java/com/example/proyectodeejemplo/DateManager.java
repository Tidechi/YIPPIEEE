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
}