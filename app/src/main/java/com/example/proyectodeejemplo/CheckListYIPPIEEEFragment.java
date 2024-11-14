package com.example.proyectodeejemplo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.ActivityCheckListYippieeeBinding;
import com.google.android.material.color.DynamicColors;

import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class CheckListYIPPIEEEFragment extends Fragment implements TaskManager.CelebrationListener {
    private ActivityCheckListYippieeeBinding binding;
    private TaskManager taskManager;
    private WebView webview;
    private Button botonCrear;
    private GifImageView conf;
    private ImageView img;
    private TextView congrats;
    private ImageView YIPPIEEE;
    private GifImageView confetti;
    public String fechaDeHoy;
    private DatePickerDialog datePickerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize the binding and inflate the layout
        binding = ActivityCheckListYippieeeBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        // Set today's date and display it
        fechaDeHoy = getTodaysDate();
        binding.fechaChecklist.setText(fechaDeHoy);

        // Initialize taskManager with getContext() instead of this
        taskManager = new TaskManager(getContext(), binding.TASKRG, binding.PBTasks, fechaDeHoy, this);
        taskManager.cargarItems();

        // Setup the button and its listener
        botonCrear = binding.OK;
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTarea = binding.add.getText().toString();
                taskManager.agregarTarea(txtTarea, binding.add, binding.PBTasks);
            }
        });

        binding.fechaChecklist.setOnClickListener(view -> {
            if (isAdded()) {
                showDatePicker(view);
            } else {
                Log.e("AgregarNotaFragment", "Fragment not attached to activity");
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload checklist items and update progress bar when fragment becomes visible
        taskManager.cargarItems();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                binding.fechaChecklist.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, year, month, day);
    }

    public void showDatePicker(View view) {
        if (datePickerDialog != null) {
            datePickerDialog.show();
        } else {
            Log.e("CheckListYIPPIEEEFragment", "DatePickerDialog is not initialized");
        }
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    @Override
    public void celebrate() {
        // Play the celebration sound and show confetti
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.yipi);
        mediaPlayer.start();
        binding.conf.setVisibility(View.VISIBLE);
        binding.YIPPIEEE.setVisibility(View.VISIBLE);
    }

}
