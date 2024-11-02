package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AgregarNotaFragment extends Fragment {

    //el bottomnavigationview está diseñado para navegar entre fragmentos dentro de una actividad principal¿
    //(en este caso, la MainActivity), en lugar de abrir nuevas actividades ¿
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("AgregarNotaFragment", "onCreateView called");
        return inflater.inflate(R.layout.agregarnotas, container, false);
    }

}
