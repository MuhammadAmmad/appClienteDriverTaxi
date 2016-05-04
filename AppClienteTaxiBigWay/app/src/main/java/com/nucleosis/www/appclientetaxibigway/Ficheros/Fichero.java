package com.nucleosis.www.appclientetaxibigway.Ficheros;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by carlos.lopez on 27/04/2016.
 */
public class Fichero {

    private Context context;

    public Fichero(Context context) {
        this.context = context;
    }

    public void InsertIdZonaIdDistrito_Origen(String[] datos){
        JSONObject jsonOrigen=new JSONObject();
        try {
            jsonOrigen.put("idDistrito",datos[0].toString());
            jsonOrigen.put("idZona",datos[1].toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try  { OutputStreamWriter OrigenId =new OutputStreamWriter(
                context.openFileOutput("idOrigen.txt", Context.MODE_PRIVATE));
            OrigenId.write(jsonOrigen.toString());
            OrigenId.close();
            Log.d("fichero_", "fichero_creado"+"-->"+jsonOrigen.toString());
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage()); }

    }

    public JSONObject ExtraerZonaIdDistrito_Origen(){
            JSONObject jsonObject=null;
        try
        {
            BufferedReader emailT =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("idOrigen.txt")));
            jsonObject=new JSONObject(emailT.readLine());
            emailT.close();
        }
        catch (Exception ex)
        {
        }
        return jsonObject;
    }

    public void InsertIdZonaIdDistrito_Destino(String[] datos){
        JSONObject jsonOrigen=new JSONObject();
        try {
            jsonOrigen.put("idDistrito",datos[0].toString());
            jsonOrigen.put("idZona",datos[1].toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try  { OutputStreamWriter OrigenId =new OutputStreamWriter(
                context.openFileOutput("idDestino.txt", Context.MODE_PRIVATE));
            OrigenId.write(jsonOrigen.toString());
            OrigenId.close();
            Log.d("fichero_", "fichero_creado" + "-->" + jsonOrigen.toString());
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage()); }

    }

    public JSONObject ExtraerZonaIdDistrito_Destino(){
        JSONObject jsonObject=null;
        try
        {
            BufferedReader emailT =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("idDestino.txt")));
            jsonObject=new JSONObject(emailT.readLine());
            emailT.close();
        }
        catch (Exception ex)
        {
        }
        return jsonObject;
    }

    public  void InsertarDireccionIncioFin(String addresInicio,String addresFin){
        JSONObject jsonOrigen=new JSONObject();
        try {
            jsonOrigen.put("addresIncio",addresInicio);
            jsonOrigen.put("addresfin",addresFin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try  { OutputStreamWriter OrigenId =new OutputStreamWriter(
                context.openFileOutput("addresCliente.txt", Context.MODE_PRIVATE));
            OrigenId.write(jsonOrigen.toString());
            OrigenId.close();
            Log.d("fichero_", "fichero_creado" + "-->" + jsonOrigen.toString());
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage()); }
    }

    public JSONObject ExtraerDireccionIncioFin(){
        JSONObject jsonObject=null;
        try
        {
            BufferedReader emailT =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("addresCliente.txt")));
            jsonObject=new JSONObject(emailT.readLine());
            emailT.close();
        }
        catch (Exception ex)
        {
        }
        return jsonObject;
    }

}
