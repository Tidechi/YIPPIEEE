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

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PERFS = "Preferencias";
    private static final String IMAGE = "img";

    private ArrayList<Usuario> usuarioList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserViewBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        dbManager = new DatabaseManager(getContext());
        dateManager = new DateManager();
        initDatePicker();

        usuarioList = dbManager.getAllUsers();
        actualizarDatos();

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
        binding.edit.setOnClickListener(v1 -> {
            binding.guardar.setVisibility(View.VISIBLE);
            binding.Editcum.setEnabled(true);
            binding.Editcol.setEnabled(true);
            binding.nombre.setEnabled(true);

            binding.guardar.setOnClickListener(v2 -> {
                String colorfav = binding.Editcol.getText().toString();
                String cumple = binding.Editcum.getText().toString();
                String nbre = binding.nombre.getText().toString();

                String[] fechaParts = cumple.split("/");
                int Dia = Integer.parseInt(fechaParts[0]);
                int Mes = Integer.parseInt(fechaParts[1]);

                String signo = getZodiacSign(Dia, Mes);

                //Esto me lo robé PORQUE NO SÉ QUÉ CARAJO MÁS HACER y aún no funciona lol
                if (!usuarioList.isEmpty()) {
                    Usuario usuario = usuarioList.get(1);

                    // Asegúrate de que el id del usuario es válido
                    if (usuario.getId() == 0) {
                        Log.e("Usuario", "El ID del usuario es inválido.");
                        return;
                    }

                    // Actualizamos los datos del usuario con el ID 1
                    usuario.setNombre(nbre);
                    usuario.setSigno(signo);
                    usuario.setColor(colorfav);
                    usuario.setCumple(cumple);

                    // Verificamos que los valores no sean null o vacíos antes de hacer la actualización
                    if (usuario.getNombre() == null || usuario.getNombre().isEmpty() || usuario.getColor() == null || usuario.getColor().isEmpty() || usuario.getSigno() == null || usuario.getSigno().isEmpty() || usuario.getCumple() == null || usuario.getCumple().isEmpty()) {
                        Log.e("Usuario", "Algunos campos están vacíos.");
                        return;
                    }
                    int result = dbManager.updateUsuario(usuario);

                    if (result > 0) {
                        Log.d("Usuario", "Usuario actualizado con id: " + usuario.getId());
                        usuarioList = dbManager.getAllUsers(); // Recargamos la lista de usuarios desde la base de datos
                        actualizarDatos();
                    } else {
                        Log.e("Usuario", "Error al actualizar el usuario con id: " + usuario.getId());
                    }

                    //Escondemos el botón de guardar y se desactiva la mmda para editar
                    binding.guardar.setVisibility(View.GONE);
                    binding.Editcum.setEnabled(false);
                    binding.Editcol.setEnabled(false);
                    binding.nombre.setEnabled(false);
                } else {
                    Log.e("Usuario", "La lista de usuarios está vacía");
                }
            });
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



    //Método para actualizar los datos de perfil en la vista
    private void actualizarDatos() {
        if (usuarioList != null && usuarioList.size() > 0) {
            Usuario primerUsuario = usuarioList.get(0);
            binding.nombre.setText(primerUsuario.getNombre());
            binding.sig.setText(primerUsuario.getSigno());
            binding.Editcol.setText(primerUsuario.getColor());
            binding.Editcum.setText(primerUsuario.getCumple());
        }
    }

    //esto es tuyo XDDDDDDDDDDDDDD
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
}

