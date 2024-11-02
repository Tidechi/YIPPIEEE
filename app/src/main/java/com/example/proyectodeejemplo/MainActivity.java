package com.example.proyectodeejemplo;

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

    // Fragment instances for each section (notes, checklist, calendar)
    private final AgregarNotaFragment agregarNotaFragment = new AgregarNotaFragment();
    private final CheckListYIPPIEEEFragment checkListYIPPIEEEFragment = new CheckListYIPPIEEEFragment();
    private final VerNotasFragment verNotasFragment = new VerNotasFragment();

    // Binding instance
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configure BottomNavigationView for fragment navigation
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        // Load the default fragment at startup
        loadFragment(agregarNotaFragment);
    }


    // Listener to handle selections in BottomNavigationView
    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.notas) {
                        selectedFragment = agregarNotaFragment;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: AgregarNotaFragment");
                    } else if (item.getItemId() == R.id.checklist) {
                        selectedFragment = checkListYIPPIEEEFragment;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: CheckListYIPPIEEEFragment");
                    } else if (item.getItemId() == R.id.calendario) {
                        selectedFragment = verNotasFragment;
                        loadFragment(selectedFragment);
                        Log.d("MainActivity", "Selected fragment: VerNotasFragment");
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
