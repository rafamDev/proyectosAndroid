package com.lieceolapaz.dam.RRMFG;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Clase para crear la tabla.
public class JugadoresSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Jugadores
    String sqlCreate = "CREATE TABLE jugadores (codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre TEXT NOT NULL, precio REAL NOT NULL, posicion TEXT NOT NULL, puntos INTEGER NOT NULL)";

    public JugadoresSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS jugadores");

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }

}
