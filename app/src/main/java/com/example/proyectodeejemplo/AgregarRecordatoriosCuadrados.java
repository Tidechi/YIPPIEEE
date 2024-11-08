package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.AgregarrecordatoriosBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;
import com.example.proyectodeejemplo.databinding.AgregarrecordatorioscuadradosBinding;

import java.util.Calendar;

public class AgregarRecordatoriosCuadrados extends AppCompatActivity {

    AgregarrecordatorioscuadradosBinding binding;
    public String fechaDeHoy;
    public String fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AgregarrecordatorioscuadradosBinding.inflate(getLayoutInflater());
        fechaDeHoy = getTodaysDate();
        DatabaseManager dbManager = new DatabaseManager(this);
        String[] estilosDeRecordatorio = {"Estilo 1", "Estilo 2", "Estilo 3", "Estilo 4", "Estilo 5", "Estilo 6"};

        //Spinner adapter beta
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estilosDeRecordatorio);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.tiponotaSpinner.setAdapter(adapter);

        binding.fechaview.setText(fechaDeHoy);

        // Load reminder for today's date if it exists
        Recordatorio existingRecordatorio = dbManager.findRecordatorioByFechaGPT(fechaDeHoy);
        if (existingRecordatorio != null) {
            binding.textoRecord.setText(existingRecordatorio.getTexto());
            int spinnerPosition = existingRecordatorio.getTiponota() - 1;  // Adjust for array index
            binding.tiponotaSpinner.setSelection(spinnerPosition);
            Toast.makeText(this, "Recordatorio cargado para hoy", Toast.LENGTH_SHORT).show();
        }

        binding.tiponotaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int selectedItem = position + 1;
                binding.imageView.setImageResource(getResources().getIdentifier("sticky" + selectedItem, "drawable", getPackageName()));
                Toast.makeText(AgregarRecordatoriosCuadrados.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //si no se selecciona nada...

            }
        });

        setContentView(binding.getRoot());
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }





}
