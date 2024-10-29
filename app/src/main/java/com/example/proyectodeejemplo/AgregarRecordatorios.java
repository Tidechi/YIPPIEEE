package com.example.proyectodeejemplo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.AgregarrecordatoriosBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

public class AgregarRecordatorios extends AppCompatActivity {

    AgregarrecordatoriosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AgregarrecordatoriosBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}
