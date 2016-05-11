package com.nucleosis.www.appdrivertaxibigway.Ficheros;

import android.content.Context;
import android.util.Log;


import com.nucleosis.www.appdrivertaxibigway.Constans.Cripto;

import org.json.JSONArray;

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

    public void InsertardataVehiculos(String jsonArray){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("dataVehiculos.txt", Context.MODE_PRIVATE));
            json.write(jsonArray);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }

    public String  ExtraerDataVehiculos(){
        String dataVehiculos="";
        try
        {
            BufferedReader getDataVehiculos =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("dataVehiculos.txt")));
            dataVehiculos = getDataVehiculos.readLine();
            getDataVehiculos.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }
        return dataVehiculos;
    }

    public void InsertarListaServiciosTomadosConductor(String data){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("jsonListaServiciosTomadosConductor.txt", Context.MODE_PRIVATE));
            json.write(data);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }

    public JSONArray ExtraerListaServiciosTomadoConductor(){

       JSONArray jsonArray=null;
        try
        {
            BufferedReader getDataServicios =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("jsonListaServiciosTomadosConductor.txt")));
        String    dataServicios = getDataServicios.readLine();
            jsonArray=new JSONArray(dataServicios);
            getDataServicios.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }
        return jsonArray;
    }
}
