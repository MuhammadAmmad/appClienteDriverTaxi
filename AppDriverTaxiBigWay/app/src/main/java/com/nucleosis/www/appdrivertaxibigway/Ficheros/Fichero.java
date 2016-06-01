package com.nucleosis.www.appdrivertaxibigway.Ficheros;

import android.content.Context;
import android.util.Log;


import com.nucleosis.www.appdrivertaxibigway.Constans.Cripto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public void InsertarFechaHoraUltimaDeCoordenadas(String FechaHoraCoordenadas){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("fechaHoraCoordenadas.txt", Context.MODE_PRIVATE));
            json.write(FechaHoraCoordenadas);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }
    public JSONObject ExtraerFechaHoraUltimaDeCoordenadas(){
        JSONObject jsonObject=null;
        try
        {
            BufferedReader getDataServicios =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("fechaHoraCoordenadas.txt")));
            String    dataServicios = getDataServicios.readLine();
            jsonObject=new JSONObject(dataServicios);
            getDataServicios.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }
        return jsonObject;
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
    public void InsertIdZonaIdDistrito_Origen(String datosJsona){

        try  {
            OutputStreamWriter OrigenId =new OutputStreamWriter(
                context.openFileOutput("idOrigen.txt", Context.MODE_PRIVATE));
            OrigenId.write(datosJsona);
            OrigenId.close();
            Log.d("fichero_", "fichero_creado"+"-->"+datosJsona);
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
    public void InsertIdZonaIdDistrito_Destino(String datosDestino){


        try  {
            OutputStreamWriter DestinoId =new OutputStreamWriter(
                context.openFileOutput("idDestino.txt", Context.MODE_PRIVATE));
            DestinoId.write(datosDestino);
            DestinoId.close();
            Log.d("fichero_", "fichero_creado" + "-->" + datosDestino);
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
    public void InsertaDireccionIncio(String addresIncio){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("addresInicio.txt", Context.MODE_PRIVATE));
            json.write(addresIncio);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());
        }
    }
    public JSONObject ExtraerDireccionIncio(){
        JSONObject jsonAddres=null;
        try
        {
            BufferedReader addres =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("addresInicio.txt")));
            jsonAddres=new JSONObject(addres.readLine());
            addres.close();
        }
        catch (Exception ex)
        {
        }
        return jsonAddres;
    }
    public void InsertaDireccionFin(String addresFin){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("addresFin.txt", Context.MODE_PRIVATE));
            json.write(addresFin);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());
        }
    }
    public JSONObject ExtraerDireccionFin(){
        JSONObject jsonAddres=null;
        try
        {
            BufferedReader addres =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("addresFin.txt")));
            jsonAddres=new JSONObject(addres.readLine());
            addres.close();
        }
        catch (Exception ex)
        {
        }
        return jsonAddres;
    }
    public void InsertarServicioUnico(String servicio){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("servicioUncio.txt", Context.MODE_PRIVATE));
            json.write(servicio);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }
    public JSONObject ExtraerServicioUnico(){
        JSONObject json=null;
        try
        {
            BufferedReader service =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("servicioUncio.txt")));
            json=new JSONObject(service.readLine());
            service.close();
        }
        catch (Exception ex)
        {
        }
        return json;
    }
    public void INSERTAR_IdZONA_IDDISTRITO_INCIO(String idDistritoIdZona_inicio){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("idDistritoIdZonaJsonIncio.txt", Context.MODE_PRIVATE));
            json.write(idDistritoIdZona_inicio);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }
    public JSONObject EXTRAER_IdZONA_IDDISTRITO_INCIO(){
        JSONObject json=null;
        try
        {
            BufferedReader service =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("idDistritoIdZonaJsonIncio.txt")));
            json=new JSONObject(service.readLine());
            service.close();
        }
        catch (Exception ex)
        {
        }
        return json;
    }
    public void INSERTAR_IdZONA_IDDISTRITO_FIN(String idDistritoIdZona_Fin){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("idDistritoIdZonaJsonFin.txt", Context.MODE_PRIVATE));
            json.write(idDistritoIdZona_Fin);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }
    public JSONObject EXTRAER_IdZONA_IDDISTRITO_FIN(){
        JSONObject json=null;
        try
        {
            BufferedReader service =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("idDistritoIdZonaJsonFin.txt")));
            json=new JSONObject(service.readLine());
            service.close();
        }
        catch (Exception ex)
        {
        }
        return json;
    }
    public void InsertarCoordendaDirrecionIncioCliente(String latlong){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("latlonAddresInicioCliente.txt", Context.MODE_PRIVATE));
            json.write(latlong);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());
        }
    }
    public JSONObject ExtraerCoordendaDirrecionIncioCliente(){
        JSONObject jsonAddresLatLon=null;
        try
        {
            BufferedReader addres =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("latlonAddresInicioCliente.txt")));
            jsonAddresLatLon=new JSONObject(addres.readLine());
            addres.close();
        }
        catch (Exception ex)
        {
        }
        return jsonAddresLatLon;
    }

    public  void InsertarCoordendaConductor(String coordenadas){
        try  { OutputStreamWriter json =new OutputStreamWriter(
                context.openFileOutput("latlonDriver.txt", Context.MODE_PRIVATE));
            json.write(coordenadas);
            json.close();
        }
        catch (Exception ex)
        {	Log.d("error", ex.getMessage());           }
    }


    public JSONObject ExtraerCoordendaConductor(){
        JSONObject jsonObject=null;
        try
        {
            BufferedReader getDataServicios =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("latlonDriver.txt")));
            String    coordenadas = getDataServicios.readLine();
            jsonObject=new JSONObject(coordenadas);
            getDataServicios.close();
        }
        catch (Exception ex)
        {
            Log.d("look_","No se puede leer fichero");   }
        return jsonObject;
    }


}
