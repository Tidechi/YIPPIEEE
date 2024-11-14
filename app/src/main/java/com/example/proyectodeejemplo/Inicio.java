package com.example.proyectodeejemplo;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectodeejemplo.databinding.ActivityMainBinding;
import com.example.proyectodeejemplo.databinding.InicioBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Inicio extends AppCompatActivity {
    private InicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseManager dbManager = new DatabaseManager(this);

        String defaultText = "Ingresa tu nombre";

        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.nom.getText().toString();

                if (!nombre.isEmpty() && !nombre.equals(defaultText)) {
                    Usuario usuarionuevo = new Usuario(0, nombre); // Toma el valor actual de userInput
                    dbManager.insertUsuario(usuarionuevo);

                    Intent intent = new Intent(Inicio.this, MainActivity.class); // Asegúrate de que UserView sea la actividad correcta
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Inicio.this, "Por favor, ingresa un nombre válido!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
