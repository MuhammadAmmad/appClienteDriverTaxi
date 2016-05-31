package com.nucleosis.www.appdrivertaxibigway.ws;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Adapters.GriddAdapterServiciosTomadosConductor;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 11/05/2016.
 */
public class wsListarServiciosTomadoConductorDiaActual extends
        AsyncTask<String,String,List<beansHistorialServiciosCreados>> {

    private Context context;
    private Fichero fichero;
    private GridViewWithHeaderAndFooter grid;
    private JSONArray   jsonListaServicios=null;
    private beansHistorialServiciosCreados row=null;
    public static List<beansHistorialServiciosCreados> listServiciosFechaActualConducor;
    private Drawable drawable;
    private ProgressDialog progressDialog;
    public wsListarServiciosTomadoConductorDiaActual(Context context,GridViewWithHeaderAndFooter grid) {
        this.context = context;
        this.grid=grid;
        fichero=new Fichero(context);
        Log.d("constructor","Sfsdfasdfasdf");
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listServiciosFechaActualConducor=new ArrayList<beansHistorialServiciosCreados>();
        listServiciosFechaActualConducor.clear();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("cargando.....");
      //  progressDialog.setCancelable(false);
        progressDialog.show();
    }
    @SuppressWarnings("deprecation")
    @Override
    protected List<beansHistorialServiciosCreados> doInBackground(String... params) {
        //tareaLarga();
        jsonListaServicios=fichero.ExtraerListaServiciosTomadoConductor();
        Log.d("serviclISTA_",jsonListaServicios.toString());
        for(int i=0; i<jsonListaServicios.length();i++){
            row=new beansHistorialServiciosCreados();
            Log.d("ListaSixe", jsonListaServicios.toString());
            try {
                row.setIdServicio(jsonListaServicios.getJSONObject(i).getString("idServicio"));
                row.setFecha(jsonListaServicios.getJSONObject(i).getString("Fecha").toString());
                row.setHora(jsonListaServicios.getJSONObject(i).getString("Hora").toString());
                row.setImporteServicio(jsonListaServicios.getJSONObject(i).getString("importeServicio").toString());
                row.setDescripcionServicion(jsonListaServicios.getJSONObject(i).getString("DescripcionServicion").toString());

                row.setImporteGastosAdicionales(jsonListaServicios.getJSONObject(i).getString("importeGastosAdicionales").toString());

                row.setIndAireAcondicionado(jsonListaServicios.getJSONObject(i).getString("indAireAcondicionado"));
                row.setImporteAireAcondicionado(jsonListaServicios.getJSONObject(i).getString("importeAireAcondicionado").toString());
                row.setImportePeaje(jsonListaServicios.getJSONObject(i).getString("importePeaje").toString());
                row.setNumeroMinutoTiempoEspera(jsonListaServicios.getJSONObject(i).getString("numeroMinutoTiempoEspera").toString());
                row.setImporteTiempoEspera(jsonListaServicios.getJSONObject(i).getString("importeTiempoEspera").toString());

                row.setNameDistritoInicio(jsonListaServicios.getJSONObject(i).getString("nameDistritoInicio").toString());
                row.setNameZonaIncio(jsonListaServicios.getJSONObject(i).getString("nameZonaIncio"));

                row.setNameDistritoFin(jsonListaServicios.getJSONObject(i).getString("nameDistritoFin").toString());
                row.setNameZonaFin(jsonListaServicios.getJSONObject(i).getString("nameZonaFin"));

                row.setDireccionIncio(jsonListaServicios.getJSONObject(i).getString("DireccionIncio").toString());
                row.setDireccionFinal(jsonListaServicios.getJSONObject(i).getString("direccionFinal").toString());
                row.setNombreConductor(jsonListaServicios.getJSONObject(i).getString("nombreConductor").toString());
                row.setStatadoServicio(jsonListaServicios.getJSONObject(i).getString("statadoServicio").toString());
                row.setNombreStadoServicio(jsonListaServicios.getJSONObject(i).getString("nombreStadoServicio").toString());
                row.setInfoAddress(jsonListaServicios.getJSONObject(i).getString("DireccionIncio").toString()
                        + "\n" + jsonListaServicios.getJSONObject(i).getString("direccionFinal").toString());

                row.setIdCliente(jsonListaServicios.getJSONObject(i).getString("idCliente").toString());
                row.setNumCelular(jsonListaServicios.getJSONObject(i).getString("nucCelularCliente").toString());
                row.setNunMovilTaxi(jsonListaServicios.getJSONObject(i).getString("numeroMovilTaxi").toString());

                row.setIndMostrarCelularCliente(jsonListaServicios.getJSONObject(i).getString("indMostrarCelularCliente").toString());
                row.setLatitudService(jsonListaServicios.getJSONObject(i).getString("latitudServicio").toString());
                row.setLongitudService(jsonListaServicios.getJSONObject(i).getString("longitudServicio").toString());

                int idStatus=Integer.parseInt(jsonListaServicios.getJSONObject(i).getString("statadoServicio").toString());
                Log.d("stadoServiciosxxxx-->",String.valueOf(idStatus));
                if(idStatus==2){
                    //STATDO ACEPTADO
                    drawable=context.getResources().getDrawable(R.drawable.shape_stado_aceptado);
                    row.setImageStatusServicio(drawable);
                    row.setStatusServicioTomadoColor(Color.rgb(255,144,247));
                }else if(idStatus==3){
                    //STADO EN RUTA CON CLIENTE
                    drawable=context.getResources().getDrawable(R.drawable.shape_green);
                    row.setImageStatusServicio(drawable);
                    row.setStatusServicioTomadoColor(Color.rgb(9,217,158));
                }else if(idStatus==4){
                    //TERMINADO CORRECTARMENTE EL SERVCIO
                    drawable=context.getResources().getDrawable(R.drawable.shape_blue);
                    row.setImageStatusServicio(drawable);
                    row.setStatusServicioTomadoColor(Color.rgb(242,223,49));

                }else if(idStatus==5){
                    drawable=context.getResources().getDrawable(R.drawable.shape_yellow);
                    row.setImageStatusServicio(drawable);
                    // NO TERMINADO
                }else  if( idStatus==6){
                    //CANCELADO POR EL CLIENTE
                    drawable=context.getResources().getDrawable(R.drawable.shape_red_cliente);
                    row.setImageStatusServicio(drawable);
                    row.setStatusServicioTomadoColor(Color.rgb(252,29,118));
                }
                else if(idStatus==7){
                    //CANCELADO POR EL CONDUCTOR
                    row.setStatusServicioTomadoColor(Color.rgb(142,1,3));
                }

                listServiciosFechaActualConducor.add(row);
            Log.d("xxx_", String.valueOf(listServiciosFechaActualConducor.size()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listServiciosFechaActualConducor;
    }

    @Override
    protected void onPostExecute(List<beansHistorialServiciosCreados> listaSeriviciosConductor) {
        super.onPostExecute(listaSeriviciosConductor);
        progressDialog.dismiss();
        if(listaSeriviciosConductor!=null){
        //  progressDialog.dismiss();
           grid.setAdapter(new GriddAdapterServiciosTomadosConductor(context,listaSeriviciosConductor));
        }else{
            Log.d("stamos_Aqui","sdjfldsakjj");
        }

    }

}
