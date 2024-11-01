package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectodeejemplo.databinding.ActivityMainBinding;
import com.example.proyectodeejemplo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //Instancias de los fragmentos para las secciones de las notas, checklist y el calendario
    AgregarNotaFragment agregarNotaFragment = new AgregarNotaFragment();
    CheckListYIPPIEEEFragment checkListYIPPIEEEFragment = new CheckListYIPPIEEEFragment();
    VerNotasFragment verNotasFragment = new VerNotasFragment();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //configuración del BottomNavigationView para gestionar la navegación entre fragmentos
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        //acá se carga el primer fragmento al momento de iniciar la actividad ¿
        loadFragment(agregarNotaFragment);
    }

    //Listener para manejar las selecciones en el BottomNavigationView
    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        //Llamen a dios
                        case R.id.notas:
                            loadFragment(agregarNotaFragment);
                            return true;
                        case R.id.checklist:
                            loadFragment(checkListYIPPIEEEFragment);
                            return true;
                            //acá puse otra cosa pq no está el calendario aún pero dsps se cambia, aja
                        case R.id.calendario:
                            loadFragment(verNotasFragment);
                            return true;
                    }
                    return false;
                }
            };

    //Método para cargar y mostrar el fragmento en el contenedor de la actividad
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment); //Reemplaza el fragmento actual por el nuevo
        transaction.commit(); //Confirma la transacción de fragmento
    }
}
