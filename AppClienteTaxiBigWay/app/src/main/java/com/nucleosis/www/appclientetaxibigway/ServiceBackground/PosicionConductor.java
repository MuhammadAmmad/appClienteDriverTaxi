package com.nucleosis.www.appclientetaxibigway.ServiceBackground;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Constantes.Utils;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.json.JSONException;
import org.json.JSONObject;
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
   private Fichero fichero;
    private int idConductor=0;
    @Override
    public void onCreate() {
        super.onCreate();
        fichero=new Fichero(PosicionConductor.this);
        try {
            idConductor=Integer.parseInt(fichero.ExtraerIdConductorServicio().getString("idConductor"));
            Log.d("id_xt",String.valueOf(idConductor));
        } catch (JSONException e) {
            e.printStackTrace();
            idConductor=0;
        }
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
                new AsyncTask<String, String, JSONObject>() {

                    @Override
                    protected  JSONObject doInBackground(String... params) {
                        JSONObject jsonLocation=new JSONObject();
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo12());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idConductor", idConductor);
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
                                jsonLocation.put("latConductor",response2.getPropertyAsString("NUM_POSICION_LATITUD").toString());
                                jsonLocation.put("lonConductor",response2.getPropertyAsString("NUM_POSICION_LONGITUD").toString());


                            }else{
                                jsonLocation.put("latConductor","");
                                jsonLocation.put("latConductor","");
                            }

                        }catch (JSONException e1) {
                            e1.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                jsonLocation.put("latConductor","");
                                jsonLocation.put("latConductor","");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                        return jsonLocation;
                    }

                    @Override
                    protected void onPostExecute(JSONObject jsonCoordendasConductor) {
                        super.onPostExecute(jsonCoordendasConductor);
                        Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE)
                                .putExtra(Utils.EXTRA_MEMORY,jsonCoordendasConductor.toString());
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
