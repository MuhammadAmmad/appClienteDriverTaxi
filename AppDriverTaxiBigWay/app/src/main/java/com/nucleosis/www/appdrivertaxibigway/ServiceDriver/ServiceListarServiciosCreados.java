package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
                    SimpleDateFormat formatIngreso;
                    Date fechaServer=null;
                    Date fechaServicio=null;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        ListServicios=new ArrayList<beansHistorialServiciosCreados>();
                        formatIngreso = new SimpleDateFormat("yyyy-MM-dd");
                    }

                    @Override
                    protected List<beansHistorialServiciosCreados> doInBackground(String... params) {
                        Calendar c = new GregorianCalendar();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);
                        String FechaSend="";
                        if(mDay>=10  && (mMonth+1)>=10){
                             FechaSend=String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay);
                        }else if(mDay>=10 && (mMonth+1)<10){
                             FechaSend=String.valueOf(mYear)+"-0"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay);
                        }else if(mDay<10 && (mMonth+1)>=10){
                             FechaSend=String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-0"+String.valueOf(mDay);
                        }else if (mDay<10 && (mMonth+1)<10){
                             FechaSend=String.valueOf(mYear)+"-0"+String.valueOf(mMonth+1)+"-0"+String.valueOf(mDay);
                        }
                        Log.d("fechaend",FechaSend);
                        beansHistorialServiciosCreados row=null;
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idCliente", 0);
                        request.addProperty("fecServicio", FechaSend);
                        request.addProperty("idConductor", 0);
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
                            Log.d("respmseVecptr",responseVector.toString());
                            for(int i=0;i<countVector;i++){
                                SoapObject dataVector=(SoapObject)responseVector.get(i);
                                row=new beansHistorialServiciosCreados();

                                String fecha1= dataVector.getPropertyAsString("FEC_ACTUAL_SERVER").toString();
                                String fecha2=dataVector.getPropertyAsString("FEC_SERVICIO_YMD").toString();

                                String horaServer= dataVector.getPropertyAsString("DES_HORA_SERVER").toString();
                                String horaServicio=dataVector.getProperty("DES_HORA").toString();

                                fechaServer=formatIngreso.parse(fecha1);
                                fechaServicio=formatIngreso.parse(fecha2);
                                int diferenciaDias=diferenciaDias(fechaServicio,fechaServer);
                                int diferenciaHoras=diferenciaHoras(horaServer,horaServicio);
                                Log.d("diferenciaDisaHoras",String.valueOf(diferenciaDias)+"**"+String.valueOf(diferenciaHoras));
                                if(diferenciaDias==0 && diferenciaHoras<=60 && diferenciaHoras>=-20){
                                    row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                                    row.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                                    row.setFechaFormat(dataVector.getProperty("FEC_SERVICIO_YMD").toString());
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
                                    row.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                                    row.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                                    row.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                                    row.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                                            + "\n" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                                    //  row.setImageHistorico(drawable);
                                    ListServicios.add(row);
                                }


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
                        String ObjetoJsonArray=new Gson().toJson(listServiceiosCreados);
                       Log.d("jsonArray", ObjetoJsonArray.toString());
                        Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE_2)
                                .putExtra(Utils.EXTRA_MEMORY_2, ObjetoJsonArray);
                        LocalBroadcastManager.
                                getInstance(ServiceListarServiciosCreados.this).sendBroadcast(localIntent);






                    }
                }.execute();
            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0,30000);
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

    private int diferenciaDias(Date fechaMayor,Date fechaMenor){
        long diferenciaEn_ms = fechaMayor.getTime()- fechaMenor.getTime();
        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }
    private int diferenciaHoras(String HoraServicio ,String  HoraServer){
        String H1=HoraServer.substring(0,2);
        String M1=HoraServer.substring(3,5);
    int TotalMinutos1=Integer.parseInt(H1)*60+Integer.parseInt(M1);

        String H2=HoraServicio.substring(0,2);
        String M2=HoraServicio.substring(3,5);
    int TotalMinutos2=Integer.parseInt(H2)*60+Integer.parseInt(M2);

        int minutos=TotalMinutos1-TotalMinutos2;
        return  minutos;
    }
}
