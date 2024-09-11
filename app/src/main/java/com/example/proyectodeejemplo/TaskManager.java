package com.example.proyectodeejemplo;

import android.app.Dialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class TaskManager {

    private Context context;
    private RadioGroup RG;
    private TextView congrats;
    private ImageView img;
    private ProgressBar PB;
    private int contadorTareas = 0;
    private static final int MAX_TAREAS = 5;
    private GifImageView conf;

    public TaskManager(Context context, GifImageView gifImageView, RadioGroup taskgroup, TextView congrats, ImageView img, ProgressBar PB) {
        this.context = context;
        this.RG = taskgroup;
        this.congrats = congrats;
        this.conf = gifImageView;
        this.img = img;
        this.PB = PB;
    }

    private List<CheckBox> tareas = new ArrayList<>();

    public void agregarTarea(String textoTarea, EditText campoTexto, ProgressBar PB) {
        if (contadorTareas < MAX_TAREAS) {
            if (!textoTarea.isEmpty()) {
                CheckBox cb = new CheckBox(context);
                cb.setText(textoTarea);

                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int terminadas = 0;
                        for (CheckBox tarea : tareas){
                            if(tarea.isChecked()){
                                terminadas++;
                                PB.setMax(tareas.size());

                            }
                        }
                        PB.setProgress(terminadas);
                        verificarTareasCompletas();
                        }

                });


                RG.addView(cb, 0);
                tareas.add(cb);
                contadorTareas++;
                campoTexto.setText("");
            } else {
                Toast.makeText(context, "Ingresa una tarea!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Se ha alcanzado el máximo de tareas", Toast.LENGTH_SHORT).show();
        }
    }

    //esto me lo robé
    private void verificarTareasCompletas() {
        //Inicializa una variable para rastrear si todas las casillas de verificación están marcadas
        boolean todasMarcadas = true;

        //Itera a través de todos los elementos del grupo de vistas RG
        for (int i = 0; i < RG.getChildCount(); i++) {
            //Obtiene la vista en la posición actual del grupo de vistas RG
            View view = RG.getChildAt(i);

            //Verifica si la vista es una instancia de CheckBox
            if (view instanceof CheckBox) {
                // Convierte la vista en un CheckBox
                CheckBox cb = (CheckBox) view;

                // Si el CheckBox no está marcado, establece todasMarcadas en false y rompe el ciclo
                if (!cb.isChecked()) {
                    todasMarcadas = false;
                    break;
                }
            }
        }

        if (todasMarcadas) {
            congrats.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            conf.setVisibility(View.VISIBLE);
            celebrate();

            new Handler().postDelayed(new Runnable() {
                //nueva instancia de una clase anónima que implementa la interfaz runnable
                //y el método que se ejecutará pasado A. el tiempo determinado
                //otra vez esto se podría hacer con función de flecha pero XD lo dejé así pq así lo enseñaron ok
                @Override
                public void run() {
                    congrats.setVisibility(View.INVISIBLE);
                    img.setVisibility(View.INVISIBLE);
                    conf.setVisibility(View.INVISIBLE);
                    PB.setProgress(0);
                    limpiarTareas();

                }
                //B. 3 segundos en este caso
            }, 3000);
        }
    }


    private void limpiarTareas() {
        for (CheckBox cb : tareas) {
            RG.removeView(cb);
        }

        RG.clearAnimation();
        tareas.clear();
        contadorTareas = 0;
    }
    public void celebrate(){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.yipi);
        mediaPlayer.start();

    }

}
