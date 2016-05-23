package com.nucleosis.www.appclientetaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;

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

        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo15());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction15(), envelope, headerPropertyArrayList);
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
                jsonObjectData.put("numTelefonoEmpesa",response2.getPropertyAsString("NUM_TELEFONO"));
                jsonObjectData.put("direccionEmpresa",response2.getPropertyAsString("DES_DIRECCION"));
                //
            }else{
                jsonObjectData.put("urlFotoConductor","0");
                jsonObjectData.put("impAireAcondicionado","0");
                jsonObjectData.put("impServicioPeaje","0");
                jsonObjectData.put("impMinutoEspera","0");
                jsonObjectData.put("impAutoVip","0");
                jsonObjectData.put("numTelefonoEmpesa","00-000000");
                jsonObjectData.put("direccionEmpresa","-----");
            }
            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();

            //Log.d("error", e.printStackTrace());
        }
        return jsonObjectData;
    }

    @Override
    protected void onPostExecute(JSONObject dataJson) {
        super.onPostExecute(dataJson);
        if(dataJson!=null){
            fichero.InsertarConfiguraciones(dataJson.toString());
            Log.d("config_",fichero.ExtraerConfiguraciones().toString());

            new wsExtraerHoraServer(context).execute();

        }

    }
}
