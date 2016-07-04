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
 * Created by karlos on 14/05/2016.
 */
public class wsExtraerConfiguracionAdicionales extends AsyncTask<String,String,JSONObject> {
    private Context context;
    private Fichero fichero;
    public wsExtraerConfiguracionAdicionales(Context context) {
        this.context = context;
        fichero=new Fichero(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        Log.d("eta_aqui", "doInBackGround");
        JSONObject jsonObjectData=new JSONObject();

        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo13());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction13(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");

            Log.d("respnseConfiguracion",response2.toString());

            if(response2.hasProperty("NOM_RUTA_FOTO_CONDUCTOR")){
                jsonObjectData.put("urlFotoConductor",response2.getPropertyAsString("NOM_RUTA_FOTO_CONDUCTOR"));
                jsonObjectData.put("impAireAcondicionado",response2.getPrimitiveProperty("IMP_SERVICIO_AIRE_ACONDICIONADO"));
                jsonObjectData.put("impServicioPeaje",response2.getPrimitiveProperty("IMP_SERVICIO_PEAJE"));
                jsonObjectData.put("impMinutoEspera",response2.getPropertyAsString("IMP_POR_MINUTO_TIEMPO_ESPERA"));
                jsonObjectData.put("impAutoVip",response2.getPropertyAsString("IMP_POR_AUTO_VIP"));
                jsonObjectData.put("radioServicio",response2.getPropertyAsString("NUM_METRO_RADIO"));
                jsonObjectData.put("direccionEmpresa",response2.getPropertyAsString("DES_DIRECCION"));
                jsonObjectData.put("numTelefonoEmpesa",response2.getPropertyAsString("NUM_TELEFONO"));
                jsonObjectData.put("numTelefonoEmpesa_2",response2.getPropertyAsString("NUM_TELEFONO2"));
                jsonObjectData.put("numTelefonoEmpesa_3",response2.getPropertyAsString("NUM_TELEFONO3"));
                jsonObjectData.put("numTelefonoEmpesa_4",response2.getPropertyAsString("NUM_TELEFONO4"));
            }else{
                jsonObjectData.put("urlFotoConductor","0");
                jsonObjectData.put("impAireAcondicionado","0");
                jsonObjectData.put("impServicioPeaje","0");
                jsonObjectData.put("impMinutoEspera","0");
                jsonObjectData.put("impAutoVip","0");
                jsonObjectData.put("radioServicio","1000");
                jsonObjectData.put("direccionEmpresa","0000000");
                jsonObjectData.put("numTelefonoEmpesa","0000000");
                jsonObjectData.put("numTelefonoEmpesa_2","0000000");
                jsonObjectData.put("numTelefonoEmpesa_3","0000000");
                jsonObjectData.put("numTelefonoEmpesa_4","0000000");

            }
            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jsonObjectData.put("urlFotoConductor","0");
                jsonObjectData.put("impAireAcondicionado","0");
                jsonObjectData.put("impServicioPeaje","0");
                jsonObjectData.put("impMinutoEspera","0");
                jsonObjectData.put("impAutoVip","0");
                jsonObjectData.put("radioServicio","1000");
                jsonObjectData.put("direccionEmpresa","0000000");
                jsonObjectData.put("numTelefonoEmpesa","0000000");
                jsonObjectData.put("numTelefonoEmpesa_2","0000000");
                jsonObjectData.put("numTelefonoEmpesa_3","0000000");
                jsonObjectData.put("numTelefonoEmpesa_4","0000000");

            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            //Log.d("error", e.printStackTrace());
        }
        return jsonObjectData;
    }

    @Override
    protected void onPostExecute(JSONObject dataJson) {
        super.onPostExecute(dataJson);
        if(dataJson!=null){
            fichero.InsertarConfiguraciones(dataJson.toString());
        }

    }
}
