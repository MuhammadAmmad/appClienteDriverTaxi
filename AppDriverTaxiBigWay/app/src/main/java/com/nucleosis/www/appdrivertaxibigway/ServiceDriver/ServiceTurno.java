package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by carlos.lopez on 04/05/2016.
 */
public class ServiceTurno extends Service {
    private TimerTask TimerCronometro;
    private PreferencesDriver preferencesDriver;
    @Override
    public void onCreate() {
        super.onCreate();
        preferencesDriver=new PreferencesDriver(ServiceTurno.this);
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
                new AsyncTask<String, String, String[]>() {
                    @Override
                    protected String[] doInBackground(String... params) {
                        String[] dataTurno=new String[2];

                        String StadoTurno="";
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo8());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;

                        request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
                        envelope.setOutputSoapObject(request);
                        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                        try {
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction8(), envelope, headerPropertyArrayList);
                            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                            SoapObject response1= (SoapObject) envelope.bodyIn;
                            SoapObject response2= (SoapObject)response1.getProperty("return");
                          //  Log.d("aaaTurno", response2.toString());
                            if(response2.hasProperty("IND_ESTADO_TURNO")){
                                StadoTurno=response2.getPropertyAsString("IND_ESTADO_TURNO");
                                dataTurno[0]=response2.getPropertyAsString("IND_ESTADO_TURNO");
                                dataTurno[1]=response2.getPropertyAsString("ID_TURNO");
                            }else {
                                dataTurno[0]="0";
                                dataTurno[1]="-1";
                            }
                            //  Log.d("response",response2.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Log.d("error", e.printStackTrace());
                        }
                        return dataTurno;
                    }

                    @Override
                    protected void onPostExecute(String[] data) {
                        super.onPostExecute(data);
                        if(data[0].length()!=0){
                            preferencesDriver.InsertarIdTurno(data[1]);
                          //  Log.d("idTurno...",preferencesDriver.ExtraerIdTurno());
                            Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE)
                                    .putExtra(Utils.EXTRA_MEMORY,data[0]);
                            LocalBroadcastManager.
                                    getInstance(ServiceTurno.this).sendBroadcast(localIntent);
                        }
                    }
                }.execute();



            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0,1000);
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
        Log.d("servicioCronometro", "DESTRUIDO");
    }
}
