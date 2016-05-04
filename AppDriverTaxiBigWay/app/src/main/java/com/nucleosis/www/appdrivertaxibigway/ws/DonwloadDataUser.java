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
 * Created by karlos on 02/04/2016.
 */
public class DonwloadDataUser extends
    AsyncTask<String,String,List<beansDataDriver>> {
    private Context context;
    PreferencesDriver preferencesDriver;
    public DonwloadDataUser(Context context) {
        this.context = context;
        preferencesDriver=new PreferencesDriver(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<beansDataDriver> doInBackground(String... params) {
        Log.d("eta_aqui","doInBackGround");
        List<beansDataDriver> listData=null;
        beansDataDriver row=null;
        String codLogin="";
        String msnLogin="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo2());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;

        request.addProperty("numDocumento", params[0].toString());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction2(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            listData=new ArrayList<beansDataDriver>();
            Log.d("dataUser", response1.getPropertyAsString("return").toString());
            Log.d("idUser", response2.getPropertyAsString("ID_CONDUCTOR").toString());
                        row=new beansDataDriver();
                        row.setIdDriver(response2.getPropertyAsString("ID_CONDUCTOR").toString());
                        row.setNameDriver(response2.getPropertyAsString("NOM_CONDUCTOR").toString());
                        row.setApellidoDriver(response2.getPropertyAsString("APE_CONDUCTOR").toString());
                        row.setNumCelular(response2.getPropertyAsString("NUM_CEL").toString());
                        row.setNumTelefono(response2.getPropertyAsString("NUM_TELEFONO").toString());
                        row.setEmailDriver(response2.getPropertyAsString("DES_EMAIL").toString());
                        row.setNumDNI(response2.getPropertyAsString("NUM_DOCUMENTO_IDENTIDAD").toString());
                        row.setFechaLicenciaExpiracion(response2.getPropertyAsString("FEC_EXPIRACION_LICENCIA").toString());
                        row.setURLPhotoDriver(response2.getPropertyAsString("NOM_RUTA_FOTO_CONDUCTOR").toString()+
                                response2.getPropertyAsString("DES_FOTO").toString());
                listData.add(row);


            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return listData;
    }

    @Override
    protected void onPostExecute(List<beansDataDriver> beansDataDrivers) {
        preferencesDriver.InsertDataDriver(beansDataDrivers);
        super.onPostExecute(beansDataDrivers);
    }
}
