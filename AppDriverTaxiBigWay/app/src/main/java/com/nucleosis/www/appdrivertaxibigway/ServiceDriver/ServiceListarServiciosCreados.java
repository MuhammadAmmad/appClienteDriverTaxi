package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.json.JSONArray;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by karlos on 04/05/2016.
 */
public class ServiceListarServiciosCreados extends Service {
    private TimerTask TimerCronometro;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("servicioCronometro", "CREADO");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final Timer timerCola=new Timer();
        TimerCronometro=new TimerTask() {
            @Override
            public void run() {
                new AsyncTask<String, String, List<beansHistorialServiciosCreados>>() {
                     List<beansHistorialServiciosCreados> ListServicios;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        ListServicios=new ArrayList<beansHistorialServiciosCreados>();
                    }

                    @Override
                    protected List<beansHistorialServiciosCreados> doInBackground(String... params) {
                        beansHistorialServiciosCreados row=null;
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idCliente", "");
                        request.addProperty("fecServicio", "");
                        request.addProperty("idConductor", "");
                        request.addProperty("idEstadoServicio", 1);
                        envelope.setOutputSoapObject(request);
                        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());



                        try {
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction7(), envelope, headerPropertyArrayList);
                            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                            SoapObject response1= (SoapObject) envelope.bodyIn;
                            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
                            int countVector=responseVector.size();
                            for(int i=0;i<countVector;i++){
                                SoapObject dataVector=(SoapObject)responseVector.get(i);
                                row=new beansHistorialServiciosCreados();
                                row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                                row.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                                row.setHora(dataVector.getProperty("DES_HORA").toString());
                                row.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                                row.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());
                                row.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                                row.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
                                row.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());
                                row.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                                row.setNameDistritoInicio(dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                                row.setNameDistritoFin(dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                                row.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                                row.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                                //  row.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                                row.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                                row.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                                row.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                                        + "\n" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                                //  row.setImageHistorico(drawable);
                                ListServicios.add(row);

                            }
                            // SoapObject response2=(SoapObject)response1.getProperty("return");
                            //Log.d("listaxxx", responseVector.toString());
                           // Log.d("sizeVector", String.valueOf(responseVector.size()));
                        } catch (Exception e) {
                            e.printStackTrace();
//            Log.d("error", e.getMessage());
                        }
                        return ListServicios;
                    }

                    @Override
                    protected void onPostExecute(List<beansHistorialServiciosCreados> listServiceiosCreados) {
                        super.onPostExecute(listServiceiosCreados);
                        String jsonArray=new Gson().toJson(listServiceiosCreados);
                      //  Log.d("jsonArray", jsonArray.toString());
                        Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE_2)
                                .putExtra(Utils.EXTRA_MEMORY_2, jsonArray);
                        LocalBroadcastManager.
                                getInstance(ServiceListarServiciosCreados.this).sendBroadcast(localIntent);
                    }
                }.execute();
            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0,10000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TimerCronometro.cancel();
    }
}
