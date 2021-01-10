package com.rafam.examen1rrmfg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EmpleadosSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE empleados (dni TEXT PRIMARY KEY NOT NULL, nombre TEXT NOT NULL, apellido TEXT NOT NULL, sueldo REAL NOT NULL, moneda TEXT NOT NULL, departamento INTEGER NOT NULL)";

    public EmpleadosSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS empleados");

        db.execSQL(sqlCreate);
    }

}
