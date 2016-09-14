package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
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
 * Created by carlos.lopez on 04/04/2016.
 */
public class UpdateLocationDriver extends AsyncTask<String,String,String[]> {
    private Context context;
    private String lat;
    private String lon;
    private PreferencesDriver preferencesDriver;
    private String ID_DRIVER;
    private Fichero fichero;
    private JSONObject jsonFecha;
    private String idStadoConductor;
    public UpdateLocationDriver(Context context, String lat, String lon,String idStadoConductor) {
        this.context = context;
        this.lat = lat;
        this.lon = lon;
        this.idStadoConductor=idStadoConductor;
        preferencesDriver=new PreferencesDriver(context);
        fichero=new Fichero(context);
        ID_DRIVER =preferencesDriver.OpenIdDriver();
        Log.d("eta_aqui", ID_DRIVER);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String[] doInBackground(String... params) {
       // Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String[] data=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo3());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        Log.d("latLon_",String.valueOf(lat)+"-->"+lon);
        request.addProperty("idConductor", Integer.parseInt(ID_DRIVER));
        request.addProperty("numPosicionLatitud", lat);
        request.addProperty("numPosicionLongitud",lon);
        request.addProperty("idConductorTurnoEstado",idStadoConductor);
       // Log.d("request_x",request.toString());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction3(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
           // Log.d("responseLongitud",response2.toString());
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
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        if(data!=null){
            if(data[0].equals("1")){
                jsonFecha=preferencesDriver.ExtraerHoraSistema();
                JSONObject jsonCoordenada=null;

                try {
                    /*Log.d("mensajeLocation",data[1].toString()+jsonFecha.getString("fechaServidor").toString()+" "+
                                            jsonFecha.getString("horaServidor"));*/
                    jsonCoordenada=new JSONObject();
                    jsonCoordenada.put("FechaCoordenda",jsonFecha.getString("fechaServidor").toString());
                    jsonCoordenada.put("HoraCoordenda",jsonFecha.getString("horaServidor").toString());
                    fichero.InsertarFechaHoraUltimaDeCoordenadas(jsonCoordenada.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
