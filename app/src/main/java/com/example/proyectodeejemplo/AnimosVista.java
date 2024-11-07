package com.example.proyectodeejemplo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectodeejemplo.databinding.ActivityAnimosVistaBinding;
import com.example.proyectodeejemplo.databinding.ActivityCheckListYippieeeBinding;

public class AnimosVista extends AppCompatActivity {

    ActivityAnimosVistaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAnimosVistaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.BotonTriste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MediaPlayer mediaPlayer = MediaPlayer.create(AnimosVista.this, R.raw.yipi);
                //mediaPlayer.start();
            }
        });

        binding.BotonSerio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MediaPlayer mediaPlayer = MediaPlayer.create(AnimosVista.this, R.raw.yipi);
                //mediaPlayer.start();
            }
        });
        binding.BotonFeliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //MediaPlayer mediaPlayer = MediaPlayer.create(AnimosVista.this, R.raw.yipi);
                //mediaPlayer.start();
            }
        });
    }
}