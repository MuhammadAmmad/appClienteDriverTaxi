package com.nucleosis.www.appdrivertaxibigway.ws;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.lopez on 11/05/2016.
 */
public class wsListarServiciosTomadoConductorDiaActual extends AsyncTask<String,String,String> {
    private Context context;
    private Fichero fichero;
    JSONArray   jsonListaServicios=null;
    beansHistorialServiciosCreados row=null;
    private List<beansHistorialServiciosCreados> listServiciosFechaActualConducor;
    public wsListarServiciosTomadoConductorDiaActual(Context context) {
        this.context = context;
        fichero=new Fichero(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listServiciosFechaActualConducor=new ArrayList<beansHistorialServiciosCreados>();

        jsonListaServicios=fichero.ExtraerListaServiciosTomadoConductor();

    }

    @Override
    protected String doInBackground(String... params) {
        for(int i=0; i<jsonListaServicios.length();i++){
            row=new beansHistorialServiciosCreados();
            try {

                String dataJson=jsonListaServicios.getJSONObject(i).getString("sdfd");


                row.setIdServicio(jsonListaServicios.getJSONObject(i).getString("idServicio"));
                row.setFecha(jsonListaServicios.getJSONObject(i).getString("Fecha").toString());
                row.setHora(jsonListaServicios.getJSONObject(i).getString("Hora").toString());
                row.setImporteServicio(jsonListaServicios.getJSONObject(i).getString("importeServicio").toString());
                row.setDescripcionServicion(jsonListaServicios.getJSONObject(i).getString("DescripcionServicion").toString());
                row.setImporteAireAcondicionado(jsonListaServicios.getJSONObject(i).getString("importeAireAcondicionado").toString());
                row.setImportePeaje(jsonListaServicios.getJSONObject(i).getString("importePeaje").toString());
                row.setNumeroMinutoTiempoEspera(jsonListaServicios.getJSONObject(i).getString("numeroMinutoTiempoEspera").toString());
                row.setImporteTiempoEspera(jsonListaServicios.getJSONObject(i).getString("importeTiempoEspera").toString());
                row.setNameDistritoInicio(jsonListaServicios.getJSONObject(i).getString("nameDistritoInicio\n").toString());
                row.setNameDistritoFin(jsonListaServicios.getJSONObject(i).getString("nameDistritoFin\n").toString());
                row.setDireccionIncio(jsonListaServicios.getJSONObject(i).getString("DireccionIncio").toString());
                row.setDireccionFinal(jsonListaServicios.getJSONObject(i).getString("direccionFinal").toString());
                row.setNombreConductor(jsonListaServicios.getJSONObject(i).getString("nombreConductor").toString());
                row.setStatadoServicio(jsonListaServicios.getJSONObject(i).getString("statadoServicio").toString());
                row.setNombreStadoServicio(jsonListaServicios.getJSONObject(i).getString("nombreStadoServicio").toString());
               /* row.setDireccionIncio(jsonListaServicios.getJSONObject(i).getString("").toString());
                row.setDireccionFinal(jsonListaServicios.getJSONObject(i).getString("").toString());*/
                row.setInfoAddress(jsonListaServicios.getJSONObject(i).getString("DireccionIncio").toString()
                        + "\n" + jsonListaServicios.getJSONObject(i).getString("direccionFinal").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
