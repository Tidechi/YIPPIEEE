package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectodeejemplo.databinding.DebugmenuBinding;

import java.util.ArrayList;

public class debugMenu extends AppCompatActivity {
    DebugmenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DebugmenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseManager dbManager = new DatabaseManager(this);

        binding.xd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Usuario> Usuarios = new ArrayList<>();
                    Usuarios.add(dbManager.getUsuarioById(1));

                    Intent intent;
                    if (!Usuarios.isEmpty()) {
                        intent = new Intent(debugMenu.this, MainActivity.class);
                    } else {
                        intent = new Intent(debugMenu.this, Inicio.class);
                    }
                    startActivity(intent);

                } catch (Exception e) {
                    Log.e("debugMenu", "Error al acceder a la base de datos", e);
                    Toast.makeText(debugMenu.this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        // Attach the click listener for button3
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.deleteAllUsers();
                Toast.makeText(debugMenu.this, "Todos los usuarios han sido eliminados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
