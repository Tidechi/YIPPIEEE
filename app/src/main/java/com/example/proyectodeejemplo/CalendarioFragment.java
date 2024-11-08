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
import com.example.proyectodeejemplo.databinding.AgregarrecordatorioscuadradosBinding;
import com.example.proyectodeejemplo.databinding.VercalendarioBinding;

import java.util.Calendar;

public class CalendarioFragment extends Fragment {

    private VercalendarioBinding binding;
    private String todayDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VercalendarioBinding.inflate(inflater, container, false);

        try {
            // Set up CalendarView listener for date selection
            binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                // Call onDateSelected with the selected date from CalendarView
                onDateSelected(dayOfMonth, month + 1, year); // month is zero-based, so add 1
            });

            // Inflate AgregarRecordatorios layout and get the root layout
            AgregarrecordatorioscuadradosBinding agregarBinding = AgregarrecordatorioscuadradosBinding.inflate(inflater, container, false);

            // Clear any previous views and add the initial layout
            binding.reminderFrame.removeAllViews();
            binding.reminderFrame.addView(agregarBinding.getRoot());
            todayDate = getTodaysDate();

            // Display reminder for today’s date
            loadRecordatorioForToday(agregarBinding);
            agregarBinding.fechaview.setVisibility(View.GONE);
            agregarBinding.tiponotaSpinner.setVisibility(View.GONE);
            agregarBinding.textoRecord.setTextSize(13);

        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error inflating or adding view", e);
        }

        return binding.getRoot();
    }

    private void loadRecordatorioForToday(AgregarrecordatorioscuadradosBinding agregarBinding) {
        // Initialize your database manager
        DatabaseManager dbManager = new DatabaseManager(requireContext());

        // Get today's date

        // Retrieve recordatorio for today
        Recordatorio recordatorio = dbManager.findRecordatorioByFechaGPT(todayDate);

        if (recordatorio != null) {
            // Populate fields if a reminder exists
            binding.reminderFrame.setBackground(getResources().getDrawable(R.drawable.rounded_bg));
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
            binding.reminderFrame.setBackground(getResources().getDrawable(R.drawable.rounded_bg_blank));
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

    //method that changes todaydate depending on the press on the calendarview and refreshes the view
    public void onDateSelected(int day, int month, int year) {
        // Update the `todayDate` variable with the new date
        todayDate = makeDateString(day, month, year);

        try {
            // Re-inflate the AgregarRecordatorios layout to refresh the reminderFrame
            LayoutInflater inflater = LayoutInflater.from(requireContext());
            AgregarrecordatorioscuadradosBinding agregarBinding = AgregarrecordatorioscuadradosBinding.inflate(inflater, binding.reminderFrame, false);

            // Clear any previous views to prevent duplicates, then add the new layout
            binding.reminderFrame.removeAllViews();
            binding.reminderFrame.addView(agregarBinding.getRoot());

            // Hide unwanted elements as before
            agregarBinding.fechaview.setVisibility(View.GONE);
            agregarBinding.tiponotaSpinner.setVisibility(View.GONE);
            agregarBinding.textoRecord.setTextSize(13);

            // Load and display the reminder for the selected date
            loadRecordatorioForToday(agregarBinding);

        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error updating view on date selection", e);
        }
    }
}