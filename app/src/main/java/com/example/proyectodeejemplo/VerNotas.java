package com.example.proyectodeejemplo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeejemplo.databinding.VernotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.List;

public class VerNotas extends AppCompatActivity {

    VernotasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = VernotasBinding.inflate(getLayoutInflater());
        DatabaseManager dbManager = new DatabaseManager(this);
        List<Nota> notas = dbManager.getAllNotas();

        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerNotas;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Crear y configurar el adaptador
        NotesAdapter adapter = new NotesAdapter(notas);
        recyclerView.setAdapter(adapter);

    }
}
