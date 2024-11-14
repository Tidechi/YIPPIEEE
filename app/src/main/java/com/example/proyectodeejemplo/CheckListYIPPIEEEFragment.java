package com.example.proyectodeejemplo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.ActivityCheckListYippieeeBinding;

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

        binding.LimpiarItemsCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoCustom();
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

    private void mostrarDialogoCustom() {
        // Crea una instancia del dialogo y configura su diseño
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.mensaje_personalizado);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView textView = dialog.findViewById(R.id.TextoMovi);
        animarTextoLetraPorLetra(textView, "Está seguro que desea eliminar todas sus tareas?", 100);

        Button btnConfirmar = dialog.findViewById(R.id.botonSi);
        Button btnCancelar = dialog.findViewById(R.id.botonNo);


        btnConfirmar.setOnClickListener(v -> {
            taskManager.LimpiarTareas();
            Toast.makeText(requireContext(), "Tareas eliminadas", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());


        dialog.show();
    }
    private void animarTextoLetraPorLetra(TextView textView, String texto, long retraso) {
        textView.setText("");
        final int longitudTexto = texto.length();
        final Handler handler = new Handler();

        for (int i = 0; i < longitudTexto; i++) {
            final int index = i;
            handler.postDelayed(() -> {
                // Agrega una letra a la vez
                textView.setText(textView.getText().toString() + texto.charAt(index));
            }, retraso * i); // Aumenta el retraso para cada letra
        }
    }

}
