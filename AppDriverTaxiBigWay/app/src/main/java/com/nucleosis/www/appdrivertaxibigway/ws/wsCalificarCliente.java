package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by karlos on 21/06/2016.
 */
public class wsCalificarCliente extends AsyncTask<String,String,String> {
    private Context context;
    private Activity activity;
    private int idtipoCalificion;
    private String idCliente;
    private String idServicio;
    private PreferencesDriver preferencesDriver;
    public wsCalificarCliente(Activity activity,String idCliente,String idServicio,int idtipoCalificion) {
        this.activity=activity;
        this.idServicio=idServicio;
        this.idCliente=idCliente;
        this.idtipoCalificion=idtipoCalificion;
        context=(Context)activity;

        preferencesDriver=new PreferencesDriver(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo19());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        String[] dataSalida=new String[2];
        request.addProperty("idServicio",idServicio);
        request.addProperty("idConductorCalificador", Integer.parseInt(preferencesDriver.OpenIdDriver()));
        request.addProperty("idClienteCalificado",Integer.parseInt(idCliente));
        request.addProperty("idCalificacionTipo",idtipoCalificion);
        request.addProperty("desObservacion","");
        request.addProperty("idUsuario",0);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());


        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction19(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("responseCalificacion", response2.toString());
         /*   if(response2.hasProperty("IND_OPERACION")){
                dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
            }else{
                dataSalida[0]="";
                dataSalida[1]="";
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            //  Log.d("error---->",e.getMessage());
            dataSalida[0]="";
            dataSalida[1]="";

        }
        return null;
    }
}
