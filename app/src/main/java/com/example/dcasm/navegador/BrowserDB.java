package com.example.dcasm.navegador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BrowserDB extends SQLiteOpenHelper {

    private static Web web;
    private static final int VERSION_BD = 1;
    private static final String NOMBRE_BD = "Dat.db";
    private static final String NOMBRE_TABLA = "historial";

    private static final String ins = "CREATE TABLE historial (id INT PRIMARY KEY, alias" +
            " VARCHAR(100), http VARCHAR(200))";

    public BrowserDB(Context context) {
        super(context, NOMBRE_BD , null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + NOMBRE_TABLA);
        onCreate(db);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        long fila = -1;

        return fila;
    }
}
