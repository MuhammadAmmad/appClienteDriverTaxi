package com.nucleosis.www.appdrivertaxibigway.Ficheros;

import android.content.Context;
import android.util.Log;


import com.nucleosis.www.appdrivertaxibigway.Constans.Cripto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by carlos.lopez on 21/12/2015.
 */
public class Fichero {
    private Cripto cifrarString;
    private Context context;
    public Fichero(Context context) {
        this.context = context;
        cifrarString=new Cripto();
    }
    public void InsertDatosUser(String[] datos){
        try  { OutputStreamWriter idUser =new OutputStreamWriter(
                context.openFileOutput("idUser.txt", Context.MODE_PRIVATE));
            OutputStreamWriter nameUser =new OutputStreamWriter(
                context.openFileOutput("nameUser.txt", Context.MODE_PRIVATE));
            idUser.write(cifrarString.Encriptar(datos[0]));
            nameUser.write(cifrarString.Encriptar(datos[1]));
            idUser.close();
            nameUser.close();
            Log.d("fichero_", "fichero_creado");
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }

    }
    public String[] ExtraerDatosUser(){
        String[] nameFicheroIdUser=new String[2];
        nameFicheroIdUser[0]="";
        nameFicheroIdUser[1]="";
        try
        {
            BufferedReader getIdUser =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("idUser.txt")));
            nameFicheroIdUser[0] = cifrarString.Desencriptar(getIdUser.readLine());
            getIdUser.close();

            BufferedReader getNameUser =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("nameUser.txt")));
            nameFicheroIdUser[1]= cifrarString.Desencriptar(getNameUser.readLine());
            getNameUser.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }

        return nameFicheroIdUser;
    }



}
