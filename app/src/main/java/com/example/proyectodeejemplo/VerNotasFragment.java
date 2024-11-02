package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectodeejemplo.databinding.ActivityCheckListYippieeeBinding;
import com.example.proyectodeejemplo.databinding.VernotasBinding;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeejemplo.databinding.VernotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.List;

public class VerNotasFragment extends Fragment implements RecyclerViewInterface {

    private VernotasBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inicializar el binding en un fragment
        binding = VernotasBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        //Metodos de la base de datos a ver si carga...
        DatabaseManager dbManager = new DatabaseManager(getContext());
        List<Nota> notas = dbManager.getAllNotas();

        //Funcionara el recyclerview...?
        RecyclerView recyclerView = binding.recyclerNotas;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //metiendo el adaptador
        NotesAdapter adapter = new NotesAdapter(notas, this);
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onNotaClick(int position) {
        Intent intent = new Intent(getContext(), AgregarNotas.class);
    }
}
