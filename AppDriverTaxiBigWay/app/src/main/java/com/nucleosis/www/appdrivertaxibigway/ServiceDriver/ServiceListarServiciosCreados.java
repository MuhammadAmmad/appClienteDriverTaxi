package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansListaServiciosCreadosPorFecha;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.Sqlite.SqlGestion;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerHoraServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InterruptedIOException;
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
import com.nucleosis.www.appdrivertaxibigway.R;
/**
 * Created by karlos on 04/05/2016.
 */
public class ServiceListarServiciosCreados extends Service {
    private TimerTask TimerCronometro;
    private PreferencesDriver preferencesDriver;
    private Fichero fichero;
    private SqlGestion sqlGestion;
    @Override
    public void onCreate() {
        super.onCreate();
        preferencesDriver=new PreferencesDriver(ServiceListarServiciosCreados.this);
        fichero=new Fichero(ServiceListarServiciosCreados.this);
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
                    List<beansListaServiciosCreadosPorFecha> ListServiciosAsignadoConductor;
                    SimpleDateFormat formatIngreso;
                    Date fechaServer=null;
                    Date fechaServicio=null;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        ListServicios=new ArrayList<beansListaServiciosCreadosPorFecha>();
                        ListServiciosAsignadoConductor=new ArrayList<beansListaServiciosCreadosPorFecha>();
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
                        beansListaServiciosCreadosPorFecha row2=null;
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                        request.addProperty("idCliente", 0);
                        request.addProperty("fecServicio", FechaSend);
                        request.addProperty("idConductor", 0);
                        request.addProperty("idEstadoServicio", 0);
                        request.addProperty("idAutoTipo", 0);
                        envelope.setOutputSoapObject(request);


                        try {
                            HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(),Utils.TIME_OUT);
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
                                row2=new beansListaServiciosCreadosPorFecha();
                                String stadoServicio=dataVector.getPropertyAsString("ID_ESTADO_SERVICIO");
                                String idConductor=dataVector.getPropertyAsString("ID_CONDUCTOR");

                                String fecha1= dataVector.getPropertyAsString("FEC_ACTUAL").toString();
                                String fecha2=dataVector.getPropertyAsString("FEC_SERVICIO_YMD").toString();
                                String horaServer= dataVector.getPropertyAsString("DES_HORA_ACTUAL").toString();
                                String horaServicio=dataVector.getProperty("DES_HORA").toString();
                                //ACTUALIZA LA HORA EN FORMATO JSON
                                JSONObject jsonObject =new JSONObject();
                                jsonObject.put("Fecha",fecha1);
                                jsonObject.put("Hora",horaServer);
                                InsertHoraFechaServidor(jsonObject);

                                fechaServer=formatIngreso.parse(fecha1);
                                fechaServicio=formatIngreso.parse(fecha2);

                                boolean swRadioServicio=true;

                                if(dataVector.getProperty("DES_LATITUD_SERVICIO")!=null &&
                                        dataVector.getProperty("DES_LONGITUD_SERVICIO")!=null  ){
                                    JSONObject jsonConfiguraciones=fichero.ExtraerConfiguraciones();

                                    String latitudServicio=dataVector.getPropertyAsString("DES_LATITUD_SERVICIO");
                                    String longitudservicio=dataVector.getPropertyAsString("DES_LONGITUD_SERVICIO");

                                    if(jsonConfiguraciones!=null){
                                        //radioServicio
                                        if(jsonConfiguraciones.has("radioServicio")){
                                            String radioServicio=jsonConfiguraciones.getString("radioServicio");
                                            if (radioServicio.length()!=0){

                                                //computeDistanceBetween()
                                                JSONObject jsonCoordenadaDriver=fichero.ExtraerCoordendaConductor();

                                                if(jsonCoordenadaDriver!=null){
                                                    Log.d("coorde_X",jsonCoordenadaDriver.toString());
                                                    Log.d("coordena_servicio",latitudServicio+"-->"+longitudservicio);
                                                    Log.d("radioVector",radioServicio);

                                                        String latitudConductor=jsonCoordenadaDriver.getString("latitud");
                                                        String longitudConductor=jsonCoordenadaDriver.getString("longitud");
                                                        if(latitudServicio.length()!=0 && longitudservicio.length()!=0){
                                                            LatLng latLngServicio=new LatLng(Double.parseDouble(latitudServicio),Double.parseDouble(longitudservicio));
                                                            LatLng latLngConductor=new LatLng(Double.parseDouble(latitudConductor),Double.parseDouble(longitudConductor));

                                                            double distancia= SphericalUtil.computeDistanceBetween(latLngServicio,latLngConductor);
                                                            String idServis=dataVector.getPropertyAsString("ID_SERVICIO");
                                                            Log.d("distancia_","idServicio"+"="+idServis+", "+"radio"+"-->"+radioServicio+"--->"+"extrack"+"-->"+String.valueOf(distancia));

                                                            if(Double.parseDouble(radioServicio)>=distancia){
                                                                Log.d("EstaEnRango","Si");
                                                                swRadioServicio=true;
                                                            }else{
                                                                Log.d("EstaEnRango","No");
                                                                swRadioServicio=false;
                                                            }
                                                        }else{
                                                            Log.d("EstaEnRango","Si*");
                                                            swRadioServicio=true;
                                                        }
                                                }

                                            }
                                        }
                                    }
                                }else {
                                    Log.d("coordenada_Z","null");
                                    swRadioServicio=true;
                                }

                                if(stadoServicio.equals("1") && swRadioServicio==true){

                                    int diferenciaDias=diferenciaDias(fechaServicio,fechaServer);
                                    int diferenciaHoras=diferenciaHoras(horaServer,horaServicio);
                                    Log.d("diferenciaDisaHoras",String.valueOf(diferenciaDias)+"**"
                                            +String.valueOf(diferenciaHoras));
                                    if(diferenciaDias==0 && diferenciaHoras<=60 && diferenciaHoras>=-20){
                                        Log.d("TiempoDifer",String.valueOf(diferenciaDias)+"**"+String.valueOf(diferenciaHoras));
                                        row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                                        row.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                                        row.setFechaFormat(dataVector.getProperty("FEC_SERVICIO_YMD").toString());

                                        row.setHora(dataVector.getProperty("DES_HORA").toString());
                                        row.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                                        row.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());
                                        //IND_AIRE_ACONDICIONADO
                                        row.setIndAireAcondicionado(dataVector.getProperty("IND_AIRE_ACONDICIONADO").toString());
                                        row.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                                        row.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
                                        row.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());

                                        row.setImporteGastosAdicionales(dataVector.getProperty("IMP_GASTOS_ADICIONALES").toString());
                                        //ID PAR SABER SI EL REBGISTRO DEL SERVICIO SE HACE POR MOVIL O POR WEB

                                        row.setIdTipoRegistro(dataVector.getProperty("ID_REGISTRO_TIPO").toString());

                                        row.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                                        row.setNameDistritoInicio(dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                                        row.setNameZonaIncio(dataVector.getProperty("NOM_ZONA_INICIO").toString());
                                        row.setNameDistritoFin(dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                                        row.setNameZonaFin(dataVector.getProperty("NOM_ZONA_FIN").toString());
                                        row.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                                        row.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                                        row.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());

                                        row.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                                        row.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                                        row.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                                                + "\n" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());

                                        row.setIdCliente(dataVector.getProperty("ID_CLIENTE").toString());
                                        row.setIdConductor(dataVector.getProperty("ID_CONDUCTOR").toString());
                                        row.setIdTipoAuto(dataVector.getProperty("ID_AUTO_TIPO").toString());
                                        //VERIFICAMOS SI EL CLIENTE QUIERE MOSTRAR SU CELULAR
                                        row.setIndMostrarCelularCliente(dataVector.getProperty("IND_MOSTRAR_TELEFONO").toString());

                                        row.setNameCliente(dataVector.getProperty("NOM_APE_CLIENTE").toString());
                                        row.setTipoPago(dataVector.getProperty("NOM_TIPO_PAGO_SERVICIO").toString());

                                        //iddistroto y idZonas
                                        row.setIdDistritoIncio(dataVector.getProperty("ID_DISTRITO_INICIO").toString());
                                        row.setIdDistritoFin(dataVector.getProperty("ID_DISTRITO_FINAL").toString());
                                        row.setIdZonaInicio(dataVector.getProperty("ID_ZONA_INICIO").toString());
                                        row.setIdZonaFin(dataVector.getProperty("ID_ZONA_FINAL").toString());
                                        String indMostrarCelular=dataVector.getProperty("IND_MOSTRAR_TELEFONO").toString();

                                        if(indMostrarCelular!=null){
                                            Log.d("idMostrarCliente_1",indMostrarCelular);
                                            if(indMostrarCelular.equals("0")){
                                                row.setNucCelularCliente("");//NO QUIERE MOSTRAR CELULAR
                                            }else if(indMostrarCelular.equals("1")) {
                                                                      //SI QUIERE MOSTRAR CELULAR
                                                row.setNucCelularCliente(dataVector.getProperty("NUM_CELULAR").toString());
                                            }else {
                                                row.setNucCelularCliente("");//NO QUIERE MOSTRAR CELULAR
                                            }
                                        }
                                        row.setDesAutoTipo(dataVector.getProperty("DES_AUTO_TIPO").toString());
                                        row.setNumeroMovilTaxi(dataVector.getProperty("NUM_MOVIL").toString());
                                        row.setDesAutoTipoPidioCliente(dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString());
                                        row.setIdAutoTipoPidioCliente(dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString());
                                        ///TIPO DE PAGO DE SERVICIO  CONTADO (1) CREDITO (2)
                                        row.setIdTipoPagoServicio(dataVector.getProperty("ID_TIPO_PAGO_SERVICIO").toString());

                                        if(dataVector.getProperty("DES_LATITUD_SERVICIO")!=null &&
                                                dataVector.getProperty("DES_LONGITUD_SERVICIO")!=null  ){
                                            row.setLatitudServicio(dataVector.getProperty("DES_LATITUD_SERVICIO").toString());
                                            row.setLongitudServicio(dataVector.getProperty("DES_LONGITUD_SERVICIO").toString());
                                        }else {
                                            row.setLatitudServicio("");
                                            row.setLongitudServicio("");
                                        }
                                        ListServicios.add(row);

                                    }
                                   String idServicio=dataVector.getPropertyAsString("ID_SERVICIO").toString();
                                        sqlGestion=new SqlGestion(ServiceListarServiciosCreados.this);
                                        String dataServicio[]=sqlGestion.BuscarIdServicio(idServicio);
                                        Log.d("sizeDta_", "-->"+String.valueOf(dataServicio.length));
                                        if(dataServicio[0].length()==0 && dataServicio[1].length()==0){
                                            String msn=getResources().getString(R.string.serviciosListos);
                                            sendNotification(msn);
                                            sqlGestion=new SqlGestion(ServiceListarServiciosCreados.this);
                                            sqlGestion.InsertarIdServicioStado(idServicio,"1");
                                        }else {
                                            Log.d("dataServisss",dataServicio[0]+"-->"+dataServicio[1]);
                                        }
                                           /* sqlGestion.InsertarIdServicioStado(idServicio,"1");
                                            sendNotification("Te an asignado un servicio!!!!");*/



                                }else if(idConductor.equals(preferencesDriver.OpenIdDriver())){
                                    row2.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                                    row2.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                                    row2.setFechaFormat(dataVector.getProperty("FEC_SERVICIO_YMD").toString());

                                    row2.setHora(dataVector.getProperty("DES_HORA").toString());
                                    row2.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                                    row2.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());

                                    row2.setIndAireAcondicionado(dataVector.getProperty("IND_AIRE_ACONDICIONADO").toString());
                                    row2.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                                    row2.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
                                    row2.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());

                                    row2.setImporteGastosAdicionales(dataVector.getProperty("IMP_GASTOS_ADICIONALES").toString());
                                    //ID PAR SABER SI EL REBGISTRO DEL SERVICIO SE HACE POR MOVIL O POR WEB

                                    row2.setIdTipoRegistro(dataVector.getProperty("ID_REGISTRO_TIPO").toString());

                                    row2.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                                    row2.setNameDistritoInicio(dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                                    row2.setNameZonaIncio(dataVector.getProperty("NOM_ZONA_INICIO").toString());
                                    row2.setNameDistritoFin(dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                                    row2.setNameZonaFin(dataVector.getProperty("NOM_ZONA_FIN").toString());
                                    row2.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                                    row2.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                                    row2.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());

                                    row2.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                                    row2.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                                    row2.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                                            + "\n" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());

                                    row2.setIdCliente(dataVector.getProperty("ID_CLIENTE").toString());
                                    row2.setIdConductor(dataVector.getProperty("ID_CONDUCTOR").toString());
                                    row2.setIdTipoAuto(dataVector.getProperty("ID_AUTO_TIPO").toString());

                                    row2.setNameCliente(dataVector.getProperty("NOM_APE_CLIENTE").toString());
                                    row2.setTipoPago(dataVector.getProperty("NOM_TIPO_PAGO_SERVICIO").toString());
                                    //iddistroto y idZonas
                                    row2.setIdDistritoIncio(dataVector.getProperty("ID_DISTRITO_INICIO").toString());
                                    row2.setIdDistritoFin(dataVector.getProperty("ID_DISTRITO_FINAL").toString());
                                    row2.setIdZonaInicio(dataVector.getProperty("ID_ZONA_INICIO").toString());
                                    row2.setIdZonaFin(dataVector.getProperty("ID_ZONA_FINAL").toString());

                                    //VERIFICAMOS SI EL CLIENTE QUIERE MOSTRAR SU CELULAR
                                    row2.setIndMostrarCelularCliente(dataVector.getProperty("IND_MOSTRAR_TELEFONO").toString());
                                    String indMostrarCelular=dataVector.getProperty("IND_MOSTRAR_TELEFONO").toString();

                                    if(indMostrarCelular!=null){
                                        Log.d("idMostrarCliente_2",indMostrarCelular);
                                        if(indMostrarCelular.equals("0")){
                                            row2.setNucCelularCliente("");//NO QUIERE MOSTRAR CELULAR
                                        }else if(indMostrarCelular.equals("1")) {
                                            //SI QUIERE MOSTRAR CELULAR
                                            row2.setNucCelularCliente(dataVector.getProperty("NUM_CELULAR").toString());
                                        }else {
                                            row2.setNucCelularCliente("");//NO QUIERE MOSTRAR CELULAR
                                        }
                                    }
                                    row2.setDesAutoTipo(dataVector.getProperty("DES_AUTO_TIPO").toString());
                                    row2.setDesAutoTipoPidioCliente(dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString());
                                    row2.setIdAutoTipoPidioCliente(dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString());
                                    row2.setNumeroMovilTaxi(dataVector.getProperty("NUM_MOVIL").toString());

                                    ///TIPO DE PAGO DE SERVICIO  CONTADO (1) CREDITO (2)
                                    row2.setIdTipoPagoServicio(dataVector.getProperty("ID_TIPO_PAGO_SERVICIO").toString());

                                    if(dataVector.getProperty("DES_LATITUD_SERVICIO")!=null &&
                                            dataVector.getProperty("DES_LONGITUD_SERVICIO")!=null ){
                                        row2.setLatitudServicio(dataVector.getProperty("DES_LATITUD_SERVICIO").toString());
                                        row2.setLongitudServicio(dataVector.getProperty("DES_LONGITUD_SERVICIO").toString());
                                    }else {
                                        row2.setLatitudServicio("");
                                        row2.setLongitudServicio("");
                                    }

                                    ListServiciosAsignadoConductor.add(row2);

                                    String idTipoRegistro=dataVector.getProperty("ID_REGISTRO_TIPO").toString();
                                    String idStadoServicio=dataVector.getProperty("ID_ESTADO_SERVICIO").toString();
                                    String idServicio=  dataVector.getProperty("ID_SERVICIO").toString();

                                        if(idTipoRegistro.equals("2") && idStadoServicio.equals("2")){
                                              sqlGestion=new SqlGestion(ServiceListarServiciosCreados.this);
                                              String dataServicio[]=sqlGestion.BuscarIdServicio(idServicio);
                                              Log.d("sizeDta_", "-->"+String.valueOf(dataServicio.length));
                                            if(dataServicio[0].length()==0 && dataServicio[1].length()==0){
                                             String msn=   getResources().getString(R.string.servicioAsignado);
                                                sendNotification(msn);
                                                sqlGestion=new SqlGestion(ServiceListarServiciosCreados.this);
                                                sqlGestion.InsertarIdServicioStado(idServicio,"1");
                                            }else {
                                                Log.d("dataServisss",dataServicio[0]+"-->"+dataServicio[1]);
                                            }
                                           /* sqlGestion.InsertarIdServicioStado(idServicio,"1");
                                            sendNotification("Te an asignado un servicio!!!!");*/

                                        }else{
                                            Log.d("tipoRegistro","!!!!!!");
                                        }

                                }

                                Log.d("siseU",String.valueOf(ListServiciosAsignadoConductor.size()));


                            }
                            String ObjetoJsonArray=new Gson().toJson(ListServiciosAsignadoConductor);
                            fichero.InsertarListaServiciosTomadosConductor(ObjetoJsonArray);
                        }catch (InterruptedIOException i){
                            i.printStackTrace();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        return ListServicios;
                    }

                    @Override
                    protected void onPostExecute(List<beansListaServiciosCreadosPorFecha> listServiceiosCreados) {
                       // Log.d("siseListReturn",String.valueOf(listServiceiosCreados.size()+"  "+listServiceiosCreados.get(0).getDireccionIncio()));

                        if(listServiceiosCreados.size()==0){
                            new wsExtraerHoraServer(ServiceListarServiciosCreados.this).execute();
                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(listServiceiosCreados);
                        Log.d("jsonU",json.toString());
                        String jsonListServicioConductor=gson.toJson(ListServiciosAsignadoConductor);
                        Log.d("jsonXXXX",jsonListServicioConductor.toString());
                        Intent localIntent = new Intent(Utils.ACTION_RUN_SERVICE_2)
                                .putExtra(Utils.EXTRA_MEMORY_2, json);
                        LocalBroadcastManager.
                                getInstance(ServiceListarServiciosCreados.this).sendBroadcast(localIntent);

                        Intent SendIntent=new Intent(Utils.ACTION_RUN_SERVICE_3)
                                .putExtra(Utils.EXTRA_MEMORY_3,jsonListServicioConductor);
                        LocalBroadcastManager.getInstance(ServiceListarServiciosCreados.this).sendBroadcast(SendIntent);


                        super.onPostExecute(listServiceiosCreados);

                    }
                }.execute();
            }
        };
        timerCola.scheduleAtFixedRate(TimerCronometro, 0,Utils.TIME_LISTAR_SERVICIOS);
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
    private int diferenciaDias(Date fechaMayor,Date fechaMenor){
        long diferenciaEn_ms = fechaMayor.getTime()- fechaMenor.getTime();
        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }
    private int diferenciaHoras(String HoraServicio ,String  HoraServer){
        Log.d("HServHserver",HoraServicio+"**"+HoraServer);
        String H1=HoraServer.substring(0,2);
        String M1=HoraServer.substring(3,5);
    int TotalMinutos1=Integer.parseInt(H1)*60+Integer.parseInt(M1);

        String H2=HoraServicio.substring(0,2);
        String M2=HoraServicio.substring(3,5);

    int TotalMinutos2=Integer.parseInt(H2)*60+Integer.parseInt(M2);
        int minutos=TotalMinutos1-TotalMinutos2;
        return  minutos;
    }

    private void InsertHoraFechaServidor(JSONObject json){
        try {
            preferencesDriver.InsertarFechaHoraActual(
                    json.getString("Fecha").toString(),
                    json.getString("Hora").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void sendNotification(String message) {
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idAlarmaNotificacion","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  *//*Request code*//* , intent,
                PendingIntent.FLAG_ONE_SHOT);*/
        long [] patron = {5000, 2000, 3000, 1000, 5000};
       // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Uri customSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + this.getPackageName() + "/raw/tonno");
      /*  Notification notification = builder.build();
        notification.sound = Uri.parse("android.resource://"
                + context.getPackageName() + "/" + R.raw.siren);*/
        String msnTitulo=getResources().getString(R.string.alerta);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle(msnTitulo)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(customSound)
                .setVibrate(patron)
                .setLights(Color.RED,1,0);
                //.setOngoing(true)
               // .setContentIntent(pendingIntent);
//builder.setLights(Color.RED, 1, 0);
        //builder.setOngoing(true);
        Notification mNotification = notificationBuilder.build();

        mNotification.flags |= Notification.FLAG_INSISTENT;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1 /* ID of notification */, mNotification);
      //  notificationManager.cancelAll();
    }
}
