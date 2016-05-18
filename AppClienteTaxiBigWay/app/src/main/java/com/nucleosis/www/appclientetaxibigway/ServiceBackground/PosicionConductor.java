package com.nucleosis.www.appclientetaxibigway.ServiceBackground;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Constantes.Utils;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

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
public class PosicionConductor extends Service {
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
                new AsyncTask<String, String,  String[] >() {
                    String[] Latlog=new String[2];
                    @Override
                    protected  String[]  doInBackground(String... params) {
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo12());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idConductor", "2");
                        envelope.setOutputSoapObject(request);
                        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                        try {
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction12(), envelope, headerPropertyArrayList);
                            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                            SoapObject response1= (SoapObject) envelope.bodyIn;
                            SoapObject response2=(SoapObject)response1.getProperty("return");
                            Log.d("locacionConductor", response2.toString());
                            if(response2.hasProperty("NUM_POSICION_LATITUD")){
                                Latlog[0]=response2.getPropertyAsString("NUM_POSICION_LATITUD").toString();
                                Latlog[1]=response2.getPropertyAsString("NUM_POSICION_LONGITUD").toString();

                            }else{
                                Latlog[0]="";
                                Latlog[1]="";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Latlog[0]="";
                            Latlog[1]="";
                        }
                        return Latlog;
                    }

                    @Override
                    protected void onPostExecute(String[] dataLatLog) {
                        super.onPostExecute(dataLatLog);
                        Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE)
                                .putExtra(Utils.EXTRA_MEMORY,dataLatLog);
                        LocalBroadcastManager.
                                getInstance(PosicionConductor.this).sendBroadcast(localIntent);

                    }
                }.execute();



            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0, 10000);

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
