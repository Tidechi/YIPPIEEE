package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectodeejemplo.databinding.DebugmenuBinding;


public class debugMenu extends AppCompatActivity {
    DebugmenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicialización del binding creando una instancia del diseño XML asociado con la actividad
        binding = DebugmenuBinding.inflate(getLayoutInflater());
        //Establece el contenido de la actividad usando la raíz del diseño instanciado con binding
        setContentView(binding.getRoot());

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(debugMenu.this, AgregarRecordatorios.class);
                startActivity(intent);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(debugMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}