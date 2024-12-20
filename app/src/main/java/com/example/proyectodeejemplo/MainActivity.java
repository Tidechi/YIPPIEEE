package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log; // Import Log class for logging
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.proyectodeejemplo.CheckListYIPPIEEE;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager db;

    // Fragment instances for each section (notes, checklist, calendar)
    private final VerNotasFragment verNotasFragment = new VerNotasFragment();
    private final CheckListYIPPIEEEFragment checkListYIPPIEEEFragment = new CheckListYIPPIEEEFragment();
    private final CalendarioFragment verCalendarioFragment = new CalendarioFragment();
    private final UserView UserView = new UserView();

    // Binding instance
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseManager(this);


        //sin no encuentra usuarios registrados, se devuelve a la pantalla de inicio
        if (db.getAllUsers().size() != 0) {
        } else {
            Intent intent = new Intent(MainActivity.this, Inicio.class);
            startActivity(intent);
            finish();
            return; //Termina `onCreate` si redirige para evitar que siga ejecutando el código restante
        }


        // Configure BottomNavigationView for fragment navigation
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        // Load the default fragment at startup
        loadFragment(verCalendarioFragment);


    }



    // Listener to handle selections in BottomNavigationView
    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.notas) {
                        selectedFragment = verNotasFragment;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: VerNotasFragment");
                    } else if (item.getItemId() == R.id.checklist) {
                        selectedFragment = checkListYIPPIEEEFragment;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: CheckListYIPPIEEEFragment");
                    } else if (item.getItemId() == R.id.calendario) {
                        selectedFragment = verCalendarioFragment;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: UserView");
                    } else if (item.getItemId() == R.id.usuario) {
                        selectedFragment = UserView;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: CalendarioFragment");
                   } else {
                          return false;
                       }
                    loadFragment(selectedFragment);
                    return true;
                }
            };

    // Method to load and display the fragment in the activity's container
    private void loadFragment(Fragment fragment) {
        Log.d("MainActivity", "Loading fragment: " + fragment.getClass().getSimpleName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment); // Replace the current fragment with the new one
        transaction.commit(); // Commit the fragment transaction
    }
}
