package com.example.dcasm.navegador;

import android.content.*;
import android.database.sqlite.*;
import android.database.*;

public class BrowserDB extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NOMBRE = "Dat.db";
    private static final String TABLA = "Historial";
    private static final String str = "CREATE TABLE Historial (id INT PRIMARY KEY, nombre" +
            " VARCHAR(100), http VARCHAR(200), visitas INT)";

    public BrowserDB(Context context) {
        super(context, NOMBRE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        sqldb.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int oldVer, int newVer) {
        sqldb.execSQL("DROP TABLE IF EXISTS" + TABLA);
        onCreate(sqldb);
    }

    public long updateSQL(int id, String nom, String dir, int nVis) {
        long res = -1;
        SQLiteDatabase sqldb = getWritableDatabase();
        if (sqldb != null) {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("nombre", nom);
            values.put("http", dir);
            values.put("visitas", nVis);
            res = sqldb.insert("Historial", null, values);
        }
        sqldb.close();
        return res;
    }

    public int getId() {
        int ult = -1;
        SQLiteDatabase sqldb = getReadableDatabase();
        if (sqldb != null) {
            String[] values = {"id"};
            Cursor pointer = sqldb.query("Historial", values, null, null, null, null, null, null);
            if (pointer.getCount() != 0) {
                pointer.moveToLast();
                ult = pointer.getInt(0);
            }
            pointer.close();
        }
        sqldb.close();
        return ult;
    }

    public String[] getUrl() {
        SQLiteDatabase sqldb = getReadableDatabase();
        String[] url = new String[0];
        if (sqldb != null) {
            String[] campos = {"direc"};
            Cursor cur = sqldb.query("Historial", campos, null, null, null, null, null, null);
            url= new String[cur.getCount()];
            cur.moveToFirst();
            if(cur.getCount() != 0) {
                for(int i=0;i<cur.getCount();i++){
                    url[i] = cur.getString(0);
                    cur.moveToNext();
                }

            }
            cur.close();
        }
        sqldb.close();
        return url;
    }

    public String[] getHistorial() {
        String[] a = new String[1];
        return a;
    }
}
