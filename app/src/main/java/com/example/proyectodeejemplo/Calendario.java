package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.VercalendarioBinding;

public class Calendario extends AppCompatActivity {
    VercalendarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = VercalendarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
}
    }
