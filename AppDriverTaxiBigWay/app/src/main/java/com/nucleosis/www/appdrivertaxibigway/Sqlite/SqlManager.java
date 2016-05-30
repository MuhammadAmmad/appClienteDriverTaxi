package com.nucleosis.www.appdrivertaxibigway.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by carlos.lopez on 30/05/2016.
 */
public class SqlManager extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Cursor c;
    private static int VERSION_DB=1;
    private static String NAME_DB="dbDriverTaxi.db";

    private static final String TABLE_NOTIFICACION_SERVICIO=
            "CREATE TABLE IF NOT EXISTS NOTIFICACION_SERVICIO ("
                    + "idTablaNotificacion INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "idServicio VARCHAR(100) NOT NULL,"
                    + "StadoNotificacion CHAR(1) NOT NULL )";
    public SqlManager(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
        Log.d("creo","dbsqli");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTIFICACION_SERVICIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
