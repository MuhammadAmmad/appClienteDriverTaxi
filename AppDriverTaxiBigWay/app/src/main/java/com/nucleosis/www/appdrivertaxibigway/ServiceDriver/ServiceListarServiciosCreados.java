package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcelable;
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
import java.util.LinkedList;
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
                new AsyncTask<String, String, List<beansListaServiciosCreadosPorFecha>>() {
                     List<beansListaServiciosCreadosPorFecha> ListServicios;
                    SimpleDateFormat formatIngreso;
                    Date fechaServer=null;
                    Date fechaServicio=null;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        ListServicios=new ArrayList<beansListaServiciosCreadosPorFecha>();
                        formatIngreso = new SimpleDateFormat("yyyy-MM-dd");
                    }

                    @Override
                    protected List<beansListaServiciosCreadosPorFecha> doInBackground(String... params) {
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
                        beansListaServiciosCreadosPorFecha row=null;
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
                                row=new beansListaServiciosCreadosPorFecha();

                                String fecha1= dataVector.getPropertyAsString("FEC_ACTUAL").toString();
                                String fecha2=dataVector.getPropertyAsString("FEC_SERVICIO_YMD").toString();

                                String horaServer= dataVector.getPropertyAsString("DES_HORA_ACTUAL").toString();
                                String horaServicio=dataVector.getProperty("DES_HORA").toString();

                                fechaServer=formatIngreso.parse(fecha1);
                                fechaServicio=formatIngreso.parse(fecha2);
                                int diferenciaDias=diferenciaDias(fechaServicio,fechaServer);
                                int diferenciaHoras=diferenciaHoras(horaServer,horaServicio);
                                Log.d("diferenciaDisaHoras",String.valueOf(diferenciaDias)+"**"+String.valueOf(diferenciaHoras));

                                if(diferenciaDias==0 && diferenciaHoras<=60 && diferenciaHoras>=-20){
                                    Log.d("TiempoDifer",String.valueOf(diferenciaDias)+"**"+String.valueOf(diferenciaHoras));

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
                                Log.d("siseU",String.valueOf(ListServicios.size()));


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return ListServicios;
                    }

                    @Override
                    protected void onPostExecute(List<beansListaServiciosCreadosPorFecha> listServiceiosCreados) {
                       // Log.d("siseListReturn",String.valueOf(listServiceiosCreados.size()+"  "+listServiceiosCreados.get(0).getDireccionIncio()));
                        Gson gson = new Gson();
                        String json = gson.toJson(listServiceiosCreados);
                        String ObjetoJsonArray=new Gson().toJson(listServiceiosCreados);
                        Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE_2)
                                .putExtra(Utils.EXTRA_MEMORY_2, json);
                        LocalBroadcastManager.
                                getInstance(ServiceListarServiciosCreados.this).sendBroadcast(localIntent);

                        super.onPostExecute(listServiceiosCreados);

                    }
                }.execute();
            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0,5000);
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
class  beansListaServiciosCreadosPorFecha{
    private String idServicio; //ID_SERVICIO
    private String importeServicio;  //IMP_SERVICIO
    private String DescripcionServicion;  //DES_SERVICIO

    private String importeAireAcondicionado;  //IMP_AIRE_ACONDICIONADO
    private String importePeaje;                //IMP_PEAJE
    private String numeroMinutoTiempoEspera;    //NUM_MINUTO_TIEMPO_ESPERA

    private String importeTiempoEspera;   //IMP_TIEMPO_ESPERA
    private String nameDistritoInicio;      //NOM_DISTRITO_INICIO
    private String nameDistritoFin;         //NOM_DISTRITO_FIN

    private String DireccionIncio;          //DES_DIRECCION_INICIO
    private String direccionFinal;            //
    private String nombreConductor;         //

    private String nombreStadoServicio;
    private String statadoServicio;
    private String Fecha;   //FEC_SERVICIO

    private String FechaFormat; //FEC_SERVICIO_YMD
    private String Hora;       //DES_HORA
    private String infoAddress;

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(String infoAddress) {
        this.infoAddress = infoAddress;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFechaFormat() {
        return FechaFormat;
    }

    public void setFechaFormat(String fechaFormat) {
        FechaFormat = fechaFormat;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getStatadoServicio() {
        return statadoServicio;
    }

    public void setStatadoServicio(String statadoServicio) {
        this.statadoServicio = statadoServicio;
    }

    public String getNombreStadoServicio() {
        return nombreStadoServicio;
    }

    public void setNombreStadoServicio(String nombreStadoServicio) {
        this.nombreStadoServicio = nombreStadoServicio;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getDireccionFinal() {
        return direccionFinal;
    }

    public void setDireccionFinal(String direccionFinal) {
        this.direccionFinal = direccionFinal;
    }

    public String getDireccionIncio() {
        return DireccionIncio;
    }

    public void setDireccionIncio(String direccionIncio) {
        DireccionIncio = direccionIncio;
    }

    public String getNameDistritoFin() {
        return nameDistritoFin;
    }

    public void setNameDistritoFin(String nameDistritoFin) {
        this.nameDistritoFin = nameDistritoFin;
    }

    public String getNameDistritoInicio() {
        return nameDistritoInicio;
    }

    public void setNameDistritoInicio(String nameDistritoInicio) {
        this.nameDistritoInicio = nameDistritoInicio;
    }

    public String getImporteTiempoEspera() {
        return importeTiempoEspera;
    }

    public void setImporteTiempoEspera(String importeTiempoEspera) {
        this.importeTiempoEspera = importeTiempoEspera;
    }

    public String getNumeroMinutoTiempoEspera() {
        return numeroMinutoTiempoEspera;
    }

    public void setNumeroMinutoTiempoEspera(String numeroMinutoTiempoEspera) {
        this.numeroMinutoTiempoEspera = numeroMinutoTiempoEspera;
    }

    public String getImportePeaje() {
        return importePeaje;
    }

    public void setImportePeaje(String importePeaje) {
        this.importePeaje = importePeaje;
    }

    public String getImporteAireAcondicionado() {
        return importeAireAcondicionado;
    }

    public void setImporteAireAcondicionado(String importeAireAcondicionado) {
        this.importeAireAcondicionado = importeAireAcondicionado;
    }

    public String getDescripcionServicion() {
        return DescripcionServicion;
    }

    public void setDescripcionServicion(String descripcionServicion) {
        DescripcionServicion = descripcionServicion;
    }

    public String getImporteServicio() {
        return importeServicio;
    }

    public void setImporteServicio(String importeServicio) {
        this.importeServicio = importeServicio;
    }
}