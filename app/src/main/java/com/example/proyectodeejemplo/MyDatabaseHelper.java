package com.example.proyectodeejemplo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "appdiario.db";

    // Database version
    private static final int DATABASE_VERSION = 10;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Notas table
        String createNotasTable = "CREATE TABLE Notas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT," +
                "titulo TEXT," +
                "texto TEXT," +
                "design INTEGER" +
                ");";
        db.execSQL(createNotasTable);

        // Creating Checklist table
        String createChecklistTable = "CREATE TABLE Checklist (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT," +
                "texto TEXT," +
                "estado INTEGER" +
                ");";
        db.execSQL(createChecklistTable);

        // Creating Recordatorios table
        String createRecordatoriosTable = "CREATE TABLE Recordatorios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tiponota INTEGER," +
                "sticker INTEGER," +
                "fecha TEXT," +
                "texto TEXT" +
                ");";
        db.execSQL(createRecordatoriosTable);

        // Creating Mood table
        String createMoodTable = "CREATE TABLE Mood (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT," +
                "estado TEXt" +
                ");";
        db.execSQL(createMoodTable);

        // Creating Designs table
        String createDesignsTable = "CREATE TABLE Designs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT" +
                ");";
        db.execSQL(createDesignsTable);

        // Creating TipoNota table
        String createTipoNotaTable = "CREATE TABLE TipoNota (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT" +
                ");";
        db.execSQL(createTipoNotaTable);

        //Tabla tipo Usuario
        String createUsuarioTable = "CREATE TABLE Usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "signo TEXT Default ''," +
                "cumple TEXT Default '13/11/2000'," +
                "colorfav TEXT Default ''" +
                ");";
        db.execSQL(createUsuarioTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS Notas");
        db.execSQL("DROP TABLE IF EXISTS Checklist");
        db.execSQL("DROP TABLE IF EXISTS Recordatorios");
        db.execSQL("DROP TABLE IF EXISTS Mood");
        db.execSQL("DROP TABLE IF EXISTS Designs");
        db.execSQL("DROP TABLE IF EXISTS TipoNota");
        db.execSQL("DROP TABLE IF EXISTS Usuario");


        onCreate(db);
    }
}
