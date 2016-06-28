package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by carlos.lopez on 27/06/2016.
 */
public class wsCalificarConductor  extends AsyncTask<String,String,String> {
    private Context context;
    private Activity activity;
    private int idtipoCalificion;
    private String idDriver;
    private String idServicio;
    private String comentario="";
    private Fichero fichero;
    private PreferencesCliente preferencesCliente;
    public wsCalificarConductor(Activity activity,String idDriver,
                                String idServicio,int idtipoCalificion,
                                String comentario) {
        this.activity=activity;
        this.idServicio=idServicio;
        this.idDriver=idDriver;
        this.comentario=comentario;
        this.idtipoCalificion=idtipoCalificion;
        context=(Context)activity;
        preferencesCliente=new PreferencesCliente(context);
        fichero=new Fichero(context);

        Log.d("comentario_Xxx",comentario);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String idOperacion="0";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo19());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        String[] dataSalida=new String[2];
        request.addProperty("idServicio",idServicio);
        request.addProperty("idClienteCalificador", Integer.parseInt(preferencesCliente.OpenIdCliente()));
        request.addProperty("idConductorCalificado",Integer.parseInt(idDriver));
        request.addProperty("idCalificacionTipo",idtipoCalificion);
        request.addProperty("desObservacion",comentario);
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
            Log.d("responseCalificacionXXX", response2.toString());
            idOperacion=response2.getPropertyAsString("IND_OPERACION");
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
            idOperacion="";

        }
        return idOperacion;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        switch (Integer.parseInt(s)){
            case 1:
                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
                activity.finish();
                break;
            case 0:
                Log.d("error_","errorr!!!");
                break;

        }
    }
}
