package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectodeejemplo.databinding.AgregarrecordatoriosBinding;
import com.example.proyectodeejemplo.databinding.VercalendarioBinding;

import java.util.Calendar;

public class CalendarioFragment extends Fragment {

    private VercalendarioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VercalendarioBinding.inflate(inflater, container, false);

        try {
            // Inflate AgregarRecordatorios layout and get the root layout
            AgregarrecordatoriosBinding agregarBinding = AgregarrecordatoriosBinding.inflate(inflater, container, false);

            // Clear any previous views to prevent duplicates, then add the new layout
            binding.reminderFrame.removeAllViews();
            binding.reminderFrame.addView(agregarBinding.getRoot());

            // Retrieve and display the reminder for today's date
            loadRecordatorioForToday(agregarBinding);
            agregarBinding.fechaview.setVisibility(View.GONE);
            agregarBinding.tiponotaSpinner.setVisibility(View.GONE);
            agregarBinding.buttonAceptar.setVisibility(View.GONE);
            agregarBinding.buttonDescartar.setVisibility(View.GONE);
            agregarBinding.textoRecord.setTextSize(5);


        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error inflating or adding view", e);
        }

        return binding.getRoot();
    }

    private void loadRecordatorioForToday(AgregarrecordatoriosBinding agregarBinding) {
        // Initialize your database manager
        DatabaseManager dbManager = new DatabaseManager(requireContext());

        // Get today's date
        String todayDate = getTodaysDate();

        // Retrieve recordatorio for today
        Recordatorio recordatorio = dbManager.findRecordatorioByFechaGPT(todayDate);

        if (recordatorio != null) {
            // Populate fields if a reminder exists
            agregarBinding.textoRecord.setText(recordatorio.getTexto());
            agregarBinding.fechaview.setText(recordatorio.getFecha());

            // Set spinner position safely
            if (recordatorio.getTiponota() > 0) {
                agregarBinding.tiponotaSpinner.setSelection(recordatorio.getTiponota() - 1);
            }

            // Update the image based on type
            int selectedItem = recordatorio.getTiponota();
            int drawableId = getResources().getIdentifier("sticky" + selectedItem, "drawable", requireContext().getPackageName());
            if (drawableId != 0) {
                agregarBinding.imageView.setImageResource(drawableId);
            } else {
                Log.e("CalendarioFragment", "Drawable resource not found for sticky" + selectedItem);
            }
        } else {
            Log.w("CalendarioFragment", "No reminder found for today's date");
        }
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}