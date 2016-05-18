package com.nucleosis.www.appclientetaxibigway.ServiceBackground;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Constantes.Utils;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.beansDetalleService;
import com.nucleosis.www.appclientetaxibigway.beans.beansServiciosFechaDetalle;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by carlos.lopez on 18/05/2016.
 */
public class EstadoServiciosCreados extends Service {
    private TimerTask TimerCronometro;
    private PreferencesCliente preferencesCliente;
    private Fichero fichero;
    private JSONObject jsonHoraActual;
    private String fechaActual="";
    private int TIME_OUT=15000;
    private boolean tiempoEsperaConexion=false;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("servicios", "CREADO");
        preferencesCliente=new PreferencesCliente(EstadoServiciosCreados.this);
        fichero=new Fichero(EstadoServiciosCreados.this);
        jsonHoraActual=fichero.ExtraerFechaHoraActual();
        if(jsonHoraActual!=null){
            try {
                fechaActual=jsonHoraActual.getString("Fecha");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final ActivityManager activityManager =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final Timer timerCola=new Timer();

        TimerCronometro=new TimerTask() {
            @Override
            public void run() {
                new AsyncTask<String, String,   List<beansDetalleService> >() {

                    beansDetalleService row2=null;
                    List<beansDetalleService> listDetalleServicio;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        listDetalleServicio=new ArrayList<beansDetalleService>();

                    }

                    String[] Latlog=new String[2];
                    @Override
                    protected   List<beansDetalleService>  doInBackground(String... params) {
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idCliente", Integer.parseInt(preferencesCliente.OpenIdCliente()));
                        request.addProperty("fecServicio", fechaActual);
                        request.addProperty("idConductor", "");
                        request.addProperty("idEstadoServicio", "");
                        envelope.setOutputSoapObject(request);
                        try {
                            HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(),TIME_OUT);
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
                           // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                            SoapObject response1= (SoapObject) envelope.bodyIn;
                            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
                            Log.d("verctorSer..",responseVector.toString());
                            int countVector=responseVector.size();
                            for(int i=0;i<countVector;i++){
                                row2=new beansDetalleService();
                                SoapObject dataVector=(SoapObject)responseVector.get(i);
                                row2.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                                row2.setIdStadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                                if(dataVector.getProperty("NOM_APE_CONDUCTOR").toString()!=null){
                                    row2.setNameConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                                }else {
                                    row2.setNameConductor("");
                                }
                                row2.setStadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                                row2.setStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                                if(dataVector.getProperty("ID_CONDUCTOR").toString()!=null){
                                    row2.setIdConductor(dataVector.getProperty("ID_CONDUCTOR").toString());
                                }else {
                                    row2.setIdConductor("");
                                }

                                if(dataVector.getProperty("ID_TURNO").toString()!=null){
                                    row2.setIdTurno(dataVector.getProperty("ID_TURNO").toString());
                                }else {
                                    row2.setIdTurno("");
                                }
                                listDetalleServicio.add(row2);

                            }


                        }catch (InterruptedIOException e){
                            tiempoEsperaConexion=true;
                        }catch (Exception e) {
                            e.printStackTrace();

                        }
                        return listDetalleServicio;
                    }

                    @Override
                    protected void onPostExecute(List<beansDetalleService>  dataLatLog) {
                        super.onPostExecute(dataLatLog);
                        Gson json=new Gson();
                        if(dataLatLog!=null){
                            String listServiciosCliente=json.toJson(dataLatLog);
                            Log.d("lisQQQQ-->",listServiciosCliente.toString());

                            Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE)
                                    .putExtra(Utils.EXTRA_MEMORY,listServiciosCliente);
                            LocalBroadcastManager.
                                    getInstance(EstadoServiciosCreados.this).sendBroadcast(localIntent);
                        }


                    }
                }.execute();



            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0, 5000);

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
        Log.d("servicios", "DESTRUIDO");
    }

}
