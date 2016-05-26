package com.nucleosis.www.appclientetaxibigway.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.Cripto;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import java.util.List;

/**
 * Created by karlos on 03/04/2016.
 */
public class PreferencesCliente {
    private Context context;

    public PreferencesCliente(Context context) {
        this.context = context;
    }

    public void InsertDataCliente(List<dataClienteSigUp> listaData){
//Guardamos las preferencias
        Log.d("dataSharePrefereces",listaData.get(0).getCelular()+"--->");
        SharedPreferences prefs =
                context.getSharedPreferences("dataCliente", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ID_CLIENTE", Cripto.Encriptar(listaData.get(0).getIdCliente()));
        editor.putString("NOM_CLIENTE", Cripto.Encriptar(listaData.get(0).getNombre()));
        editor.putString("APE_PATERNO", Cripto.Encriptar(listaData.get(0).getaPaterno()));
        editor.putString("APE_MATERNO", Cripto.Encriptar(listaData.get(0).getaMaterno()));
        editor.putString("NUM_DOCUMENTO_IDENTIDAD", Cripto.Encriptar(listaData.get(0).getDni()));
        editor.putString("DES_EMAIL", Cripto.Encriptar(listaData.get(0).getEmail()));
        editor.putString("NUM_CELULAR", Cripto.Encriptar(listaData.get(0).getCelular()));
        editor.commit();
    }
    public String[]  OpenDataCliente(){
        String[]data=new String[7];

        SharedPreferences prefs =
        context.getSharedPreferences("dataCliente", Context.MODE_PRIVATE);

        try {
            data[0]=Cripto.Desencriptar(prefs.getString("ID_CLIENTE","-1"));
            data[1]=Cripto.Desencriptar(prefs.getString("NOM_CLIENTE","USER"));
            data[2]=Cripto.Desencriptar(prefs.getString("APE_PATERNO","0000000"));
            data[3]=Cripto.Desencriptar(prefs.getString("APE_MATERNO","222222"));
            data[4]=Cripto.Desencriptar(prefs.getString("NUM_DOCUMENTO_IDENTIDAD","00000000"));
            data[5]=Cripto.Desencriptar(prefs.getString("DES_EMAIL","0000000"));
            data[6]=Cripto.Desencriptar(prefs.getString("NUM_CELULAR", "0000000"));
        } catch (Exception e) {
            e.printStackTrace();
           // Log.d("errot->",e.getMessage());
        }
       // Log.d("dataCliente","--->xxx"+prefs.getString("NOM_CLIENTE","-1"));
        return data;
    }

    public String OpenIdCliente(){
        String idClinte="-1";
        SharedPreferences prefs =
                context.getSharedPreferences("dataCliente", Context.MODE_PRIVATE);

        try {
            idClinte=Cripto.Desencriptar(prefs.getString("ID_CLIENTE","-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idClinte;

    }
}
