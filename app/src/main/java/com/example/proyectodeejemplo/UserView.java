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
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectodeejemplo.databinding.UserViewBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class UserView extends Fragment {
    private UserViewBinding binding;
    private DatabaseManager dbManager;
    private DateManager dateManager;
    private DatePickerDialog datePickerDialog;
    private String cumpleanhos;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PERFS = "Preferencias";
    private static final String IMAGE = "img";

    private ArrayList<Usuario> usuarioList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserViewBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        //Cargar la bdd
        dbManager = new DatabaseManager(getContext());
        dateManager = new DateManager();

        //Este metodo solo consigue una fecha "date",
        initDatePicker();

        //Para que quiero una lista de "todos los usuarios??" weird, unnecesary
        // usuarioList = dbManager.getAllUsers();

        //mejor un metodo que me de el unico usuario de la app, re chad
        cargarDatosUsuario();

        if (binding.Editcum.toString() ==null){
            binding.Editcum.setText(getTodaysDate());
        }

        // Calendario
        String dia = dateManager.getDia();
        binding.dia.setText(dia);
        String mes = dateManager.getMesLetras();
        binding.mes.setText(mes);

        //Cargar la imagen guardada si existe
        SharedPreferences prefs = getContext().getSharedPreferences(PERFS, Context.MODE_PRIVATE);
        String imgguardada = prefs.getString(IMAGE, null);
        if (imgguardada != null) {
            File imageFile = new File(imgguardada);
            if (imageFile.exists()) {
                binding.foto.setImageURI(Uri.fromFile(imageFile)); //acá le pasa la foto al imagebutton
            }
        }


        binding.Editcum.setOnClickListener(v1 -> datePickerDialog.show());

        //Desde acá tamos mal
        binding.edit.setOnClickListener(v1 -> enableEditing());

        binding.guardar.setOnClickListener(v2 -> {
            Usuario elSujeto = dbManager.getUsuarioById(1); // Always fetch user with ID 1

            if (elSujeto != null) { // Check if user exists
                // Update user information
                String nombre = binding.nombre.getText().toString();
                String cumple = binding.Editcum.getText().toString();
                String colorFav = binding.Editcol.getText().toString();

                // Parse and calculate zodiac sign
                String[] fechaParts = cumple.split("/");
                int Dia = Integer.parseInt(fechaParts[0]);
                int Mes = Integer.parseInt(fechaParts[1]);
                String signo = getZodiacSign(Dia, Mes);

                elSujeto.setNombre(nombre);
                elSujeto.setCumple(cumple);
                elSujeto.setcolorfav(colorFav);
                elSujeto.setSigno(signo);
                dbManager.updateUsuario(elSujeto); // Save updates

                Toast.makeText(getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No se encontró el usuario para actualizar", Toast.LENGTH_SHORT).show();
            }

            // Disable editing and reload data
            binding.guardar.setVisibility(View.GONE);
            binding.Editcum.setEnabled(false);
            binding.Editcol.setEnabled(false);
            binding.nombre.setEnabled(false);
            cargarDatosUsuario();
        });

        binding.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Para que el usuario asigne la foto desde galeria
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        return v;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1; // Adjust month (DatePicker uses 0-based indexing)
                String date = makeDateString(day, month, year);

                // Update Editcum TextView with the selected date
                binding.Editcum.setText(date);

                // Get the zodiac sign for the selected date
                String zodiacSign = getZodiacSign(day, month);

                // Update signo TextView with the zodiac sign
                binding.sig.setText(zodiacSign);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        // Initialize the DatePickerDialog
        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, year, month, day);
    }

    //Metodo para mostrar los datos de la bdd en la interfaz
    private void cargarDatosUsuario() {
        Usuario elSujeto = dbManager.getUsuarioById(1);

        if (elSujeto != null) {
            binding.nombre.setText(elSujeto.getNombre());
            binding.Editcum.setText(elSujeto.getCumple());
            binding.Editcol.setText(elSujeto.getcolorfav());
            binding.sig.setText(elSujeto.getSigno());
        } else {
            Toast.makeText(getContext(), "No se encontraron datos de usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }


    //esto no lmao
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

    //Método para procesar el resultado después de que el usuario selecciona la imagen desde la galería
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Verifica que la solicitud provenga de la selección de imagen y que el resultado sea exitoso
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            //Obtiene la URI de la imagen seleccionada
            //Uri = especie de dirección que apunta a recursos como archivos, imágenes, o
            // ualquier contenido accesible en el dispositivo o en la web
            Uri uriImg = data.getData();

            //Muestra la imagen seleccionada en el ImageView de la interfaz
            binding.foto.setImageURI(uriImg);

            //Guarda la imagen en el almacenamiento interno y registra la ruta en SharedPreferences
            try {
                //Llama al método para guardar la imagen y obtiene la ruta del archivo guardado
                String img = saveImageToInternalStorage(uriImg);

                //Accede a SharedPreferences para guardar la ruta de la imagen
                SharedPreferences prefs = getContext().getSharedPreferences(PERFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                //Guarda la ruta del archivo de imagen en SharedPreferences usando una clave específica
                editor.putString(IMAGE, img);
                editor.apply(); //aplica los cambios en SharedPreferences de manera asíncrona
            } catch (IOException e) {
                e.printStackTrace(); //manejo de cualquier error de entrada o salida
            }
        }
    }

    //Método para guardar la imagen seleccionada en el almacenamiento interno de la aplicación
    private String saveImageToInternalStorage(Uri imageUri) throws IOException {
        //Convierte la URI de la imagen en un objeto Bitmap
        Bitmap BM = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

        //Crea un archivo en el almacenamiento interno (fotodeperfil)
        File file = new File(getContext().getFilesDir(), "fotodeperfil");

        //Abre un flujo de salida para escribir en el archivo creado
        FileOutputStream fos = new FileOutputStream(file);

        //Comprime y guarda el Bitmap en el archivo como JPEG con calidad 100%
        BM.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        //Cierra el flujo de salida para liberar recursos
        fos.close();

        //Retorna la ruta completa del archivo guardado
        return file.getAbsolutePath();
    }

    //nota pa quien sea que lea esto: eliminar los comentarios explicando pq se ve como si fuese más codigo de lo que es

    private void enableEditing() {
        // Show the save button and enable fields for editing
        binding.guardar.setVisibility(View.VISIBLE);
        binding.Editcum.setEnabled(true);
        binding.Editcol.setEnabled(true);
        binding.nombre.setEnabled(true);
    }

    public void onDateSelected(int day, int month, int year) {
        cumpleanhos = makeBirthdayString(day, month, year);
    }

    private String makeBirthdayString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }
}