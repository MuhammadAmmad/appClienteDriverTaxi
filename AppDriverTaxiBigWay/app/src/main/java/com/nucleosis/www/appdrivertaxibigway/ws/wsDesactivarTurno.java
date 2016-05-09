package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.locationDriver;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by karlos on 02/05/2016.
 */
public class wsDesactivarTurno extends AsyncTask<String,String,String[]> {
    private Context context;
    private PreferencesDriver preferencesDriver;
    private componentesR compR;

    public wsDesactivarTurno(Context context) {
        this.context = context;
        Activity activity=(Activity)context;
        preferencesDriver=new PreferencesDriver(context);
        compR=new componentesR(context);
        compR.Contros_main_activity(activity);
        Log.d("idConductor",preferencesDriver.OpenIdDriver());

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo6());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        String[] dataSalida=new String[2];
        request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
        request.addProperty("usrActualizacion","");
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());


        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction6(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("responseDesactivarTurno", response2.toString());
            if(response2.hasProperty("IND_OPERACION")){
                dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
            }else{
                dataSalida[0]="";
                dataSalida[1]="";
            }

        } catch (Exception e) {
            e.printStackTrace();
          //  Log.d("error---->",e.getMessage());
            dataSalida[0]="";
            dataSalida[1]="";

        }
        return dataSalida;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        if(data!=null){
            if(data[0].equals("1")){
                Intent intent=new Intent(context,locationDriver.class);
                context.stopService(intent);
                Toast.makeText(context, data[1], Toast.LENGTH_LONG).show();
            }else if(data[0].equals("2")){
                Toast.makeText(context, data[1], Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "Errorl al desactivar turno !!!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
