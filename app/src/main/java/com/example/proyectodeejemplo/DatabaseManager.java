package com.example.proyectodeejemplo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private MyDatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    //METODOS PARA LAS NOTAS #ayuda
    //para insertar una nota
    public void insertNota(Nota nota) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", nota.getFecha());
        values.put("titulo", nota.getTitulo());
        values.put("texto", nota.getTexto());
        values.put("design", nota.getDesign());
        long result = db.insert("Notas", null, values);
        if (result == -1) {
            Log.e("Database", "No se inserto nada...");
        } else {
            Log.d("Database", "Nota insertada con id: " + result);
        }
        db.close();
    }

    //Obtener todas las notas(LISTO)
    public ArrayList<Nota> getAllNotas() {
        ArrayList<Nota> notaList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Notas", null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String fecha = cursor.getString(1);
                String titulo = cursor.getString(2);
                String texto = cursor.getString(3);
                int design = cursor.getInt(4);
                Nota nota = new Nota(id, fecha, titulo, texto, design);
                notaList.add(nota);
                Log.d("Database", "Nota encontrada: " + nota.getTitulo());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("Database", "Total notes retrieved: " + notaList.size());
        return notaList;
    }

    //Actualizar nota
    public void updateNota(Nota nota){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", nota.getFecha());
        values.put("titulo", nota.getTitulo());
        values.put("texto", nota.getTexto());
        values.put("design", nota.getDesign());
        db.update("Notas", values, "id = ?", new String[]{String.valueOf(nota.getId())});
        db.close();
    }

    //Eliminar nota
    public void deleteNota(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Notas", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Encontrar una nota por id
    public Nota findNotaById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Notas WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {

            String fecha = cursor.getString(1);
            String titulo = cursor.getString(2);
            String texto = cursor.getString(3);
            int design = cursor.getInt(4);
            Nota nota = new Nota(id, fecha, titulo, texto, design);
            cursor.close();
            db.close();
            return nota;
        }
        //Si no hay una nota con ese id nos debe dar null...
        cursor.close();
        db.close();
        return null;
    }

    //Encontrar una nota por fecha (WIP)
    public Nota findNotaByFecha(String fecha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Notas WHERE fecha = ?", new String[]{String.valueOf(fecha)});

        if (cursor != null && cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(1));
            String titulo = cursor.getString(2);
            String texto = cursor.getString(3);
            int design = cursor.getInt(4);
            Nota nota = new Nota(id, fecha, titulo, texto, design);
            cursor.close();
            db.close();
            return nota;
        }
        //Si no hay una nota con esa fecha nos debe dar null...
        cursor.close();
        db.close();
        return null;
    }

    //encontrar nota por texto (WIP)
    public Nota findNotaByTexto(String texto) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Notas WHERE texto LIKE ?", new String[]{"%" + texto + "%"});

        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
            String textoEncontrado = cursor.getString(cursor.getColumnIndexOrThrow("texto"));
            int design = cursor.getInt(cursor.getColumnIndexOrThrow("design"));

            Nota nota = new Nota(id, fecha, titulo, textoEncontrado, design);

            cursor.close();
            db.close();
            return nota;
        }
        //Si no hay una nota con ese texto nos debe dar null...
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    //Metodos para los recordatorios #ayuda

    //Metodo para insertar recordatorios
    public void insertRecordatorio(Recordatorio recordatorio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tiponota", recordatorio.getTiponota());
        values.put("sticker", recordatorio.getSticker());
        values.put("fecha", recordatorio.getFecha());
        values.put("texto", recordatorio.getTexto());
        long result = db.insert("Recordatorios", null, values);
        if (result == -1) {
            Log.e("Database", "No se inserto nada...");
        } else {
            Log.d("Database", "Recordatorio insertado con id: " + result);
        }
        db.close();

    }

    //Metodo para obtener todos los recordatorios
    public ArrayList<Recordatorio> getAllRecordatorios() {
        ArrayList<Recordatorio> recordatorioList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Recordatorios", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int tiponota = cursor.getInt(1);
                int sticker = cursor.getInt(2);
                String fecha = cursor.getString(3);
                String texto = cursor.getString(4);
                Recordatorio recordatorio = new Recordatorio(id, tiponota, sticker, fecha, texto);
                recordatorioList.add(recordatorio);
                Log.d("Database", "Recordatorio encontrado: " + recordatorio.getTexto());
                } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("Database", "Total recordatorios retrieved: " + recordatorioList.size());
        return recordatorioList;
    }

    //Metodo para actualizar recordatorios
    public void updateRecordatorio(Recordatorio recordatorio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tiponota", recordatorio.getTiponota());
        values.put("sticker", recordatorio.getSticker());
        values.put("texto", recordatorio.getTexto());

        // Update based on fecha instead of id
        int rowsUpdated = db.update("Recordatorios", values, "fecha = ?", new String[]{recordatorio.getFecha()});
        if (rowsUpdated > 0) {
            Log.d("Database", "Recordatorio actualizado para la fecha: " + recordatorio.getFecha());
        } else {
            Log.e("Database", "No se actualizó ningún recordatorio...");
        }

        db.close();
    }

    //Eliminar recordatorio
    public void deleteRecordatorio(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Recordatorios", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Encontrar recordatorio por id
    public void findRecordatorioById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Recordatorios WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            int tiponota = cursor.getInt(1);
            int sticker = cursor.getInt(2);
            String fecha = cursor.getString(3);
            String texto = cursor.getString(4);
            Recordatorio recordatorio = new Recordatorio(id, tiponota, sticker, fecha, texto);
            cursor.close();
            db.close();
            Log.d("Database", "Recordatorio encontrado: " + recordatorio.getTexto());
        }
        cursor.close();
        db.close();
        Log.d("Database", "No se encontro un recordatorio con ese id...");
    }

    //Encontrar recordatorio por fecha
    public boolean findRecordatorioByFecha(String fecha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Recordatorios WHERE fecha = ?", new String[]{fecha});
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int tiponota = cursor.getInt(1);
            int sticker = cursor.getInt(2);
            String texto = cursor.getString(4);
            Recordatorio recordatorio = new Recordatorio(id, tiponota, sticker, fecha, texto);
            cursor.close();
            db.close();
            Log.d("Database", "Recordatorio encontrado: " + recordatorio.getTexto());
            return true;
        } else {
        cursor.close();
        db.close();
        Log.d("Database", "No se encontro un recordatorio con esa fecha...");
        }
        return false;
    }

    public Recordatorio findRecordatorioByFechaGPT(String fecha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Recordatorios WHERE fecha = ?", new String[]{fecha});
        Recordatorio recordatorio = null;

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int tiponota = cursor.getInt(1);
            int sticker = cursor.getInt(2);
            String texto = cursor.getString(4);
            recordatorio = new Recordatorio(id, tiponota, sticker, fecha, texto);
        }

        if (cursor != null) cursor.close();
        db.close();
        return recordatorio; // Returns null if no record found
    }

    //Metodos para la Checklist

    //Agregar item a la tabla de checklist
    public void insertItem(ChecklistItem checklistItem){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", checklistItem.getFecha());
        values.put("texto", checklistItem.getTexto());
        values.put("estado", checklistItem.getEstado());
        long result = db.insert("Checklist", null, values);
        if (result == -1) {
            Log.e("Database", "No se inserto nada...");
        } else {
            Log.d("Database", "Item insertado con id: " + result);
        }
        db.close();
    }

    //Obtener todos los items de la checklist
    public ArrayList<ChecklistItem> getAllItems(){
        ArrayList<ChecklistItem> checklistItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Checklist", null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String fecha = cursor.getString(1);
                String texto = cursor.getString(2);
                boolean estado = cursor.getInt(3) == 1;

                ChecklistItem checklistItem = new ChecklistItem(id, fecha, texto, estado);
                checklistItems.add(checklistItem);
                Log.d("Database", "Item encontrado: " + checklistItem.getTexto());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("Database", "Total items retrieved: " + checklistItems.size());
        return checklistItems;
    }

    //Obtener todos los items de una fecha determinada
    public ArrayList<ChecklistItem> getAllItemsByFecha(String fecha){
        ArrayList<ChecklistItem> checklistItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Checklist WHERE fecha = ?", new String[]{fecha});

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String texto = cursor.getString(2);
                boolean estado = cursor.getInt(3) == 1;
                ChecklistItem checklistItem = new ChecklistItem(id, fecha, texto, estado);
                checklistItems.add(checklistItem);
                Log.d("Database", "Item encontrado: " + checklistItem.getTexto());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("Database", "Total items retrieved: " + checklistItems.size());
        return checklistItems;
    }

    //Marcar un item como completado
    public void marcarComoCompletado(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", 1);
        db.update("Checklist", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Marcar como no completado
    public void marcarComoNoCompletado(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", 0);
        db.update("Checklist", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //actualizar item
    public void updateItem(ChecklistItem checklistItem){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", checklistItem.getFecha());
        values.put("texto", checklistItem.getTexto());
        values.put("estado", checklistItem.getEstado());
        db.update("Checklist", values, "id = ?", new String[]{String.valueOf(checklistItem.getId())});
        db.close();
    }

    public ArrayList<ChecklistItem> getUncheckedItemsByFecha(String fecha) {
        ArrayList<ChecklistItem> checklistItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Checklist WHERE fecha = ? AND estado = 0", new String[]{fecha});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String texto = cursor.getString(2);
                boolean estado = cursor.getInt(3) == 1;
                ChecklistItem checklistItem = new ChecklistItem(id, fecha, texto, estado);
                checklistItems.add(checklistItem);
                Log.d("Database", "Item encontrado: " + checklistItem.getTexto());
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        Log.d("Database", "Total unchecked items retrieved: " + checklistItems.size());
        return checklistItems;

    }

    public void EliminarTodosItems(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Checklist", null, null);
        db.close();

    }

    //Metodos para el mood
    public void insertMood(Mood mood){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", mood.getFecha());
        values.put("estado", mood.getEstado());
        long result = db.insert("Mood", null, values);
        if (result == -1) {
            Log.e("Database", "No se inserto nada...");
        } else {
            Log.d("Database", "Mood insertado con id: " + result);
        }
        db.close();
    }

    public Mood obtenerMoodPorFecha(String fecha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Mood WHERE fecha = ?", new String[]{fecha});
        Mood mood = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String estado = cursor.getString(2);
            mood = new Mood(id, fecha, estado);
        }
        cursor.close();
        db.close();
        return mood;
    }

    public void actualizarMood(Mood mood) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", mood.getEstado());
        db.update("Mood", values, "fecha = ?", new String[]{mood.getFecha()});
        db.close();
    }


    //Método para insertar un usuario
    public void insertUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Force the ID to be 1
        values.put("id", 1);
        values.put("nombre", usuario.getNombre());
        values.put("colorfav", usuario.getcolorfav());
        values.put("cumple", usuario.getCumple());
        values.put("signo", usuario.getSigno());

        long result = db.insertWithOnConflict("Usuario", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        // REPLACE ensures that if there's already a user with id=1, it will overwrite

        if (result == -1) {
            Log.e("Database", "No se pudo insertar el usuario...");
        } else {
            Log.d("Database", "Usuario insertado o reemplazado con id: " + result);
        }
        db.close();
    }

    //Actualizar usuario
    public void updateUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", usuario.getNombre());
        values.put("colorfav", usuario.getcolorfav());
        values.put("cumple", usuario.getCumple());
        values.put("signo", usuario.getSigno());

        int rowsAffected = db.update("Usuario", values, "id = ?", new String[]{"1"});
        if (rowsAffected > 0) {
            Log.d("Database", "Usuario actualizado correctamente.");
        } else {
            Log.d("Database", "No se encontró un usuario para actualizar.");
        }
        db.close();
    }


    public void updateUsuarioFull(int id, String nombre, String colorfav, String cumple, String signo ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nombre", nombre);
        values.put("colorfav", colorfav);
        values.put("cumple", cumple);
        values.put("signo", signo);
        db.update("Usuario", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Encontrar usuario por id

    public Usuario getUsuarioById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE id = ?", new String[]{String.valueOf(id)});
        Usuario usuario = null;

        if (cursor.moveToFirst()) {
            int idCol = cursor.getInt(cursor.getColumnIndex("id"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String signo = cursor.getString(cursor.getColumnIndex("signo"));
            String cumple = cursor.getString(cursor.getColumnIndex("cumple"));
            String colorfav = cursor.getString(cursor.getColumnIndex("colorfav"));
            usuario = new Usuario(idCol, nombre, signo, cumple, colorfav);
        } else {
            Log.d("Database", "No se encontró un usuario con ese ID.");
        }

        cursor.close();
        db.close();
        return usuario;
    }

    //Recupera los usuarios // Esto me lo robé del esclavo pq no estaba funcionando de la manera en que lo estaba haciendo
    public ArrayList<Usuario> getAllUsers() {
        ArrayList<Usuario> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM Usuario", null); // Usando la tabla 'Usuario'

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Recupera los datos de cada columna (asegúrate de que los índices de columna coincidan con tu esquema)
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));

                    Usuario usuario = new Usuario(id, nombre, "", "", "");

                    userList.add(usuario);

                    Log.d("Database", "Usuario encontrado: " + usuario.getNombre() + " con id: " + usuario.getId() + " y cumple: " + usuario.getCumple()+ " y signo: " + usuario.getSigno()+ " y color: " + usuario.getcolorfav());
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Database", "Error al obtener usuarios", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // Para depurar y asegurarnos de que la lista tiene la cantidad correcta de usuarios
        Log.d("Database", "Total usuarios recuperados: " + userList.size());
        return userList;
    }

    // Método para eliminar todos los usuarios
    public void deleteAllUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            db.execSQL("DELETE FROM Usuario");

            Log.d("Database", "Todos los usuarios han sido eliminados.");
        } catch (Exception e) {
            Log.e("Database", "Error al eliminar usuarios", e);
        } finally {
            db.close();
        }
    }


}