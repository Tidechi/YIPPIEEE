package com.example.proyectodeejemplo;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectodeejemplo.databinding.UserViewBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class UserView extends Fragment {
    private UserViewBinding binding;
    private DatabaseManager dbManager;
    private DateManager dateManager;
    private DatePickerDialog datePickerDialog;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PERFS = "Preferencias";
    private static final String IMAGE = "img";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserViewBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        dbManager = new DatabaseManager(getContext());
        dateManager = new DateManager();
        initDatePicker();

        // Initialize data from the database
        actualizarDatos();

        // Calendario
        String dia = dateManager.getDia();
        binding.dia.setText(dia);
        String mes = dateManager.getMesLetras();
        binding.mes.setText(mes);

        // Cargar la imagen guardada si existe
        SharedPreferences prefs = getContext().getSharedPreferences(PERFS, Context.MODE_PRIVATE);
        String imgguardada = prefs.getString(IMAGE, null);
        if (imgguardada != null) {
            File imageFile = new File(imgguardada);
            if (imageFile.exists()) {
                binding.foto.setImageURI(Uri.fromFile(imageFile)); // Acá le pasa la foto al ImageButton
            }
        }

        binding.Editcum.setOnClickListener(v1 -> datePickerDialog.show());

        binding.edit.setOnClickListener(v1 -> enableEditing());
        binding.guardar.setOnClickListener(v2 -> saveChanges());

        binding.foto.setOnClickListener(v1 -> {
            // Para que el usuario asigne la foto desde galería
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        return v;
    }

    private void actualizarDatos() {
        Usuario usuario = dbManager.getUsuarioById(0); // Directly fetch user with ID 0
        if (usuario != null) {
            Log.d("Usuario", "Fetched user: " + usuario.getNombre() + ", signo: " + usuario.getSigno());
            binding.nombre.setText(usuario.getNombre());
            binding.sig.setText(usuario.getSigno());  // Ensure 'signo' is displayed correctly
            binding.Editcol.setText(usuario.getColor());

            // Load 'cumple' as empty if it's blank in the database
            String cumpleValue = usuario.getCumple();
            if (cumpleValue == null || cumpleValue.isEmpty()) {
                binding.Editcum.setText("");
            } else {
                binding.Editcum.setText(cumpleValue);
            }
        } else {
            Log.e("Usuario", "No user found with ID 0");
        }
    }



    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);

            binding.Editcum.setText(date);

            String signo = getZodiacSign(day, month);

            binding.sig.setText(signo);
        };

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private String getZodiacSign(int day, int month) {
        if (month == 1) return (day >= 20) ? "Acuario" : "Capricornio";
        if (month == 2) return (day >= 19) ? "Piscis" : "Acuario";
        if (month == 3) return (day >= 21) ? "Aries" : "Piscis";
        if (month == 4) return (day >= 21) ? "Tauro" : "Aries";
        if (month == 5) return (day >= 21) ? "Géminis" : "Tauro";
        if (month == 6) return (day >= 21) ? "Cáncer" : "Géminis";
        if (month == 7) return (day >= 23) ? "Leo" : "Cáncer";
        if (month == 8) return (day >= 23) ? "Virgo" : "Leo";
        if (month == 9) return (day >= 23) ? "Libra" : "Virgo";
        if (month == 10) return (day >= 23) ? "Escorpio" : "Libra";
        if (month == 11) return (day >= 22) ? "Sagitario" : "Escorpio";
        return (day >= 22) ? "Capricornio" : "Sagitario";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uriImg = data.getData();
            binding.foto.setImageURI(uriImg);

            try {
                String img = saveImageToInternalStorage(uriImg);

                SharedPreferences prefs = getContext().getSharedPreferences(PERFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(IMAGE, img);
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveImageToInternalStorage(Uri imageUri) throws IOException {
        Bitmap BM = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        File file = new File(getContext().getFilesDir(), "fotodeperfil");
        FileOutputStream fos = new FileOutputStream(file);
        BM.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.close();
        return file.getAbsolutePath();
    }

    private void enableEditing() {
        binding.guardar.setVisibility(View.VISIBLE);
        binding.Editcum.setEnabled(true);
        binding.Editcol.setEnabled(true);
        binding.nombre.setEnabled(true);
    }

    private void saveChanges() {
        String colorfav = binding.Editcol.getText().toString();
        String cumple = binding.Editcum.getText().toString();
        String nbre = binding.nombre.getText().toString();

        // Check if fields are empty before updating
        if (!nbre.isEmpty() && !colorfav.isEmpty() && !cumple.isEmpty()) {
            String[] fechaParts = cumple.split("/");  // Ensure cumple is in "dd/mm/yyyy"
            if (fechaParts.length >= 2) {
                int Dia = Integer.parseInt(fechaParts[0]);
                int Mes = Integer.parseInt(fechaParts[1]);
                String signo = getZodiacSign(Dia, Mes);  // Calculate the zodiac sign

                // Log signo before updating
                Log.d("Usuario", "Calculated signo: " + signo);

                // Update the database directly without checking for nulls
                Usuario usuario = new Usuario(0, nbre, signo, colorfav, cumple);  // Create new user with updated fields
                int result = dbManager.updateUsuario(usuario); // Update all fields in one go
                if (result > 0) {
                    Log.d("Usuario", "Usuario actualizado con id: 0");

                    // Show a toast indicating the update was successful
                    Toast.makeText(getActivity(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();

                    actualizarDatos(); // Refresh the data after saving
                } else {
                    Log.e("Usuario", "Error al actualizar el usuario con id: 0");
                }
            } else {
                Log.e("Usuario", "Formato de fecha inválido en el campo de cumpleaños.");
            }
        } else {
            Log.e("Usuario", "Campos vacíos, por favor completa todos los campos.");
        }

        disableEditing();
    }

    private void disableEditing() {
        binding.guardar.setVisibility(View.GONE);
        binding.Editcum.setEnabled(false);
        binding.Editcol.setEnabled(false);
        binding.nombre.setEnabled(false);
    }
}
