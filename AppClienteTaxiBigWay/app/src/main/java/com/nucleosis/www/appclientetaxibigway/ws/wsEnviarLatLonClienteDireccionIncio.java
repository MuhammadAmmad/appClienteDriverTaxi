package com.nucleosis.www.appclientetaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;

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
public class wsEnviarLatLonClienteDireccionIncio extends AsyncTask<String,String,String[]> {
    private Context context;
    private PreferencesCliente preferencesCliente;
    private Fichero fichero;
    private JSONObject jsonDataLocationAddresIncio;
    private int idCliente;
    public wsEnviarLatLonClienteDireccionIncio(Context context) {
        this.context = context;
        preferencesCliente=new PreferencesCliente(context);
        fichero=new Fichero(context); idCliente=Integer.parseInt(preferencesCliente.OpenIdCliente());

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        idCliente=Integer.parseInt(preferencesCliente.OpenIdCliente());
        jsonDataLocationAddresIncio=fichero.ExtraerCoordendaDirrecionIncio();
    }

    @Override
    protected String[] doInBackground(String... params) {
        Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String[] data=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo13());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("idCliente", idCliente);
        try {
            Log.d("latLon_",jsonDataLocationAddresIncio.getString("latitud")+"-->"+jsonDataLocationAddresIncio.getString("longitud"));
            request.addProperty("numPosicionLatitud", jsonDataLocationAddresIncio.getString("latitud"));
            request.addProperty("numPosicionLongitud",jsonDataLocationAddresIncio.getString("longitud"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction13(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("responseLongitud",response2.toString());
            if(response2.hasProperty("IND_OPERACION")){
                data[0]=response2.getPropertyAsString("IND_OPERACION").toString();
                data[1]=response2.getPropertyAsString("DES_MENSAJE").toString();
            }else {
                data[0]="";
                data[1]="";
            }
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
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
    }
}
