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
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.json.JSONArray;
import org.json.JSONObject;
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
    private Fichero fichero;

    public wsListVehiculos(Context context, View view) {
        this.context = context;
        this.view=view;
        preferencesDriver=new PreferencesDriver(context);
        compR=new componentesR(context);
        compR.Controls_alert_elegir_auto_conductor(view);
        fichero=new Fichero(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listVehiculos=new ArrayList<beansVehiculoConductor>();
    }

    @Override
    protected List<beansVehiculoConductor> doInBackground(String... params) {
        try {

            String json=fichero.ExtraerDataVehiculos();
            JSONArray jsonArray=new JSONArray(json);
            int contVehiculos=jsonArray.length();
            beansVehiculoConductor row=null;
            for(int i=0;i<contVehiculos;i++){
               // SoapObject dataVector=(SoapObject)responseVector.get(i);
                JSONObject dataVector=(JSONObject)jsonArray.get(i);
                row=new beansVehiculoConductor();
                row.setIdConductor(Integer.parseInt(preferencesDriver.OpenIdDriver()));
                row.setIdVehiculo(Integer.parseInt(dataVector.getString("idVehiculo")));
                row.setPlacaVehiculo(dataVector.getString("placaVehiculo"));
                row.setNombreMarca(dataVector.getString("nombreMarca"));
                row.setDesAuto(dataVector.getString("desAuto"));
                row.setColorVehiculo(dataVector.getString("colorVehiculo"));
                row.setFechaExpiracionSOAT(dataVector.getString("fechaExpiracionSOAT"));
                row.setFechaRevisionTecnica(dataVector.getString("fechaRevisionTecnica"));
                row.setNombreFoto_1(dataVector.getString("nombreFoto_1"));
                row.setNombreFoto_2(dataVector.getString("nombreFoto_2"));
                        row.setNameConductor(dataVector.getString("nameConductor"));
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
        AdapterListVehiculos adapterListVehiculos=new
                AdapterListVehiculos(context,listVehiculos);
        compR.getSpinerVehiculo().setAdapter(adapterListVehiculos);

    }
}
