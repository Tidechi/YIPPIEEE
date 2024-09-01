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
    //
    private Handler handler;
    //
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        handler = new Handler();

        //Inicialización del binding creando una instancia del diseño XML asociado con la actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //Establece el contenido de la actividad usando la raíz del diseño instanciado con binding
        setContentView(binding.getRoot());

        //Acá básicamente tomamos el texto que se ingrese en TXT2, y hacemos que se transcriba en la
        //otra casilla de texto dándole al botón
        binding.BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = binding.TXT2.getText().toString();
                //Esto manda una notificación noma
                Toast.makeText(getApplicationContext(), "Mucho texto"+texto,Toast.LENGTH_SHORT).show();
                binding.TXT1.setText(texto);
            }
        });

        binding.CB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.CB1.isChecked()){
                    Toast.makeText(getApplicationContext(), "Waos marcado", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Waos desmarcado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.RG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.OPC_1) {
                    Toast.makeText(MainActivity.this, "Opción 1 seleccionada!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Opción 2 seleccionada!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (progressStatus < 100){
                    progressStatus++;
                    binding.PB1.setProgress(progressStatus);
                    handler.postDelayed(this, 100);
                }
            }
        };
        handler.post(runnable);

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(MainActivity.this, "AAAAAA: " + rating, Toast.LENGTH_SHORT).show();

            }
        });

        binding.BTNCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckListYIPPIEEE.class);
                //un Intent es un objeto que se utiliza para enviar un mensaje desde una parte de la aplicación a otra. Es una forma de comunicar diferentes componentes de la aplicación
                startActivity(intent);
            }
        });


        // Establece un listener para el botón BTNCL usando una expresión lambda
        binding.BTNCL.setOnClickListener(v -> {
            // Crea un nuevo Intent para iniciar la actividad CheckListYIPPIEEE
            Intent intent = new Intent(MainActivity.this, CheckListYIPPIEEE.class);
            // Inicia la nueva actividad usando el intent
            startActivity(intent);
        });
    //Esto es lo mismo que hice arriba pero con la función de flecha!!

    }
}