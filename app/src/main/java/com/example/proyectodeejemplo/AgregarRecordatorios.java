package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.proyectodeejemplo.databinding.AgregarrecordatoriosBinding;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.AgregarrecordatoriosBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;

import java.util.Calendar;

public class AgregarRecordatorios extends AppCompatActivity {
    AgregarrecordatoriosBinding binding;
    private int selectedTiponota = 1; // Defaults to sticky1
    private String fechaDeHoy;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AgregarRecordatorios", "onCreate: Activity started");

        try {
            binding = AgregarrecordatoriosBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            Log.d("AgregarRecordatorios", "Binding and layout set successfully");

            dbManager = new DatabaseManager(this);
            Log.d("AgregarRecordatorios", "Database initialized successfully");

            // Check if a date was passed from CalendarioFragment
            if (getIntent().hasExtra("selected_date")) {
                fechaDeHoy = getIntent().getStringExtra("selected_date");
            } else {
                fechaDeHoy = getTodaysDate();
            }

            binding.fechaview.setText(fechaDeHoy);
            setupGalleryRecyclerView();
            loadExistingRecordatorio();

            binding.buttonAceptar.setOnClickListener(view -> saveRecordatorio());
            binding.buttonDescartar.setOnClickListener(view -> discardChanges());
        } catch (Exception e) {
            Log.e("AgregarRecordatorios", "Error in onCreate setup", e);
        }
    }

    private void setupGalleryRecyclerView() {
        try {
            List<Integer> imageResources = Arrays.asList(
                    R.drawable.sticky1, R.drawable.sticky2, R.drawable.sticky3,
                    R.drawable.sticky4, R.drawable.sticky5, R.drawable.sticky6
            );

            RecyclerView recyclerView = findViewById(R.id.horizontal_scroller);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            GalleryImageAdapter adapter = new GalleryImageAdapter(this, imageResources, imageResource -> {
                selectedTiponota = imageResources.indexOf(imageResource) + 1; // sticky1 = 1, sticky2 = 2, etc.
                binding.imageView.setImageResource(imageResource);
                Log.d("AgregarRecordatorios", "Selected tiponota: " + selectedTiponota);
            });
            recyclerView.setAdapter(adapter);

            binding.backStyleButton.setOnClickListener(v -> recyclerView.setAdapter(adapter));
            Log.d("AgregarRecordatorios", "Gallery RecyclerView setup complete");
        } catch (Exception e) {
            Log.e("AgregarRecordatorios", "Error in setupGalleryRecyclerView", e);
        }
    }

    private void loadExistingRecordatorio() {
        try {
            Recordatorio existingRecordatorio = dbManager.findRecordatorioByFechaGPT(fechaDeHoy);

            if (existingRecordatorio != null) {
                binding.textoRecord.setText(existingRecordatorio.getTexto());
                selectedTiponota = existingRecordatorio.getTiponota();
                binding.imageView.setImageResource(getImageResourceByTiponota(selectedTiponota));
                Log.d("AgregarRecordatorios", "Loaded existing recordatorio with tiponota: " + selectedTiponota);
            } else {
                binding.textoRecord.setText("Sin recordatorio aun");
                selectedTiponota = 1;
                binding.imageView.setImageResource(R.drawable.sticky1);
                Log.d("AgregarRecordatorios", "No existing recordatorio found");
            }
        } catch (Exception e) {
            Log.e("AgregarRecordatorios", "Error loading existing recordatorio", e);
        }
    }

    private void saveRecordatorio() {
        String texto = binding.textoRecord.getText().toString();

        if (texto.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Check if there's an existing recordatorio for today's date
            Recordatorio existingRecordatorio = dbManager.findRecordatorioByFechaGPT(fechaDeHoy);

            if (existingRecordatorio != null) {
                // Update the existing recordatorio
                existingRecordatorio.setTexto(texto);
                existingRecordatorio.setTiponota(selectedTiponota);
                dbManager.updateRecordatorio(existingRecordatorio);
                Toast.makeText(this, "Recordatorio actualizado", Toast.LENGTH_SHORT).show();
            } else {
                // Insert a new recordatorio if none exists
                Recordatorio newRecordatorio = new Recordatorio(selectedTiponota, 1, fechaDeHoy, texto);
                dbManager.insertRecordatorio(newRecordatorio);
                Toast.makeText(this, "Recordatorio guardado", Toast.LENGTH_SHORT).show();
            }

            finish();
        } catch (Exception e) {
            Log.e("AgregarRecordatorios", "Error saving or updating recordatorio", e);
        }
    }

    private void discardChanges() {
        finish();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private int getImageResourceByTiponota(int tiponota) {
        switch (tiponota) {
            case 1: return R.drawable.sticky1;
            case 2: return R.drawable.sticky2;
            case 3: return R.drawable.sticky3;
            case 4: return R.drawable.sticky4;
            case 5: return R.drawable.sticky5;
            case 6: return R.drawable.sticky6;
            default: return R.drawable.sticky1;
        }
    }
}
