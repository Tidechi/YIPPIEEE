package com.example.proyectodeejemplo;

import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodeejemplo.databinding.ActivityCheckListYippieeeBinding;

public class CheckListYIPPIEEEFragment extends Fragment {
    private ActivityCheckListYippieeeBinding binding;
    private TaskManager taskManager;
    private WebView webview;
    private Button botonCrear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize the binding and inflate the layout
        binding = ActivityCheckListYippieeeBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        // Initialize taskManager with getContext() instead of this
        taskManager = new TaskManager(getContext(), binding.confetti, binding.TASKRG, binding.CONGRATS, binding.YIPPIEEE, binding.PBTasks);

        // Setup the button and its listener
        botonCrear = binding.OK;
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTarea = binding.add.getText().toString();
                taskManager.agregarTarea(txtTarea, binding.add, binding.PBTasks);
            }
        });

        return v;
    }
}
