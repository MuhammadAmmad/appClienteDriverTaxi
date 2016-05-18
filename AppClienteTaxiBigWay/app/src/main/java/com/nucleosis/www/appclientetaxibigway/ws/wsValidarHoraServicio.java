package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.admin.DeviceAdminInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.ListaServicios;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

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

/**
 * Created by carlos.lopez on 26/04/2016.
 */
public class wsValidarHoraServicio extends AsyncTask<String,String, String[]> {
    private Context context;
    private String fechaIngreso;
    private String HoraIngreso;
    private  SimpleDateFormat formatIngreso;
    private  SimpleDateFormat formatSalida;
    private AlertDialog alertDialog;
    private JSONObject jsonOrigen;
    private JSONObject jsonDestino;
    private PreferencesCliente preferencesCliente;
    private String idCliente;
    private String tarifa;
    private String solicitaAire;
    private String preciosAireSolicitado;
    private String tipoAutoSolicita;
    private MainActivity mainActivity=new MainActivity();
    private Fichero fichero;
    public wsValidarHoraServicio(Context context,
                                 String fechaIngreso,
                                 String horaIngreso,
                                 JSONObject jsonOrigen,
                                 JSONObject jsonDestino,
                                 String tarifa,
                                 String solicitaAire,
                                 String preciosAireSolicitado,
                                 String tipoAutoSolicita,
                                 AlertDialog alertDialog) {
        this.context = context;
        HoraIngreso = horaIngreso;
        this.fechaIngreso = fechaIngreso;
        this.alertDialog=alertDialog;
        this.jsonDestino = jsonDestino;
        this.jsonOrigen = jsonOrigen;
        this.tarifa=tarifa;
        this.preciosAireSolicitado=preciosAireSolicitado;
        this.tipoAutoSolicita=tipoAutoSolicita;
        this.solicitaAire=solicitaAire;
        formatIngreso = new SimpleDateFormat("yyyy-MM-dd");
        formatSalida = new SimpleDateFormat("dd-MM-yyyy");
        preferencesCliente=new PreferencesCliente(context);
        idCliente=preferencesCliente.OpenIdCliente();
        fichero=new Fichero(context);
        Log.d("idCliente-->",idCliente);
        Log.d("dataIngreso->", String.valueOf(fechaIngreso) + "-->" + horaIngreso);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected  String[] doInBackground(String... params) {
        String[] FechaHoraServer=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo5());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction5(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);

            SoapObject response= (SoapObject) envelope.bodyIn;
            SoapObject response2=(SoapObject)response.getProperty("return");
            //Log.d("responseHora", response2.toString());
            String fecha= response2.getPropertyAsString("FEC_ACTUAL").toString();
            FechaHoraServer[0]=fecha;
            String hora= response2.getPropertyAsString("DES_HORA").toString();
            FechaHoraServer[1]=hora;
            Log.d("fecha-hora", fecha + "---->" + hora);
        } catch (Exception e) {
            e.printStackTrace();
            //  Log.d("error", e.getMessage());
        }
        return FechaHoraServer;
    }

    @Override
    protected void onPostExecute(String[] dateServer) {
        super.onPostExecute(dateServer);
        Date fecha = null;
        Date fechaServer=null;
        Date fechaIngresada=null;
        try {
            fecha = formatIngreso.parse(dateServer[0]);
            Log.d("newFormat",String.valueOf(formatSalida.format(fecha)));
            fechaServer=formatSalida.parse(String.valueOf(formatSalida.format(fecha)));
            fechaIngresada=formatSalida.parse(fechaIngreso);
            int diferenciaDias=diferenciaDias(fechaIngresada,fechaServer);
            //dando formato salida a la fecha
            Date fechaDateUp= formatSalida.parse(fechaIngreso);
            String fechaUp=formatIngreso.format(fechaDateUp);
            Log.d("fechaUp-->",fechaUp.toString());
            if(diferenciaDias==0){
                Log.d("hora",dateServer[1]+"****>"+HoraIngreso);
                ///Hora ingresada
                String H1=HoraIngreso.substring(0,2);
                String M1=HoraIngreso.substring(3,5);
                int Tminutos_1=Integer.parseInt(H1)*60+Integer.parseInt(M1);
                //Hora servidor
                String H2=dateServer[1].substring(0, 2);
                String M2=dateServer[1].substring(3, 5);
                int Tminutos_2=Integer.parseInt(H2)*60+Integer.parseInt(M2);
                int DiferenciaMinutos=Tminutos_1-Tminutos_2;
                Log.d("diferenciaOK",String.valueOf(DiferenciaMinutos));
                if(DiferenciaMinutos>=-10){
                    Toast.makeText(context,"Solicitando servicio",Toast.LENGTH_LONG).show();
                    AlertaCreacionServicio(context,fechaUp,HoraIngreso);
                }else{
                    Toast.makeText(context,"Ingrese una hora valida/Revise la hora en su celular",Toast.LENGTH_LONG).show();
                }
            }else if(diferenciaDias>0 && diferenciaDias<=2){
                Toast.makeText(context,"Solicitando servicio",Toast.LENGTH_SHORT).show();
                AlertaCreacionServicio(context,fechaUp,HoraIngreso);
            }else{
                Toast.makeText(context,"Ingrese una fecha valida",Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException ex) {

            ex.printStackTrace();

        }

    }

    private int diferenciaDias(Date fechaMayor,Date fechaMenor){
        long diferenciaEn_ms = fechaMayor.getTime()- fechaMenor.getTime();
        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

    private void AlertaCreacionServicio(final Context context, final String fecha, final String hora){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Alerta !!");
        alertDialogBuilder.setMessage("Esta seguro de solicitar este servicio para esta fecha y hora: " + "\n\n" + fecha+" "+hora);
        alertDialogBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                alertDialog.dismiss();
                new AsyncTask<String, String, String[]>() {
                    @Override
                    protected String[] doInBackground(String... params) {
                        String[]dataSalida=new String[2];
                        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo10());
                        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = false;
                        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                                JSONObject jsonObject=fichero.ExtraerDireccionIncioFin();
                                JSONObject jsonCoordenadas=fichero.ExtraerCoordendaDirrecionIncio();
                        Log.d("coorLaLn",jsonCoordenadas.toString());
                        try {
                            request.addProperty("fecServicio", fecha);
                            request.addProperty("desHora", hora);
                            request.addProperty("impServicio", tarifa);
                            request.addProperty("desServicio", "");

                            request.addProperty("indAireAcondicionado", solicitaAire);//indAireAcondicionado=0 SI NO DESEA AIREACONDICIONADO
                                                                            //indAireAcondicionado =1  SI GUSTA AIRE ACONDICIONADO
                            request.addProperty("impAireAcondicionado", preciosAireSolicitado);
                            request.addProperty("impPeaje", "");
                            request.addProperty("usrRegistro", "");
                            request.addProperty("idCliente", idCliente);

                            request.addProperty("idTurno", 0);
                            request.addProperty("idConductor", 0);
                            request.addProperty("idAuto", 0);

                            request.addProperty("idAutoTipo", tipoAutoSolicita); //idAutoTipo=1  VIP     idAutoTipo=2 ECONOMICO



                            request.addProperty("idDistritoInicio", Integer.parseInt(jsonOrigen.getString("idDistrito").toString()));
                            request.addProperty("idZonaInicio", Integer.parseInt(jsonOrigen.getString("idZona").toString()));
                            request.addProperty("desDireccionInicio", jsonObject.getString("addresIncio").toString() );
                        //"/"+jsonCoordenadas.toString()
                            request.addProperty("idDistritoFinal", Integer.parseInt(jsonDestino.getString("idDistrito").toString()));
                            request.addProperty("idZonaFinal", Integer.parseInt(jsonDestino.getString("idZona").toString()));
                            request.addProperty("desDireccionFinal", jsonObject.getString("addresfin").toString());

                            request.addProperty("idAutoTipo", 0);
                            request.addProperty("idRegistroTipo", 1);// idRegistroTipo=1 SI EL REGISTRO SE HACE POR MOVIL
                                                                        //idRegistroTipo=2 SI EL REGISTRO SE HACE POR WEB
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("request-->",request.toString());

                        envelope.setOutputSoapObject(request);
                        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                        try {
                            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                            httpTransport.call(ConstantsWS.getSoapAction10(), envelope, headerPropertyArrayList);
                            SoapObject response1= (SoapObject) envelope.bodyIn;

                            SoapObject response2=(SoapObject)response1.getProperty("return");
                            Log.d("response2", response2.toString());
                            if(response2.hasProperty("IND_OPERACION")){
                                dataSalida[0]=response2.getPropertyAsString("IND_OPERACION").toString();
                            }else {
                                dataSalida[0]="";
                            }
                            if(response2.hasProperty("DES_MENSAJE")){
                                dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE").toString();
                            }else{
                                dataSalida[1]="";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        return dataSalida ;
                    }

                    @Override
                    protected void onPostExecute(String[] dataSalida) {
                        super.onPostExecute(dataSalida);
                        if(dataSalida[0].equals("1")){
                            Toast.makeText(context,dataSalida[1].toString(),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(context, ListaServicios.class);
                            context.startActivity(intent);
                            mainActivity.MAIN_ACTIVITY.finish();
                        }else if (dataSalida[0].equals("0")){
                            Toast.makeText(context,dataSalida[1].toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();

            }
        });
        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        alertDialogBuilder.show();
    }
}
