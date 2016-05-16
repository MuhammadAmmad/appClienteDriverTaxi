package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

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
public class wsObtenerDireccionIncioCliente extends AsyncTask<String,String,String[]> {
    private Context context;
    private String idCliente;
    private Fichero fichero;
    public wsObtenerDireccionIncioCliente(Context context,String idCliente) {
        this.context = context;
        this.idCliente=idCliente;
        fichero=new Fichero(context);
        Log.d("idCliente_ob",idCliente);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo18());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        String[] dataSalida=new String[2];
        request.addProperty("idCliente", Integer.parseInt(idCliente));
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

 /*   <idConductor xsi:type="xsd:int">?</idConductor>
        <idAuto xsi:type="xsd:int">?</idAuto>
        <usrRegistro xsi:type="xsd:int">?</usrRegistro>
*/
        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction18(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("responsesCliente",response2.toString());
            if(response2.hasProperty("NUM_POSICION_LATITUD") && response2.hasProperty("NUM_POSICION_LONGITUD")){
                    dataSalida[0]=response2.getPropertyAsString("NUM_POSICION_LATITUD");
                    dataSalida[1]=response2.getPropertyAsString("NUM_POSICION_LONGITUD");

            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return dataSalida;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        if(data!=null){
            JSONObject jsonDataCoordenadaAddresClienteIncio=new JSONObject();

            try {
                jsonDataCoordenadaAddresClienteIncio.put("latitud",data[0]);
                jsonDataCoordenadaAddresClienteIncio.put("longitud",data[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            fichero.InsertarCoordendaDirrecionIncioCliente(jsonDataCoordenadaAddresClienteIncio.toString());
            Log.d("ExtracLATLON",fichero.ExtraerCoordendaDirrecionIncioCliente().toString());
        }
    }
}
