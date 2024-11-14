package com.example.proyectodeejemplo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectodeejemplo.databinding.AgregarrecordatorioscuadradosBinding;
import com.example.proyectodeejemplo.databinding.VercalendarioBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarioFragment extends Fragment {

    private VercalendarioBinding binding;
    private String todayDate;
    private DatabaseManager dbManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VercalendarioBinding.inflate(inflater, container, false);
        dbManager = new DatabaseManager(requireContext());

        try {
            // Set up CalendarView listener for date selection
            binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                onDateSelected(dayOfMonth, month + 1, year); // month is zero-based, so add 1
            });

            // Initialize today's date and load data for today
            todayDate = getTodaysDate();
            loadRecordatorioForToday();
            loadChecklistItemsForToday();
            displayCurrentMood();

            // Set MoodLayout click listener to open AnimosVista
            binding.moodLayout.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AnimosVista.class);
                startActivity(intent);
            });

            binding.reminderFrame.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AgregarRecordatorios.class);
                intent.putExtra("selected_date", todayDate); // Pass the selected date
                startActivity(intent);
            });

        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error inflating or adding view", e);
        }

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data every time the fragment becomes visible
        loadRecordatorioForToday();
        loadChecklistItemsForToday();
        displayCurrentMood();
    }

    private void displayCurrentMood() {
        Mood mood = dbManager.obtenerMoodPorFecha(todayDate); // Use todayDate for selected date
        if (mood != null) {
            switch (mood.getEstado()) {
                case "triste":
                    binding.moodLayout.setBackground(getResources().getDrawable(R.drawable.triste));
                    break;
                case "neutral":
                    binding.moodLayout.setBackground(getResources().getDrawable(R.drawable.neutral));
                    break;
                case "feliz":
                    binding.moodLayout.setBackground(getResources().getDrawable(R.drawable.feliz));
                    break;
            }
        }
        else {
            binding.moodLayout.setBackground(getResources().getDrawable(R.drawable.circle_bg_blank));
        }
    }

    private void loadRecordatorioForToday() {
        AgregarrecordatorioscuadradosBinding agregarBinding = AgregarrecordatorioscuadradosBinding.inflate(getLayoutInflater(), binding.reminderFrame, false);

        binding.reminderFrame.removeAllViews();
        binding.reminderFrame.addView(agregarBinding.getRoot());

        Recordatorio recordatorio = dbManager.findRecordatorioByFechaGPT(todayDate);
        if (recordatorio != null) {
            agregarBinding.textoRecord.setText(recordatorio.getTexto());
            agregarBinding.fechaview.setText(recordatorio.getFecha());
            agregarBinding.tiponotaSpinner.setSelection(recordatorio.getTiponota() - 1);
            agregarBinding.imageView.setImageResource(getResources().getIdentifier("sticky" + recordatorio.getTiponota(), "drawable", requireContext().getPackageName()));
            binding.reminderFrame.setBackground(getResources().getDrawable(R.drawable.rounded_bg));
        } else {
            agregarBinding.imageView.setVisibility(View.GONE);
            binding.reminderFrame.setBackground(getResources().getDrawable(R.drawable.rounded_bg_blank));
        }
    }

    private void loadChecklistItemsForToday() {
        ArrayList<ChecklistItem> uncheckedItems = dbManager.getUncheckedItemsByFecha(todayDate);
        binding.nextCheckItemLayout.removeAllViews();

        if (uncheckedItems != null && !uncheckedItems.isEmpty()) {
            ChecklistItem item = uncheckedItems.get(0);
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(item.getTexto());
            checkBox.setPadding(3, 3, 3, 3);
            checkBox.setChecked(false);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    item.setCompleted(true);
                    dbManager.updateItem(item);
                    loadChecklistItemsForToday();
                }
            });
            binding.nextCheckItemLayout.addView(checkBox);
        } else {
            TextView noItemsTextView = new TextView(requireContext());
            noItemsTextView.setPadding(3, 3, 3, 3);
            noItemsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            noItemsTextView.setText("Completaste tus tareas!");
            noItemsTextView.setTextSize(15);
            binding.nextCheckItemLayout.addView(noItemsTextView);
        }
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        return makeDateString(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onDateSelected(int day, int month, int year) {
        todayDate = makeDateString(day, month, year);

        try {
            loadRecordatorioForToday();
            loadChecklistItemsForToday();
            displayCurrentMood();

        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error updating view on date selection", e);
        }
    }
}
