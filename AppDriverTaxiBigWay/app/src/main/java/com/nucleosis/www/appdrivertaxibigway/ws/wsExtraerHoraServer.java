package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 06/05/2016.
 */
public class wsExtraerHoraServer extends AsyncTask<String,String,JSONObject> {
    private PreferencesDriver preferencesDriver;
    public wsExtraerHoraServer(Context context) {
        this.context = context;
        preferencesDriver=new PreferencesDriver(context);
    }

    private Context context;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject=new JSONObject();
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo10());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction10(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response= (SoapObject) envelope.bodyIn;
            SoapObject response2=(SoapObject)response.getProperty("return");
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
            try {
                preferencesDriver.InsertarFechaHoraActual(
                        json.getString("Fecha").toString(),
                        json.getString("Hora").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //new wsVerificarServicioActivo(context,json).execute();
        Log.d("json..", json.toString());
    }
}
