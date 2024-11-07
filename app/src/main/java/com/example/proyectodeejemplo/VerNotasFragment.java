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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeejemplo.databinding.VernotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class VerNotasFragment extends Fragment implements RecyclerViewInterface, OnNotaSavedListener{

    private VernotasBinding binding;

    @Override
    public void onNotaSaved() {
        // Reload the notes from the database
        DatabaseManager dbManager = new DatabaseManager(getContext());
        List<Nota> updatedNotas = dbManager.getAllNotas();

        // Update the adapter's data
        NotesAdapter adapter = new NotesAdapter(updatedNotas, this);
        binding.recyclerNotas.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VernotasBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        // Set up database and RecyclerView for displaying notes
        DatabaseManager dbManager = new DatabaseManager(getContext());
        List<Nota> notas = dbManager.getAllNotas();
        RecyclerView recyclerView = binding.recyclerNotas;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NotesAdapter adapter = new NotesAdapter(notas, this);
        recyclerView.setAdapter(adapter);

        // Set click listener for add note button
        binding.agregarNotaButton.setOnClickListener(this::onAddNotaButtonClick);

        return v;
    }

    @Override
    public void onNotaClick(int position) {
        Intent intent = new Intent(getContext(), AgregarNotaFragment.class);
    }

    public void onAddNotaButtonClick(View view) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.VerNotasFragmentContainer, new AgregarNotaFragment()); // Replace VerNotasFragment with AgregarNotaFragment
        transaction.addToBackStack(null); // Allows back navigation to VerNotasFragment
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        // This ensures the list is also refreshed when the user manually returns to this fragment
        onNotaSaved();
    }


}
