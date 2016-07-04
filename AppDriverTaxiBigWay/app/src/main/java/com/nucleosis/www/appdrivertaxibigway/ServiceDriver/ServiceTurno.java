package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.ws.wsDesactivarTurno;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by carlos.lopez on 04/05/2016.
 */
public class ServiceTurno extends Service {
    private TimerTask TimerCronometro;
    private PreferencesDriver preferencesDriver;
    private static int sw=0;
    private static int swLocation=0;
    @Override
    public void onCreate() {
        super.onCreate();
        preferencesDriver=new PreferencesDriver(ServiceTurno.this);
        Log.d("servicioCronometro", "CREADO");
        Log.d("idTaxista",preferencesDriver.OpenIdDriver());
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final Timer timerCola=new Timer();

        TimerCronometro=new TimerTask() {
            @Override
            public void run() {
                new AsyncTask<String, String, String[]>() {
                    @Override
                    protected String[] doInBackground(String... params) {
                        String[] dataTurno=new String[4];
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
                           Log.d("aaaTurnox", response2.toString());
                            if(response2.hasProperty("IND_ESTADO_TURNO")){
                                dataTurno[0]=response2.getPropertyAsString("IND_ESTADO_TURNO");
                                dataTurno[1]=response2.getPropertyAsString("ID_TURNO");
                                dataTurno[2]=response2.getPropertyAsString("FEC_ACTIVACION_YMD");
                                dataTurno[3]=response2.getPropertyAsString("DES_HORA_ACTIVACION");

                            }else {
                                dataTurno[0]="0";
                                dataTurno[1]="-1";
                                dataTurno[2]="";
                                dataTurno[3]="";
                            }

                            //  Log.d("response",response2.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            dataTurno[0]="0";
                            dataTurno[1]="-1";
                            dataTurno[2]="";
                            dataTurno[3]="";
                            //Log.d("error", e.printStackTrace());
                        }
                        return dataTurno;
                    }

                    @Override
                    protected void onPostExecute(String[] data) {
                        super.onPostExecute(data);
                        if(data!=null){
                            if(data[0].length()!=0){
                                preferencesDriver.InsertarIdTurno(data[1]);
                                preferencesDriver.InsertarDataTurno(data[1],data[2],data[3],data[0]);
                                JSONObject jsonTurno=preferencesDriver.ExtraerDataTurno();
                                JSONObject jsonFechaServer=preferencesDriver.ExtraerHoraSistema();
                                //  Log.d("idTurno...",preferencesDriver.ExtraerIdTurno());
                                VerificarAntiguedadDeTurno(jsonTurno,jsonFechaServer);
                                Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE)
                                        .putExtra(Utils.EXTRA_MEMORY,data[0]);
                                LocalBroadcastManager.
                                        getInstance(ServiceTurno.this).sendBroadcast(localIntent);

                                if(swLocation==0){
                                    swLocation=1;
                                }

                            }
                        }

                    }
                }.execute();
            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0,Utils.TIME_SERVICIO_TURNO);
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
        Log.d("servicioCronometro", "DESTRUIDO_X");
    }


    private String VerificarAntiguedadDeTurno(JSONObject jsonTurno, JSONObject jsonFechaServer){
        String ind="0";
        Log.d("JonsDATA",jsonTurno.toString()+"\n"+jsonFechaServer.toString());
        try {

            int diferenciaMinutos=   diferenciaHoras(jsonFechaServer.getString("horaServidor"),
                    jsonTurno.getString("HoraTurnoJson"));



            //TIEMPO MAXIMO QUE DURA EL SERVICIO  720 MINUTOS  12 HORAS
            if(diferenciaMinutos>720 && sw==0){
              //  Toast.makeText(ServiceTurno.this,"Su Turno esta a punto de desactivarse",Toast.LENGTH_SHORT).show();
                //new wsDesactivarTurno(ServiceTurno.this).execute();
                new AsyncTask<String, String, String[]>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        sw=1;
                    }

                    @Override
                    protected String[] doInBackground(String... params) {
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo6());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        String[] dataSalida=new String[2];
                        request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
                        request.addProperty("usrActualizacion","");
                        envelope.setOutputSoapObject(request);
                        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());


                        try {
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction6(), envelope, headerPropertyArrayList);
                            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                            SoapObject response1= (SoapObject) envelope.bodyIn;
                            SoapObject response2= (SoapObject)response1.getProperty("return");
                            Log.d("responseDesactivarTurno", response2.toString());
                            if(response2.hasProperty("IND_OPERACION")){
                                dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                                dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
                            }else{
                                dataSalida[0]="";
                                dataSalida[1]="";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //  Log.d("error---->",e.getMessage());
                            dataSalida[0]="";
                            dataSalida[1]="";

                        }
                        return dataSalida;
                    }

                    @Override
                    protected void onPostExecute(String[] data) {
                        super.onPostExecute(data);
                        sw=0;
                        if(data!=null){
                            if(data[0].equals("1")){
                                Toast.makeText(ServiceTurno.this,"Su turno fue desactivado !! "+"\n"
                                        +"exedio el tiempo permitido",Toast.LENGTH_LONG).show();

                                TimerCronometro.cancel();
                            }else if(data[0].equals("3")) {

                                Log.d("obsTurno",data[1]);
                            }
                        }


                    }
                }.execute();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }
        return ind;
    }

    private int diferenciaDias(String fechaMayor_server, String fechaMenor_servicio){
        SimpleDateFormat formatIngreso = new SimpleDateFormat("yyyy-MM-dd");
        long dias = 0;
        try {
           Date fechaServer=formatIngreso.parse(fechaMayor_server);
         //   Date fechaServer=formatIngreso.parse("2016-05-11");
            Date fechaServicio=formatIngreso.parse(fechaMenor_servicio);
            long diferenciaEn_ms = fechaServer.getTime()- fechaServicio.getTime();
            dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Log.d("diferenciaDiasJson",String.valueOf(dias));
        return (int) dias;
    }
    private int diferenciaHoras(String  HoraServer, String HoraTurno ){
        int minutos=0;
        if(HoraServer.length()==5 && HoraTurno.length()==5){
            /* HoraServer="18:01";//135 //HORA ACTUAL 22:50
             HoraTurno="06:00";//1130 // HORA DEL TURNO*/
            int minutosFormales=12*60;

            int H1=Integer.parseInt(HoraServer.substring(0,2));
            int M1=Integer.parseInt(HoraServer.substring(3,5));
            int TotalMinutos1=H1*60+M1;

            int H2=Integer.parseInt(HoraTurno.substring(0,2));
            int M2=Integer.parseInt(HoraTurno.substring(3,5));

            int TotalMinutos2=H2*60+M2;

            if(H1<H2 ){
                int diaMinutos=24*60;
                int RestaParcialMinutos=diaMinutos-TotalMinutos2;
                Log.d("horaTermino",String.valueOf(RestaParcialMinutos));
                int SumaTotal=RestaParcialMinutos+TotalMinutos1;
                Log.d("TotalMintuos",String.valueOf(SumaTotal));

                minutos=SumaTotal;
            }else if(H1>=H2){
                minutos=TotalMinutos1-TotalMinutos2;
                Log.d("TotalMintuos",String.valueOf(minutos));
            }
        }


    //    int minutos=TotalMinutos1-TotalMinutos2;
        return  minutos;
    }
}
