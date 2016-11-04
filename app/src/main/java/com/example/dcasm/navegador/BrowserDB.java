package com.example.dcasm.navegador;

import android.content.*;
import android.database.sqlite.*;
import android.database.*;

public class BrowserDB extends SQLiteOpenHelper {
    public BrowserDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
