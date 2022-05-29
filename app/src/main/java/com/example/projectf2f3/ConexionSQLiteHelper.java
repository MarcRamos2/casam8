package com.example.projectf2f3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.projectf2f3.Utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    // connexió amb base de dades
    public ConexionSQLiteHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // crear taules
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidades.CREAR_TABLA_MENU);
        db.execSQL(Utilidades.CREAR_TABLA_PUNTUACIO);

    }

    // eliminar taules si existeixen
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_MENU);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PUNTUACIO);

        onCreate(db);
    }



}
