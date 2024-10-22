package com.example.proyectodeejemplo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
        db.insert("Notas", null, values);
        db.close();
    }

    //Obtener todas las notas
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
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
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

    //encontrar una nota por fecha
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

    //encontrar nota por texto
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

}
