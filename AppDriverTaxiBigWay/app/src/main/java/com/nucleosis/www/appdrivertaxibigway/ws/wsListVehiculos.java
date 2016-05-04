package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.nucleosis.www.appdrivertaxibigway.Adapters.AdapterListVehiculos;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansVehiculoConductor;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by carlos.lopez on 04/05/2016.
 */
public class wsListVehiculos extends AsyncTask<String,String,List<beansVehiculoConductor>> {
    private Context context;
    private View view;
    private PreferencesDriver preferencesDriver;
    public  static List<beansVehiculoConductor> listVehiculos;
    private componentesR compR;

    public wsListVehiculos(Context context, View view) {
        this.context = context;
        this.view=view;
        preferencesDriver=new PreferencesDriver(context);
        compR=new componentesR(context);
        compR.Controls_alert_elegir_auto_conductor(view);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listVehiculos=new ArrayList<beansVehiculoConductor>();
    }

    @Override
    protected List<beansVehiculoConductor> doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo5());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("numPlaca","");
        request.addProperty("nomMarca", "");
        request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction5(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            // SoapObject response2= (SoapObject)response1.getProperty("return");+
            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
            int contVehiculos=responseVector.size();
            beansVehiculoConductor row=null;
            for(int i=0;i<contVehiculos;i++){
                SoapObject dataVector=(SoapObject)responseVector.get(i);
                row=new beansVehiculoConductor();
                row.setIdConductor(Integer.parseInt(preferencesDriver.OpenIdDriver()));
                row.setIdVehiculo(Integer.parseInt(dataVector.getPropertyAsString("ID_AUTO")));
                row.setPlacaVehiculo(dataVector.getPropertyAsString("NUM_PLACA"));
                row.setNombreMarca(dataVector.getPropertyAsString("NUM_PLACA"));
                row.setDesAuto(dataVector.getPropertyAsString("DES_AUTO"));
                row.setColorVehiculo(dataVector.getPropertyAsString("DES_COLOR"));
                row.setFechaExpiracionSOAT(dataVector.getPropertyAsString("FEC_EXPIRACION_SOAT"));
                row.setFechaRevisionTecnica(dataVector.getPropertyAsString("FEC_REVISION_TECNICA"));
                row.setNombreFoto_1(dataVector.getPropertyAsString("DES_PRIMERA_FOTO"));
                row.setNombreFoto_2(dataVector.getPropertyAsString("DES_SEGUNDA_FOTO"));
                row.setNameConductor(dataVector.getPropertyAsString("NOM_COMPLETO_CONDUCTOR"));
                listVehiculos.add(row);
            }
                    /*Log.d("responseCoducntor",responseVector.toString());
                    Log.d("lista", String.valueOf(listVehiculos.size()));*/
            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return listVehiculos;
    }

    @Override
    protected void onPostExecute(final List<beansVehiculoConductor> listVehiculos) {
        super.onPostExecute(listVehiculos);
        AdapterListVehiculos adapterListVehiculos=new AdapterListVehiculos(context,listVehiculos);
        compR.getSpinerVehiculo().setAdapter(adapterListVehiculos);

    }
}
