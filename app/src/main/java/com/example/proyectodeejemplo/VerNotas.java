package com.example.proyectodeejemplo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.VernotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

public class VerNotas extends AppCompatActivity {

    VernotasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = VernotasBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

    }
}
