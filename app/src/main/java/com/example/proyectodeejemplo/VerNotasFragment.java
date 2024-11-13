package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeejemplo.databinding.VernotasBinding;

import java.util.List;

public class VerNotasFragment extends Fragment implements RecyclerViewInterface, OnNotaSavedListener, SearchView.OnQueryTextListener,AgregarNotaFragment.OnNotaSavedListener {

    private VernotasBinding binding;
    private NotesAdapter adapter;
    private DatabaseManager dbManager;
    private List<Nota> notas;

    @Override
    public void onNotaSaved() {
        // Reload notes and update the adapter's data without creating a new adapter instance
        List<Nota> updatedNotas = dbManager.getAllNotas();
        adapter.updateData(updatedNotas);
        adapter.notifyDataSetChanged();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VernotasBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        // Set up database and RecyclerView for displaying notes
        dbManager = new DatabaseManager(getContext());
        notas = dbManager.getAllNotas();
        binding.recyclerNotas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter once and set it to RecyclerView
        adapter = new NotesAdapter(notas, this);
        binding.recyclerNotas.setAdapter(adapter);



        binding.Buscador.setOnQueryTextListener(this);
        binding.agregarNotaButton.setOnClickListener(this::onAddNotaButtonClick);

        return v;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrar(newText);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the notes list each time the fragment becomes visible
        onNotaSaved();
        RecargarNotas();
    }

    public void onNotaClick(int position) {
        // Get the selected note and pass data to AgregarNotaFragment
        Nota selectedNota = adapter.getNotaAt(position); // get the note directly from the adapter

        AgregarNotaFragment agregarNotaFragment = new AgregarNotaFragment();
        Bundle args = new Bundle();
        args.putInt("noteId", selectedNota.getId());
        args.putString("titulo", selectedNota.getTitulo());
        args.putString("texto", selectedNota.getTexto());
        args.putString("fecha", selectedNota.getFecha());
        agregarNotaFragment.setArguments(args);

        // Start transaction to replace VerNotasFragment with AgregarNotaFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.VerNotasFragmentContainer, agregarNotaFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onAddNotaButtonClick(View view) {
        // Replace VerNotasFragment with AgregarNotaFragment to add a new note
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.VerNotasFragmentContainer, new AgregarNotaFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void RecargarNotas() {
        List<Nota> updatedNotas = dbManager.getAllNotas();
        notas.clear();
        notas.addAll(updatedNotas);
        adapter.updateData(notas); // Update the adapter with the latest data
    }
}