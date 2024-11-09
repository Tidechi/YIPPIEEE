package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectodeejemplo.databinding.ActivityAnimosVistaBinding;
import java.util.Calendar;

public class AnimosVista extends AppCompatActivity {
    private String fechaDeHoy;
    private DatabaseManager dbManager;
    private ActivityAnimosVistaBinding binding;

    private TextView dateTimeTextView;
    private DateManager dateManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DatabaseManager(this);
        binding = ActivityAnimosVistaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fechaDeHoy = getTodaysDate();


        //Esto es sólo por el detalle estético que agregué al bosquejo pa que se vea la fecha y hora actuales ¿
        DateManager dateManager = new DateManager();
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        String currentDateAndTime = dateManager.getTodaysDate();

        dateTimeTextView.setText(currentDateAndTime);



        binding.triste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMood("triste");
                finish(); // Return to calendar view
            }
        });

        binding.neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMood("neutral");
                finish(); // Return to calendar view
            }
        });

        binding.feliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMood("feliz");
                finish(); // Return to calendar view
            }
        });
    }

    private void saveMood(String estado) {
        Mood mood = dbManager.obtenerMoodPorFecha(fechaDeHoy);
        if (mood == null) {
            mood = new Mood(0, fechaDeHoy, estado);
            dbManager.insertMood(mood);
        } else {
            mood.setEstado(estado);
            dbManager.actualizarMood(mood);
        }
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


}
