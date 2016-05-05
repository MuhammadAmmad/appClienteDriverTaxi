package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by carlos.lopez on 05/05/2016.
 */
public class wsAsignarServicioConductor extends AsyncTask<String,String,String> {
    private Context context;
    private PreferencesDriver preferencesDriver;
    private String idServicio;
    public wsAsignarServicioConductor(Context context,String idServicio) {
        this.context = context;
        this.idServicio=idServicio;
        preferencesDriver=new PreferencesDriver(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {


       /* <idServicio xsi:type="xsd:int">?</idServicio>
        <idTurno xsi:type="xsd:int">?</idTurno>
        <idConductor xsi:type="xsd:int">?</idConductor>
        <idAuto xsi:type="xsd:int">?</idAuto>
        <usrActualizacion xsi:type="xsd:int">?</usrActualizacion>*/

                SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo9());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;

        request.addProperty("idServicio", Integer.parseInt(idServicio));
        request.addProperty("idTurno", "");
        request.addProperty("idConductor",preferencesDriver.OpenIdDriver());
        request.addProperty("idAuto", preferencesDriver.ExtraerIdVehiculo());
        request.addProperty("usrActualizacion", "");
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction9(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");



            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return null;
    }
}
