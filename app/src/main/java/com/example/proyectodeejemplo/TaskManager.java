package com.example.proyectodeejemplo;


import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.proyectodeejemplo.CheckListYIPPIEEEFragment;

import pl.droidsonroids.gif.GifImageView;

import android.util.Log;

public class TaskManager {
    private Context context;
    private RadioGroup RG;
    private ProgressBar PB;
    private int contadorTareas = 0;
    private static final int MAX_TAREAS = 8;
    private String fechaDeHoy;
    private DatabaseManager dbManager;
    private List<CheckBox> tareas = new ArrayList<>();
    private List<ChecklistItem> checklistItems;
    private CelebrationListener celebrationListener;
    private TextView PorcentajeBarra;
    private int terminadas;

    public interface CelebrationListener {
        void celebrate();
    }

    // Constructor
    public TaskManager(Context context, RadioGroup taskgroup, ProgressBar PB, String fechaDeHoy, CelebrationListener listener,TextView PorcentajeBarra) {
        Log.d("TaskManager", "Initializing TaskManager with date: " + fechaDeHoy);
        this.context = context;
        this.RG = taskgroup;
        this.PB = PB;
        this.fechaDeHoy = fechaDeHoy;
        this.celebrationListener = listener;
        this.PorcentajeBarra = PorcentajeBarra;

        try {
            dbManager = new DatabaseManager(context);
            checklistItems = dbManager.getAllItemsByFecha(fechaDeHoy);
        } catch (Exception e) {
            Log.e("TaskManager", "Error initializing DatabaseManager: " + e.getMessage(), e);
        }
    }

    public void cargarItems() {
        Log.d("TaskManager", "Loading items for date: " + fechaDeHoy);

        try {
            RG.removeAllViews();
            tareas.clear();


            checklistItems = dbManager.getAllItemsByFecha(fechaDeHoy);
            if (checklistItems == null) {
                Log.w("TaskManager", "No items found for today's date.");
                checklistItems = new ArrayList<>();
            }

            Log.d("TaskManager", "Tasks found: " + checklistItems.size());
            PB.setMax(checklistItems.size());

            int completedTasks = 0;

            for (ChecklistItem item : checklistItems) {
                Log.d("TaskManager", "Task loaded: " + item.getTexto() + ", Completed: " + item.getEstado());

                CheckBox cb = new CheckBox(context);
                cb.setTextColor(context.getResources().getColor(R.color.onTertiary));
                cb.setTextSize(24);
                cb.setText(item.getTexto());
                cb.setChecked(item.getEstado());
                RG.addView(cb, 0);
                tareas.add(cb);

                if (item.getEstado()) {
                    completedTasks++;
                }

                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setEstado(isChecked);
                        dbManager.updateItem(item);
                        Log.d("TaskManager", "Task updated in DB: " + item.getTexto() + " - " + (isChecked ? "Completed" : "Not Completed"));

                        //tachar si la checkbox esta marcada
                        if (isChecked) {
                            cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            //Color para cuando se marque una tarea
                            cb.setBackgroundColor(context.getResources().getColor(R.color.muted_brown));

                        } else {
                            cb.setPaintFlags(cb.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                            cb.setBackgroundColor(context.getResources().getColor(R.color.Secondary));
                        }
                         terminadas = 0;
                        for (CheckBox tarea : tareas) {
                            if (tarea.isChecked()) {
                                terminadas++;

                            }
                        }
                        actualizarProgreso();

                        PB.setProgress(terminadas);


                        verificarTareasCompletas();
                    }
                });

            }

            PB.setProgress(completedTasks);
            actualizarProgreso();
            Log.d("TaskManager", "Initial progress set to: " + completedTasks);
        } catch (Exception e) {
            Log.e("TaskManager", "Error in cargarItems: " + e.getMessage(), e);
        }
    }

    public void agregarTarea(String textoTarea, EditText campoTexto, ProgressBar PB) {
        Log.d("TaskManager", "Adding new task: " + textoTarea);

        if (contadorTareas < MAX_TAREAS) {
            if (!textoTarea.isEmpty()) {
                ChecklistItem newTask = new ChecklistItem();
                newTask.setTexto(textoTarea);
                newTask.setFecha(fechaDeHoy);
                newTask.setEstado(false);

                try {
                    dbManager.insertItem(newTask);
                    Log.d("TaskManager", "New task added: " + newTask.getTexto());
                    cargarItems();
                    actualizarProgreso();
                    RG.invalidate(); // Force redraw
                    campoTexto.setText("");
                } catch (Exception e) {
                    Log.e("TaskManager", "Error inserting task into DB: " + e.getMessage(), e);
                }
            } else {
                Toast.makeText(context, "Ingresa una tarea!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Se ha alcanzado el máximo de tareas", Toast.LENGTH_SHORT).show();
        }
    }

    private void verificarTareasCompletas() {
        boolean todasMarcadas = true; // Start with the assumption that all are checked

        for (int i = 0; i < RG.getChildCount(); i++) {
            View view = RG.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox cb = (CheckBox) view;
                if (!cb.isChecked()) {
                    todasMarcadas = false;
                    break;
                }
            }
        }

        if (todasMarcadas && PB.getProgress() == PB.getMax()) {
            Log.d("TaskManager", "All tasks completed and progress bar is full!");
            if (celebrationListener != null) {
                celebrationListener.celebrate();
            }
        }
    }

    public void LimpiarTareas(){
        dbManager.EliminarTodosItems();
        cargarItems();
    }
    private void actualizarProgreso() {
        terminadas = 0;
        for (CheckBox tarea : tareas) {
            if (tarea.isChecked()) {
                terminadas++;
            }
        }



        int PorcentajeTerminado = checklistItems.size() > 0
                ? (terminadas * 100) / checklistItems.size()
                : 0; // Evitar división por cero.

        PorcentajeBarra.setText(PorcentajeTerminado + "%");
    }


}

