package com.example.proyectodeejemplo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.AgregarnotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

public class AgregarNotas extends AppCompatActivity {

    AgregarnotasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AgregarnotasBinding.inflate(getLayoutInflater());
        DatabaseManager dbManager = new DatabaseManager(this);

        setContentView(binding.getRoot());

        binding.saveNota.setOnClickListener(view -> {
            String fecha = binding.editFecha.getText().toString();
            String titulo = binding.editTitulo.getText().toString();
            String texto = binding.editTexto.getText().toString();
            int design = 1;
            int id = 1;

            Nota nota = new Nota(id, fecha, titulo, texto, design);
            dbManager.insertNota(nota);
            finish();
        });
    }


}
