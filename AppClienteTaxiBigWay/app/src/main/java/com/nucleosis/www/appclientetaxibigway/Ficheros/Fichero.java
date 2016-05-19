package com.nucleosis.www.appclientetaxibigway.Ficheros;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
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

    public void InsertarSesion (String sesion){
        if(sesion!=null){
            Log.d("sesion",sesion);
        }
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("sesion.txt", Context.MODE_PRIVATE));
            json.write(sesion);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }


    public JSONObject ExtraerSesion(){
        JSONObject sesion=null;
        try
        {

            BufferedReader getSesion =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("sesion.txt")));
            String data = getSesion.readLine();
            Log.d("ssionX",data.toString());
            sesion =new JSONObject(data);
            getSesion.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }

        return sesion;
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

    public void InsertarCoordendaDirrecionIncio(String latlong){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("latlonAddresInicio.txt", Context.MODE_PRIVATE));
            json.write(latlong);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());
        }
    }

    public JSONObject ExtraerCoordendaDirrecionIncio(){
        JSONObject jsonAddresLatLon=null;
        try
        {
            BufferedReader addres =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("latlonAddresInicio.txt")));
            jsonAddresLatLon=new JSONObject(addres.readLine());
            addres.close();
        }
        catch (Exception ex)
        {
        }
        return jsonAddresLatLon;
    }
    public void InsertarListaDistritos(String listaDitritos){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("listDistritos.txt", Context.MODE_PRIVATE));
            json.write(listaDitritos);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());
        }
    }
    public JSONArray ExtraerListDistritos(){
        JSONArray jsonDistritos=null;
        try
        {
            BufferedReader distritosList =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("listDistritos.txt")));
            jsonDistritos=new JSONArray(distritosList.readLine());
            distritosList.close();
        }
        catch (Exception ex)
        {
        }
        return jsonDistritos;
    }
    public void InsertarConfiguraciones(String configuracion){
        if(configuracion!=null){
            Log.d("configguracion_",configuracion);
        }
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("configuracion.txt", Context.MODE_PRIVATE));
            json.write(configuracion);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }

    public JSONObject ExtraerConfiguraciones(){
        JSONObject jsonObject=null;
        try
        {

            BufferedReader getConfiguracion =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("configuracion.txt")));
            String data = getConfiguracion.readLine();
            Log.d("daa",data.toString());
            jsonObject =new JSONObject(data);
            getConfiguracion.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }

        return jsonObject;
    }

    public void InsertarListaServiciosTomados(String data){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("jsonListaServiciosTomadosCliete.txt", Context.MODE_PRIVATE));
            json.write(data);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }
    public JSONArray ExtraerListaServiciosTomadoCliete(){

        JSONArray jsonArray=null;
        try
        {
            BufferedReader getDataServicios =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("jsonListaServiciosTomadosCliete.txt")));
            String    dataServicios = getDataServicios.readLine();
            jsonArray=new JSONArray(dataServicios);
            getDataServicios.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }
        return jsonArray;
    }

    public void InsertarFechaHoraActual(String fechaHora){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("fechaHoraActual.txt", Context.MODE_PRIVATE));
            json.write(fechaHora);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());
        }
    }

    public JSONObject ExtraerFechaHoraActual(){
        JSONObject ReturHoraFecha=null;
        try
        {
            BufferedReader horaFecha =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("fechaHoraActual.txt")));
            ReturHoraFecha=new JSONObject(horaFecha.readLine());
            horaFecha.close();
        }
        catch (Exception ex)
        {
        }
        return ReturHoraFecha;
    }
}
