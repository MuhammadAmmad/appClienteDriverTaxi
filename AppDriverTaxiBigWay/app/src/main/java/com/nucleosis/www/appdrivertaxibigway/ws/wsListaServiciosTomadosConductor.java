package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.nucleosis.www.appdrivertaxibigway.Adapters.GridAdapterHistorialCarrera;
import com.nucleosis.www.appdrivertaxibigway.Adapters.GriddAdapterServiciosTomadosConductor;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.nucleosis.www.appdrivertaxibigway.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 06/05/2016.
 */
public class wsListaServiciosTomadosConductor
        extends AsyncTask<String,String,List<beansHistorialServiciosCreados>>
implements OnItemClickListener{

    private Context context;
    private String fecha;
    private PreferencesDriver preferencesDriver;
    private GridViewWithHeaderAndFooter grid;
    private Drawable drawable;
    private ProgressDialog progressDialog;
    private Activity activity;
    public static List<beansHistorialServiciosCreados> ListServicios;
    public wsListaServiciosTomadosConductor(Context context,String fecha,GridViewWithHeaderAndFooter grid) {
        this.context = context;
        this.fecha=fecha;
        this.grid=grid;
        activity=(Activity)context;
        Log.d("fecha_servicio",fecha);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preferencesDriver=new PreferencesDriver(context);
        ListServicios=new ArrayList<beansHistorialServiciosCreados>();
        ListServicios.clear();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("cargando.....");
       // progressDialog.setCancelable(false);
        progressDialog.show();
        Log.d("size_clear",String.valueOf(ListServicios.size()));
    }
    @SuppressWarnings("deprecation")
    @Override
    protected List<beansHistorialServiciosCreados> doInBackground(String... params) {
        beansHistorialServiciosCreados row=null;
        SoapObject request = new SoapObject("http://taxibigway.com/soap","WS_SERVICIO_LISTAR");
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        request.addProperty("idCliente", 0);
        request.addProperty("fecServicio", fecha);
        request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
        request.addProperty("idEstadoServicio", 0);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE("http://taxibigway.com/soap");

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call("http://taxibigway.com/soap/WS_SERVICIO_LISTAR", envelope, headerPropertyArrayList);
           //  httpTransport.call("http://taxibigway.com/soap/WS_SERVICIO_LISTAR", envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
            Log.d("vectorServicisListar",responseVector.toString());
            int countVector=responseVector.size();
            for(int i=0;i<countVector;i++){
                SoapObject dataVector=(SoapObject)responseVector.get(i);
                row=new beansHistorialServiciosCreados();
                row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                row.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                row.setHora(dataVector.getProperty("DES_HORA").toString());
                row.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                row.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());
                row.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                row.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
                row.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());
                row.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                row.setNameDistritoInicio(dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                row.setNameZonaIncio(dataVector.getProperty("NOM_ZONA_INICIO").toString());
                row.setNameZonaFin(dataVector.getProperty("NOM_ZONA_FIN").toString());
                row.setNameDistritoFin(dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                row.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                row.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                row.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                row.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                row.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                row.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                row.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                row.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                + "\n" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                //  row.setImageHistorico(drawable);
                int idStatus=Integer.parseInt(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                Log.d("stadoServicios-->",String.valueOf(idStatus));
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
                ListServicios.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.d("error", e.getMessage());
        }
        return ListServicios;
    }

    @Override
    protected void onPostExecute(List<beansHistorialServiciosCreados> listServics) {
        super.onPostExecute(listServics);
        progressDialog.dismiss();
        grid.setAdapter(new GriddAdapterServiciosTomadosConductor(context,listServics));
    }


    @Override
    public void onClick(Context context,String id,String statdoServicio) {

    }
}
