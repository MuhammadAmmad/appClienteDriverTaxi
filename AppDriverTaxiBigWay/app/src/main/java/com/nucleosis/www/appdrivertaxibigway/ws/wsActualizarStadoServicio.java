package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 08/05/2016.
 */
public class wsActualizarStadoServicio extends AsyncTask<String,String,String[]> {
    private Context context;
    private PreferencesDriver preferencesDriver;
    private String idDriver;
    private String idServicio;
    private String IdStadoServicio;
    private String desMovito;
    public wsActualizarStadoServicio(Context context,String idDriver,String idServicio, String idStadoServicio, String desMovito) {
        this.context = context;
        IdStadoServicio = idStadoServicio;
        this.desMovito = desMovito;
        this.idServicio=idServicio;
        this.idDriver=idDriver;
        Log.d("idObtener",idDriver);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        Log.d("eta_aqui","doInBackGround");
        String[] data=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;

        request.addProperty("idServicio", Integer.parseInt(idServicio));
        request.addProperty("idTurno", 0);
        request.addProperty("idConductor",Integer.parseInt(idDriver));
        request.addProperty("idAuto", 0);
        request.addProperty("idEstadoServicio", Integer.parseInt(IdStadoServicio));
        request.addProperty("desMotivo", desMovito);
        request.addProperty("usrActualizacion", 0);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            if(response2.hasProperty("IND_OPERACION")){
                data[0]=response2.getProperty("IND_OPERACION").toString();
                data[1]=response2.getProperty("DES_MENSAJE").toString();

            }else{
                data[0]="";
                data[1]="";
            }

           // Log.d("actualizarServicio", response2.toString());


            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            data[0]="";
            data[1]="";
            //Log.d("error", e.printStackTrace());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        if(data!=null){
            if(data[0].equals("1")){
                Toast.makeText(context,"El servicio fue cancelado", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"Error al cancelar el servicio",Toast.LENGTH_LONG).show();
        }
    }
}


