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

        if (dbManager.getUsuarioById(1) != null) {
            Intent intent = new Intent(Inicio.this, MainActivity.class);
            startActivity(intent);
        }

        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.nom.getText().toString();

                if (!nombre.isEmpty() && !nombre.equals(defaultText)) {
                    Usuario user = dbManager.getUsuarioById(1); // Always fetch user with ID 1
                    if (user != null) {
                        user.setNombre(nombre); // Update name
                        dbManager.updateUsuario(user); // Save updates
                        Toast.makeText(Inicio.this, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                    } else {
                        dbManager.insertUsuario(new Usuario(1, nombre)); // Use ID 1
                        Toast.makeText(Inicio.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(Inicio.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Inicio.this, "Por favor, ingresa un nombre válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}