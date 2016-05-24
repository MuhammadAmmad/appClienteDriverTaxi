package com.nucleosis.www.appclientetaxibigway.ServiceBackground;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.ListaServicios;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.beansDetalleService;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by karlos on 22/05/2016.
 */
public class AlarmaLLegadaConductor extends Service {
    private TimerTask TimerCronometro;
    private int sw=0;
    private PreferencesCliente preferencesCliente;
    private Fichero fichero;
    private JSONObject jsonFecha;
    private String fechaActual;
    private int TIME_OUT=10000;
    private boolean tiempoEsperaConexion=false;
    private TextToSpeech t1;
    @Override
    public void onCreate() {
        super.onCreate();
        preferencesCliente=new PreferencesCliente(AlarmaLLegadaConductor.this);
        fichero=new Fichero(AlarmaLLegadaConductor.this);
        jsonFecha=fichero.ExtraerFechaHoraActual();
        if(jsonFecha!=null){
            try {
                fechaActual=jsonFecha.getString("Fecha");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            fechaActual="0000-00-00";
        }
        Log.d("serviceAlarma","creado");
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    // t1.setLanguage(Locale.US);
                    Locale local=new Locale("spa","es");
                    t1.setLanguage(local);

                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Timer timerCola=new Timer();

        TimerCronometro=new TimerTask() {
            @Override
            public void run() {
                new AsyncTask<String, String, JSONObject>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected JSONObject doInBackground(String... params) {
                        JSONObject jsonStadoUltimoServicio=new JSONObject();
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idCliente", Integer.parseInt(preferencesCliente.OpenIdCliente()));
                        request.addProperty("fecServicio", fechaActual);
                        request.addProperty("idConductor", "");
                        request.addProperty("idEstadoServicio", "");
                        envelope.setOutputSoapObject(request);
                        Log.d("resquest_a",request.toString());
                        try {
                            HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(),TIME_OUT);
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
                       //  httpTransport.call(ConstantsWS.getSoapAction11(), envelope);
                            SoapObject response1= (SoapObject) envelope.bodyIn;
                            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
                            Log.d("verctorSerQW..",responseVector.toString());
                            int countVector=responseVector.size();
                            int sw=0;
                            int MAYOR_=0;
                            for(int i=0;i<countVector;i++){
                                SoapObject dataVector=(SoapObject)responseVector.get(i);
                                String idServicio=dataVector.getPropertyAsString("ID_SERVICIO").toString();
                                int mayor=Integer.parseInt(idServicio);

                                if(sw==0){
                                    MAYOR_=mayor;
                                    sw=1;
                                }else {
                                    if(MAYOR_<mayor){
                                        MAYOR_=mayor;
                                    }
                                }

                            }
                            for (int i=0;i<countVector;i++){
                                SoapObject dataVector=(SoapObject)responseVector.get(i);
                                if(String.valueOf(MAYOR_).equals(dataVector.getPropertyAsString("ID_SERVICIO"))){
                                    jsonStadoUltimoServicio.put("stadoServicio",dataVector.getPropertyAsString("ID_ESTADO_SERVICIO"));

                                }
                            }
                            Log.d("mayor_",String.valueOf(MAYOR_));


                        }catch (InterruptedIOException e){
                            tiempoEsperaConexion=true;
                             e.printStackTrace();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        return jsonStadoUltimoServicio;
                    }

                    @Override
                    protected void onPostExecute(JSONObject jsonUltimoServ) {
                        super.onPostExecute(jsonUltimoServ);
                        if(jsonUltimoServ!=null){
                            try {
                                if(jsonUltimoServ.getString("stadoServicio").equals("3")){
                                    sendNotification("Su taxi a llegado");
                                }else{
                                    Log.d("check_stado",jsonUltimoServ.getString("stadoServicio"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.execute();





            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0, 10000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TimerCronometro.cancel();
        Log.d("serviceAlarma","Finalizada");
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressWarnings("deprecation")
    private void sendNotification(String message) {

                                        String toSpeak = "tu taxi a llegado";
                                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                       /* Vibrator vv = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                        long [] patron1 = {0, 500, 300, 1000, 500};
                                        vv.vibrate(patron1,3);*/

        Intent intent = new Intent(this, ListaServicios.class);
        intent.putExtra("idAlarmaNotificacion","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        long [] patron = {5000, 2000, 3000, 1000, 5000};
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle("Alerta !!!")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(patron)
                .setLights(Color.RED,1,0)
                //.setOngoing(true)
                .setContentIntent(pendingIntent);
//builder.setLights(Color.RED, 1, 0);
        //builder.setOngoing(true);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
