package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by karlos on 15/05/2016.
 */
public class wsActualizarServicio extends AsyncTask<String,String,String[]> {
    private ProgressDialog progressDialog;
    private Context context;
    private Activity activity;
    private Fichero fichero;
    private PreferencesDriver preferencesDriver;
    private JSONObject jsonServicio;
    private JSONObject OBJETO_IdDISTRITO_IDZONA_INCIO;
    private JSONObject OBJETO_IdDISTRITO_IDZONA_FIN;
    private JSONArray JSON_SERVICIOS_TOMADOS_CONDUCTOR;
    private String idServicio;
    private String indAire;
    private String impAireAcondic;
    private String impPeaje;
    private String impTiempoEspera;
    private String minutosTiempoEspera;
    private int idDriver;
    private int idTurno;
    private int idVehiculo;
    private String idDistritoIncio;
    private String idZonaIncio;
    private String idDistritoFin;
    private String idZonaFin;
    private String direccionIncio;
    private String direccionFinal;
    private String impServicio;
    public wsActualizarServicio(Activity activity,
                                String idServicio,
                                String indAire,
                                String impAireAcondic,
                                String impPeaje,
                                String impTiempoEspera,
                                String minutosTiempoEspera,
                                String impServicio,
                                String idDistritoIncio,
                                String idZonaIncio,
                                String direccionIncio,
                                String idDistritoFin,
                                String idZonaFin,
                                String direccionFinal
                                ) {
        this.activity = activity;
        this.idServicio=idServicio;
        this.indAire=indAire;
        this.impPeaje=impPeaje;
        this.impAireAcondic=impAireAcondic;
        this.impTiempoEspera=impTiempoEspera;
        this.minutosTiempoEspera=minutosTiempoEspera;
        this.idDistritoIncio=idDistritoIncio;
        this.idDistritoFin=idDistritoFin;
        this.idZonaIncio=idZonaIncio;
        this.idZonaFin=idZonaFin;
        this.direccionIncio=direccionIncio;
        this.direccionFinal=direccionFinal;
        this.impServicio=impServicio;
        context=(Context)activity;
        fichero=new Fichero(context);
        preferencesDriver=new PreferencesDriver(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Espere....");
        progressDialog.show();
        progressDialog.setCancelable(false);

        jsonServicio=fichero.ExtraerServicioUnico();
        idDriver=Integer.parseInt(preferencesDriver.OpenIdDriver());
        idTurno=Integer.parseInt(preferencesDriver.ExtraerIdTurno());
        idVehiculo=Integer.parseInt(preferencesDriver.ExtraerIdVehiculo());
        JSON_SERVICIOS_TOMADOS_CONDUCTOR=fichero.ExtraerListaServiciosTomadoConductor();
        OBJETO_IdDISTRITO_IDZONA_INCIO=fichero.EXTRAER_IdZONA_IDDISTRITO_INCIO();
        OBJETO_IdDISTRITO_IDZONA_FIN=fichero.EXTRAER_IdZONA_IDDISTRITO_FIN();
    }

    @Override
    protected String[] doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo17());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        String[] dataSalida=new String[2];

        for(int i=0;i<JSON_SERVICIOS_TOMADOS_CONDUCTOR.length();i++){
            try {
                String idServis=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("idServicio");
                if(idServicio.equals(idServis)){
                    InsertaData(i);
                    request.addProperty("idServicio", Integer.parseInt(idServicio));
                    request.addProperty("fecServicio", JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("FechaFormat"));
                    request.addProperty("desHora", JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("Hora"));
                    request.addProperty("impServicio", impServicio);
                    request.addProperty("desServicio", JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("DescripcionServicion"));

                    request.addProperty("indAireAcondicionado", Integer.parseInt(indAire));
                    request.addProperty("impAireAcondicionado",impAireAcondic );
                    request.addProperty("impPeaje",impPeaje);
                    request.addProperty("impTiempoEspera", impTiempoEspera);
                    request.addProperty("numMinutoTiempoEspera",minutosTiempoEspera );

                    request.addProperty("usrActualizacion",0);
                    request.addProperty("idCliente", Integer.parseInt(JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("idCliente")));
                    request.addProperty("idTurno", idTurno);
                    request.addProperty("idConductor", idDriver);
                    request.addProperty("idAuto", idVehiculo);

                    request.addProperty("idDistritoInicio",Integer.parseInt(idDistritoIncio));
                    request.addProperty("idZonaInicio", Integer.parseInt(idZonaIncio));
                    request.addProperty("desDireccionInicio",direccionIncio);

                    request.addProperty("idDistritoFinal", Integer.parseInt(idDistritoFin));
                    request.addProperty("idZonaFinal", Integer.parseInt(idZonaFin));
                    request.addProperty("desDireccionFinal",direccionFinal);
                    request.addProperty("idAutoTipo", Integer.parseInt(JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("idTipoAuto")));

                    i=JSON_SERVICIOS_TOMADOS_CONDUCTOR.length();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.d("rescuesUpdate",request.toString());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction17(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("restActualizarService",response2.toString());
            if(response2.hasProperty("IND_OPERACION")){
                if(response2.getPropertyAsString("IND_OPERACION").equals("1")){
                    dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                    dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
                }else if(response2.getPropertyAsString("IND_OPERACION").equals("2")){
                    dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                    dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return dataSalida;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        Toast.makeText(context,data[1],Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }


    private void InsertaData(int i){

        if(impServicio.length()==0){
            try {
                impServicio=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("importeServicio");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(indAire.length()==0){
            try {
                indAire=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("indAireAcondicionado");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(impAireAcondic.length()==0){
            try {
                impAireAcondic=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("importeAireAcondicionado");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(impPeaje.length()==0){
            try {
                impPeaje=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("importePeaje");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(impTiempoEspera.length()==0){
            try {
                impTiempoEspera=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("importeTiempoEspera");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(minutosTiempoEspera.length()==0){
            try {
                minutosTiempoEspera=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("numeroMinutoTiempoEspera");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(idDistritoIncio.length()==0){
            try {
                idDistritoIncio=OBJETO_IdDISTRITO_IDZONA_INCIO.getString("idDistrito");
                idZonaIncio=OBJETO_IdDISTRITO_IDZONA_INCIO.getString("idZona");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(idDistritoFin.length()==0){
            try {
                idDistritoFin=OBJETO_IdDISTRITO_IDZONA_FIN.getString("idDistrito");
                idZonaFin=OBJETO_IdDISTRITO_IDZONA_FIN.getString("idZona");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(direccionIncio.length()==0){
            try {
                direccionIncio=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("DireccionIncio");
                direccionFinal=JSON_SERVICIOS_TOMADOS_CONDUCTOR.getJSONObject(i).getString("direccionFinal");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
