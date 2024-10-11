package com.example.proyectodeejemplo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.OpeningscrnBinding;

public class Openingscrn extends AppCompatActivity {

    OpeningscrnBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = OpeningscrnBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}
