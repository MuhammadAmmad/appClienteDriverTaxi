package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karlos on 15/05/2016.
 */
public class AsytaskServicioExtraido extends AsyncTask<String,String,JSONObject> {
    private Context context;
    private String idServicio;
    private Fichero fichero;
    private JSONArray ServiciosJson;
    private JSONObject ServicioUnico;
    public AsytaskServicioExtraido(Context context,String idServicio) {
        this.context = context;
        this.idServicio=idServicio;
        fichero=new Fichero(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ServiciosJson=fichero.ExtraerListaServiciosTomadoConductor();
        Log.d("servicioXXX",fichero.ExtraerListaServiciosTomadoConductor().toString());
        ServicioUnico=new JSONObject();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        for(int i=0; i<ServiciosJson.length();i++){
            try {
                String idServiceExtrac=ServiciosJson.getJSONObject(i).getString("idServicio");
                if(idServicio.equals(idServiceExtrac)) {

                    ServicioUnico.put("desAutoTipo",ServiciosJson.getJSONObject(i).getString("desAutoTipo") );
                    ServicioUnico.put("DescripcionServicion",ServiciosJson.getJSONObject(i).getString("DescripcionServicion") );
                    ServicioUnico.put("direccionFinal", ServiciosJson.getJSONObject(i).getString("direccionFinal"));
                    ServicioUnico.put("DireccionIncio", ServiciosJson.getJSONObject(i).getString("DireccionIncio"));

                    ServicioUnico.put("Fecha", ServiciosJson.getJSONObject(i).getString("Fecha"));
                    ServicioUnico.put("FechaFormat",ServiciosJson.getJSONObject(i).getString("FechaFormat") );
                    ServicioUnico.put("Hora", ServiciosJson.getJSONObject(i).getString("Hora"));
                    ServicioUnico.put("idCliente", ServiciosJson.getJSONObject(i).getString("idCliente"));
                    ServicioUnico.put("idConductor", ServiciosJson.getJSONObject(i).getString("idConductor"));

                    ServicioUnico.put("idServicio", ServiciosJson.getJSONObject(i).getString("idServicio"));
                    ServicioUnico.put("idTipoAuto", ServiciosJson.getJSONObject(i).getString("idTipoAuto"));

                    ServicioUnico.put("indAireAcondicionado", ServiciosJson.getJSONObject(i).getString("indAireAcondicionado"));
                    ServicioUnico.put("importeAireAcondicionado",ServiciosJson.getJSONObject(i).getString("importeAireAcondicionado"));
                    ServicioUnico.put("importePeaje",ServiciosJson.getJSONObject(i).getString("importePeaje"));
                    ServicioUnico.put("importeServicio",ServiciosJson.getJSONObject(i).getString("importeServicio"));

                    ServicioUnico.put("importeTiempoEspera",ServiciosJson.getJSONObject(i).getString("importeTiempoEspera"));
                    ServicioUnico.put("infoAddress",ServiciosJson.getJSONObject(i).getString("infoAddress"));
                    ServicioUnico.put("nameDistritoInicio",ServiciosJson.getJSONObject(i).getString("nameDistritoInicio"));
                    ServicioUnico.put("nameDistritoFin",ServiciosJson.getJSONObject(i).getString("nameDistritoFin"));
                    ServicioUnico.put("nombreConductor",ServiciosJson.getJSONObject(i).getString("nombreConductor"));

                    ServicioUnico.put("nombreStadoServicio",ServiciosJson.getJSONObject(i).getString("nombreStadoServicio"));
                    ServicioUnico.put("nucCelularCliente",ServiciosJson.getJSONObject(i).getString("nucCelularCliente"));
                    ServicioUnico.put("numeroMinutoTiempoEspera",ServiciosJson.getJSONObject(i).getString("numeroMinutoTiempoEspera"));
                    ServicioUnico.put("numeroMovilTaxi",ServiciosJson.getJSONObject(i).getString("numeroMovilTaxi"));
                    ServicioUnico.put("statadoServicio",ServiciosJson.getJSONObject(i).getString("statadoServicio"));

                    ServicioUnico.put("nameZonaIncio",ServiciosJson.getJSONObject(i).getString("nameZonaIncio"));
                    ServicioUnico.put("nameZonaFin",ServiciosJson.getJSONObject(i).getString("nameZonaFin"));


                    i=ServiciosJson.length();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ServicioUnico;
    }

    @Override
    protected void onPostExecute(JSONObject jsonServicio) {
        super.onPostExecute(jsonServicio);
        if(jsonServicio!=null){
            fichero.InsertarServicioUnico(jsonServicio.toString());
        }
    }
}
