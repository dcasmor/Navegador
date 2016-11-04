package com.example.dcasm.navegador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BrowserDB extends SQLiteOpenHelper {

    private static Web web;
    private static final int VERSION_BD = 1;
    private static final String NOMBRE_BD = "Dat.db";
    private static final String NOMBRE_TABLA = "historial";

    private static final String ins = "CREATE TABLE historial (id INT PRIMARY KEY, nombre" +
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

    public long insertar(Web web) {
        long reg = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("id", web.getId());
            valores.put("nombre", web.getNombre());
            valores.put("direccion", web.getDireccion());
            reg = db.insert("historial", null, valores);
        }
        db.close();
        return reg;
    }

    public boolean borrar(Web web) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DROP TABLE historial");
            try {
                db.execSQL("SELECT * FROM historial");
            } catch (SQLException sqlx) {return true;}
        }
        return false;
    }
}
