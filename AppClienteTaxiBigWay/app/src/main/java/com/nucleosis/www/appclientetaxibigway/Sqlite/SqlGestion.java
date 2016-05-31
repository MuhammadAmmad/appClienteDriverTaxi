package com.nucleosis.www.appclientetaxibigway.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by carlos.lopez on 30/05/2016.
 */
public class SqlGestion {
    private SQLiteDatabase db;
    private Context context;
    private SqlManager sqlManager;
    public SqlGestion(Context context) {
        this.context = context;
        sqlManager=new SqlManager(context);
        db=sqlManager.getReadableDatabase();
    }

    public void InsertarIdServicioStado(String idServicio, String stado){
        Log.d("insertar_:",idServicio+"*-*-"+stado);
        SQLiteStatement stp =db.compileStatement("INSERT INTO NOTIFICACION_SERVICIO " +
                "(idServicio, StadoNotificacion ) VALUES (?,?)");
        stp.bindString(1, idServicio);
        stp.bindString(2, stado);
        stp.execute();

        db.close();
        stp.close();
    }

    public String[] BuscarIdServicio(String idServicio){

        Cursor c = db.rawQuery("SELECT idServicio, StadoNotificacion FROM NOTIFICACION_SERVICIO WHERE "
                + "idServicio='"+idServicio+"'",null);
        String[] data =new String[2];
        data[0]="";
        data[1]="";
        if(c.moveToFirst()){
            do{
                data[0] =c.getString(0);
                data[1] =c.getString(1);

            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return data;
    }


    /*    public  void ACTUALIZAR_PESO_IMC(SQLiteDatabase db,String dia,String peso, String imc,int idPImc){
        db.execSQL("UPDATE PESO_IMC SET "
                + "Peso='"+peso+"',"
                + "IMC='"+imc+"',"
                + "FECHA_REGISTRO='"+dia+"' WHERE idPesoImc ='"+idPImc+"'");
    }*/
    public void UpdateIdServicioStado(){




    }


}
