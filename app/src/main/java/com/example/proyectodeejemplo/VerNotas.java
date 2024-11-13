package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeejemplo.databinding.VernotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class VerNotas extends AppCompatActivity implements RecyclerViewInterface,SearchView.OnQueryTextListener {

    SearchView textoBuscar;
    VernotasBinding binding;
    DatabaseManager dbManager = new DatabaseManager(this);
    List<Nota> notas = dbManager.getAllNotas();
    NotesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = VernotasBinding.inflate(getLayoutInflater());



        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerNotas;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Crear y configurar el adaptador
         adapter = new NotesAdapter(notas, this);
        recyclerView.setAdapter(adapter);


        binding.Buscador.clearFocus();
        binding.Buscador.setOnQueryTextListener(this);

    }


    @Override
    public void onNotaClick(int position) {
        Intent intent = new Intent(this, AgregarNotas.class);

    }

    public void onAddNotaButtonClick(View view) {
        Intent intent = new Intent(this, AgregarNotaFragment.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrar(s);
        return false;
    }
}
