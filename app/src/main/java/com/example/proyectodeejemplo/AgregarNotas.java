package com.example.proyectodeejemplo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.AgregarnotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.Calendar;

public class AgregarNotas extends AppCompatActivity {

    AgregarnotasBinding binding;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AgregarnotasBinding.inflate(getLayoutInflater());
        DatabaseManager dbManager = new DatabaseManager(this);

        setContentView(binding.getRoot());
        initDatePicker();
        binding.editFecha.setText(getTodaysDate());

        binding.saveNota.setOnClickListener(view -> {
            String fecha = binding.editFecha.getText().toString();
            String titulo = binding.editTitulo.getText().toString();
            String texto = binding.editTexto.getText().toString();
            int design = 1;

            if (fecha.isEmpty() || titulo.isEmpty() || texto.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                Nota nota = new Nota(0, fecha, titulo, texto, design);
                dbManager.insertNota(nota);
                finish();
            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                binding.editFecha.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day, month, year);
    }

    public void showDatePicker(View view) {
        datePickerDialog.show();
    }


}
