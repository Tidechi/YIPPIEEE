package com.example.proyectodeejemplo;

import android.graphics.Color;
import android.os.Bundle;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectodeejemplo.databinding.OpeningscrnBinding;

public class Openingscrn extends AppCompatActivity {

    OpeningscrnBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = OpeningscrnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int nightmodeflags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch(nightmodeflags){
            case Configuration.UI_MODE_NIGHT_YES:
                //el codigo que ocurrira si esta en night mode
                binding.nombreInput.setHintTextColor(Color.BLACK);
                binding.nombreInput.setTextColor(Color.BLACK);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                //el codigo que ocurrira si no esta en night mode
                binding.nombreInput.setHintTextColor(Color.BLACK);
                binding.nombreInput.setTextColor(Color.BLACK);
                break;
        }
    }


}
