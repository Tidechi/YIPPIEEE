package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;
//
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //Declaración de la variable binding que contendrá la referencia a la clase generada para el archivo XML de diseño
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicialización del binding creando una instancia del diseño XML asociado con la actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //Establece el contenido de la actividad usando la raíz del diseño instanciado con binding
        setContentView(binding.getRoot());


        binding.BTNCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckListYIPPIEEE.class);
                //un Intent es un objeto que se utiliza para enviar un mensaje desde una parte de la aplicación a otra. Es una forma de comunicar diferentes componentes de la aplicación
                startActivity(intent);
            }
        });

        binding.BTNCL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Openingscrn.class);
                startActivity(intent);
            }
        });




    }
}