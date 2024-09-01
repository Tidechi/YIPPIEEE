package com.example.proyectodeejemplo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.ActivityCheckListYippieeeBinding;

public class CheckListYIPPIEEE extends AppCompatActivity {
    ActivityCheckListYippieeeBinding binding;
    TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckListYippieeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        taskManager = new TaskManager(this, binding.TASKRG, binding.CONGRATS, binding.YIPPIEEE, binding.PBTasks);

        //esto es exactamente lo mismo que el código de abajo pero con la función de flecha
        //recuerden borrar esto pq, aja, no lo enseñaron así zzzz

        //binding.OK.setOnClickListener(v -> {
            //String txtTarea = binding.add.getText().toString();
            //taskManager.agregarTarea(txtTarea, binding.add);
        //});

        binding.OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTarea = binding.add.getText().toString();
                taskManager.agregarTarea(txtTarea, binding.add, binding.PBTasks);
            }
        });
    }
}
