package com.nucleosis.www.appdrivertaxibigway.ws;

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
 * Created by carlos.lopez on 04/04/2016.
 */
public class UpdateLocationDriver extends AsyncTask<String,String,String> {
    private Context context;
    private String lat;
    private String lon;
    PreferencesDriver preferencesDriver;
    private String ID_DRIVER;

    public UpdateLocationDriver(Context context, String lat, String lon) {
        this.context = context;
        this.lat = lat;
        this.lon = lon;
        preferencesDriver=new PreferencesDriver(context);
        ID_DRIVER =preferencesDriver.OpenIdDriver();
        Log.d("eta_aqui", ID_DRIVER);
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String msnLogin="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo3());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("idConductor", Integer.parseInt(ID_DRIVER));
        Log.d("latLon_",String.valueOf(lat)+"-->"+lon);
        request.addProperty("numPosicionLatitud", lat);
        request.addProperty("numPosicionLongitud",lon);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction3(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("responseLongitud",response1.toString());

            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return msnLogin;
    }

}
