package com.example.proyectodeejemplo;

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

import com.example.proyectodeejemplo.databinding.AgregarrecordatoriosBinding;
import com.example.proyectodeejemplo.databinding.AgregarrecordatorioscuadradosBinding;
import com.example.proyectodeejemplo.databinding.VercalendarioBinding;

import java.util.ArrayList;
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

            // Load checklist items for today
            loadChecklistItemsForToday();

        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error inflating or adding view", e);
        }

        return binding.getRoot();
    }

    private void loadRecordatorioForToday(AgregarrecordatorioscuadradosBinding agregarBinding) {
        // Initialize your database manager
        DatabaseManager dbManager = new DatabaseManager(requireContext());

        // Retrieve recordatorio for today
        Recordatorio recordatorio = dbManager.findRecordatorioByFechaGPT(todayDate);
        Log.d("CalendarioFragment", "Fetched reminder: " + (recordatorio != null ? recordatorio.getTexto() : "No reminder found"));

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

    private void loadChecklistItemsForToday() {
        // Initialize your database manager
        DatabaseManager dbManager = new DatabaseManager(requireContext());

        // Retrieve checklist items for today (only the ones that are not completed)
        ArrayList<ChecklistItem> uncheckedItems = dbManager.getUncheckedItemsByFecha(todayDate);

        // Clear any previous checklist items
        binding.nextCheckItemLayout.removeAllViews();

        if (uncheckedItems != null && !uncheckedItems.isEmpty()) {
            // Get the first unchecked item
            ChecklistItem item = uncheckedItems.get(0);

            // Create a CheckBox for marking the task as completed
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(item.getTexto());  // Set the checklist item text as the checkbox text
            checkBox.setPadding(3,3,3,3);

            // Set the checkbox to unchecked initially
            checkBox.setChecked(false);

            // When checkbox is clicked, update the task completion status in the database
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Mark the task as completed
                    item.setCompleted(true);  // Assuming the ChecklistItem has a setCompleted method
                    dbManager.updateItem(item);  // Update the task status in the database
                }

                // After marking it as completed, load the next task (if any)
                loadChecklistItemsForToday();  // Recursively load the next task
            });

            // Add the CheckBox to the layout
            binding.nextCheckItemLayout.addView(checkBox);
        } else {
            // Display message if no unchecked tasks for today
            TextView noItemsTextView = new TextView(requireContext());
            noItemsTextView.setText("Completaste tus tareas!");
            noItemsTextView.setTextSize(12);
            binding.nextCheckItemLayout.addView(noItemsTextView);
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

    // Method to handle date selection from the calendar
    public void onDateSelected(int day, int month, int year) {
        // Update todayDate with the selected date
        todayDate = makeDateString(day, month, year);

        try {
            // Re-inflate the layout to refresh the reminder frame
            LayoutInflater inflater = LayoutInflater.from(requireContext());
            AgregarrecordatorioscuadradosBinding agregarBinding = AgregarrecordatorioscuadradosBinding.inflate(inflater, binding.reminderFrame, false);

            // Clear any previous views
            binding.reminderFrame.removeAllViews();
            binding.reminderFrame.addView(agregarBinding.getRoot());

            // Hide elements as before
            agregarBinding.fechaview.setVisibility(View.GONE);
            agregarBinding.tiponotaSpinner.setVisibility(View.GONE);
            agregarBinding.textoRecord.setTextSize(13);

            // Load the checklist items for the selected date
            loadChecklistItemsForToday();

            // Load the reminder for the new date
            loadRecordatorioForToday(agregarBinding);  // Ensure reminder is loaded after date change

        } catch (Exception e) {
            Log.e("CalendarioFragment", "Error updating view on date selection", e);
        }
    }
}
