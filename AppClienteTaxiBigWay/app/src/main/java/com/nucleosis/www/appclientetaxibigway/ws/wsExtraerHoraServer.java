package com.nucleosis.www.appclientetaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by karlos on 06/05/2016.
 */
public class wsExtraerHoraServer extends AsyncTask<String,String,JSONObject> {
    private Fichero fichero;
    public wsExtraerHoraServer(Context context) {
        this.context = context;
        fichero=new Fichero(context);
    }

    private Context context;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject=new JSONObject();
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo16());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction16(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response= (SoapObject) envelope.bodyIn;
            SoapObject response2=(SoapObject)response.getProperty("return");
            Log.d("responFecha",response2.toString());
            jsonObject.put("Fecha",response2.getPropertyAsString("FEC_ACTUAL").toString());
            jsonObject.put("Hora",response2.getPropertyAsString("DES_HORA").toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return jsonObject;
    }
    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        if(json!=null){
            Log.d("json..", json.toString());
                fichero.InsertarFechaHoraActual(json.toString());
           if(fichero.ExtraerFechaHoraActual()!=null){
               Log.d("getFechaHoora",fichero.ExtraerFechaHoraActual().toString());
           }

        }

        //new wsVerificarServicioActivo(context,json).execute();

    }
}
