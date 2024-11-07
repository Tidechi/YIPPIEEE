package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeejemplo.databinding.VernotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.List;

public class VerNotas extends AppCompatActivity implements RecyclerViewInterface {

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
        NotesAdapter adapter = new NotesAdapter(notas, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNotaClick(int position) {
        Intent intent = new Intent(this, AgregarNotas.class);

    }

    public void onAddNotaButtonClick(View view) {
        Intent intent = new Intent(this, AgregarNotaFragment.class);
        startActivity(intent);
    }
}
