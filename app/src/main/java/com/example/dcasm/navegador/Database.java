package com.example.dcasm.navegador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final int VERSION_BD = 1;
    private static final String NOMBRE_BD = "Dat.db";

    private static final String ins = "CREATE TABLE HISTORIAL (" +
            "URL TEXT NOT NULL PRIMARY KEY);";

    public Database(Context context) {
        super(context, NOMBRE_BD , null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HISTORIAL");
        onCreate(db);
    }

    //Insercón de URL
    public void nuevaUrl(String url) {
        SQLiteDatabase db = getWritableDatabase();
        long nreg = -1;

        if (db != null) {
            ContentValues v = new ContentValues();
            v.put("url", url);
            nreg = db.insert("historial", null, v);
        }
        db.close();
        Log.d("REG", "" + nreg);
    }

    //Obtención de URLs para el AutoCompleteTextView
    public Cursor getUrls() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            try {
                Cursor c = db.rawQuery("SELECT URL FROM HISTORIAL", null);
                return c;
            } catch (Exception e) {}
        }
        db.close();
        return null;
    }

    //Borrado del historial
    public void borrar() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("DELETE FROM HISTORIAL;");
        }
        db.close();
    }

}
