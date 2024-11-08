package com.example.proyectodeejemplo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.AgregarnotasBinding;
import com.example.proyectodeejemplo.databinding.ActivityMainBinding;
import com.example.proyectodeejemplo.databinding.VernotasBinding;

import java.util.Calendar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AgregarNotaFragment extends Fragment {

    private AgregarnotasBinding binding;
    private DatePickerDialog datePickerDialog;
    private boolean isEditing = false;
    private int editingNoteId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AgregarnotasBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        DatabaseManager dbManager = new DatabaseManager(getContext());
        initDatePicker();
        binding.editFecha.setText(getTodaysDate());

        // Check if we're editing an existing note
        Bundle args = getArguments();
        if (args != null) {
            editingNoteId = args.getInt("noteId", -1);
            String titulo = args.getString("titulo");
            String texto = args.getString("texto");
            String fecha = args.getString("fecha");

            if (editingNoteId != -1) {
                isEditing = true;
                // Populate fields for editing
                binding.editTitulo.setText(titulo);
                binding.editTexto.setText(texto);
                binding.editFecha.setText(fecha);
            }
        }

        // Set the save button to either update or insert based on editing status
        binding.saveNota.setOnClickListener(view -> {
            String fecha = binding.editFecha.getText().toString();
            String titulo = binding.editTitulo.getText().toString();
            String texto = binding.editTexto.getText().toString();
            int design = 1;

            if (fecha.isEmpty() || titulo.isEmpty() || texto.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                if (isEditing) {
                    // Update existing note
                    Nota nota = new Nota(editingNoteId, fecha, titulo, texto, design);
                    dbManager.updateNota(nota);
                    Toast.makeText(getContext(), "Nota actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert new note
                    Nota nota = new Nota(0, fecha, titulo, texto, design);
                    dbManager.insertNota(nota);
                    Toast.makeText(getContext(), "Nota guardada", Toast.LENGTH_SHORT).show();
                }

                // Return to VerNotasFragment
                getParentFragmentManager().popBackStack();
            }
        });

        binding.editFecha.setOnClickListener(view -> {
            if (isAdded()) {
                showDatePicker(view);
            } else {
                Log.e("AgregarNotaFragment", "Fragment not attached to activity");
            }
        });

        return v;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                binding.editFecha.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day, month, year);
    }

    public void showDatePicker(View view) {
        if (datePickerDialog != null) {
            datePickerDialog.show();
        } else {
            Log.e("AgregarNotaFragment", "DatePickerDialog is not initialized");
        }
    }

}
