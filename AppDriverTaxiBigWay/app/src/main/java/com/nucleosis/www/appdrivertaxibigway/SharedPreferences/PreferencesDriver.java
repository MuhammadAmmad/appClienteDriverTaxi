package com.nucleosis.www.appdrivertaxibigway.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 03/04/2016.
 */
public class PreferencesDriver {
    private Context context;

    public PreferencesDriver(Context context) {
        this.context = context;
    }

    public void InsertDataDriver(List<beansDataDriver> listaData){
//Guardamos las preferencias
        SharedPreferences prefs =
                context.getSharedPreferences("dataDriver", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", listaData.get(0).getIdDriver());
        editor.putString("name", listaData.get(0).getNameDriver());
        editor.putString("apellidos", listaData.get(0).getApellidoDriver());
        editor.putString("celphone", listaData.get(0).getNumCelular());
        editor.putString("phone", listaData.get(0).getNumTelefono());
        editor.putString("email", listaData.get(0).getEmailDriver());
        editor.putString("dni", listaData.get(0).getNumDNI());
        editor.putString("urlPhotoDriver",listaData.get(0).getURLPhotoDriver());
        editor.putString("expirationLicencia", listaData.get(0).getFechaLicenciaExpiracion());
        editor.commit();
    }
    public String[]  OpenDataDriver(){
        String[]data=new String[8];

        SharedPreferences prefs =
        context.getSharedPreferences("dataDriver", Context.MODE_PRIVATE);
        data[0]=prefs.getString("name","driver");
        data[1]=prefs.getString("apellidos","fit");
        data[2]=prefs.getString("celphone","0000000");
        data[3]=prefs.getString("phone","222222");
        data[4]=prefs.getString("email","android@fit-pe.com");
        data[5]=prefs.getString("dni","0000000");
        data[6]=prefs.getString("urlPhotoDriver","http://taxibigway.com/img/conductor/");
        data[7]=prefs.getString("expirationLicencia","00-00-00");
        return data;
    }

    public String OpenIdDriver(){
        SharedPreferences prefs =
                context.getSharedPreferences("dataDriver", Context.MODE_PRIVATE);

        return prefs.getString("id","0");

    }

    public void InsertarIdVehiculo(String idvehiculo){

        SharedPreferences prefs =
                context.getSharedPreferences("idVehiculo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("idAuto",idvehiculo);
        editor.commit();
    }

    public String ExtraerIdVehiculo(){
        SharedPreferences prefs =
                context.getSharedPreferences("idVehiculo", Context.MODE_PRIVATE);
        return prefs.getString("idAuto","0");
    }


    public void InsertarIdTurno(String idTurno){
        SharedPreferences prefs =
                context.getSharedPreferences("Turno", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("idTurno",idTurno);
        editor.commit();
    }

    public String ExtraerIdTurno(){
        SharedPreferences prefs =
                context.getSharedPreferences("Turno", Context.MODE_PRIVATE);
        return prefs.getString("idTurno","0");
    }

    public void InsertarDataTurno(String idTurno,String fecha,String hora,String stado){
        SharedPreferences prefs =
                context.getSharedPreferences("dataTurno", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("idTurno",idTurno);
        editor.putString("Fecha_Pref",fecha);
        editor.putString("Hora_Pref",hora);
        editor.putString("stado_Turno_Pref",stado);
        editor.commit();
    }
    public JSONObject ExtraerDataTurno(){
        JSONObject dataTurnoJson=new JSONObject();
        SharedPreferences prefs =
                context.getSharedPreferences("dataTurno", Context.MODE_PRIVATE);

        try {
            dataTurnoJson.put("idTurnoJson",prefs.getString("idTurno","0"));
            dataTurnoJson.put("FechaTurnoJson",prefs.getString("Fecha_Pref","0000-00-00"));
            dataTurnoJson.put("HoraTurnoJson",prefs.getString("Hora_Pref","00:00"));
            dataTurnoJson.put("stadoTurnoJson",prefs.getString("stado_Turno_Pref","0"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataTurnoJson;
    }
    public  void InsertarFechaHoraActual(String fecha,String hora){
        SharedPreferences prefs =
                context.getSharedPreferences("FechaHoraActual", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fecha_sis",fecha);
        editor.putString("hora_sis",hora);
        editor.commit();
    }

    public JSONObject ExtraerHoraSistema(){
        SharedPreferences prefs =
                context.getSharedPreferences("FechaHoraActual", Context.MODE_PRIVATE);

        JSONObject jsonFechaHora= new JSONObject();
        try {
            jsonFechaHora.put("fechaServidor",prefs.getString("fecha_sis","0000-00-00"));
            jsonFechaHora.put("horaServidor",prefs.getString("hora_sis","00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonFechaHora;
    }

    public void InsertaListServiciosCreados(String json){
        SharedPreferences prefs =
                context.getSharedPreferences("jsonListaServiciosCreados", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("jsonList",json);
        editor.commit();
    }
    public JSONArray ExtarerListServiciosCreados(){

        return null;
    }
}
