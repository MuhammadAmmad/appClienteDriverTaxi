package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by karlos on 14/05/2016.
 */
public class wsActualizarStadosAdicionales extends AsyncTask<String,String,String[]> {
    private Activity activity;
    private String msnMotivo;
    private int idObjectos;
    private componentesR compR;
    private String idServicio;
    private PreferencesDriver preferencesDriver;
    private ProgressDialog progressDialog;
    private  Context context;
    private String idDriver;
    public wsActualizarStadosAdicionales(Activity activity,String idServicio, String msnMotivo, int idObjectos) {
        this.activity = activity;
        this.msnMotivo = msnMotivo;
        this.idObjectos = idObjectos;
        this.idServicio=idServicio;
        context=(Context)activity;
        compR=new componentesR(context);
        compR.Controls_Maps_conducotor_cliente(activity);
        preferencesDriver=new PreferencesDriver(context);
        Log.d("idServicioRun",idServicio);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        idDriver=preferencesDriver.OpenIdDriver();
    }
    @Override
    protected  String[] doInBackground(String... params) {

        Log.d("eta_aqui","doInBackGround");
        String[] data=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;

        request.addProperty("idServicio", Integer.parseInt(idServicio));
        request.addProperty("idTurno",Integer.parseInt(preferencesDriver.ExtraerIdTurno()));
        request.addProperty("idConductor",Integer.parseInt(idDriver));
        request.addProperty("idAuto",Integer.parseInt(preferencesDriver.ExtraerIdVehiculo()));
        request.addProperty("idEstadoServicio",Integer.parseInt(params[0].toString()));
        request.addProperty("desMotivo", msnMotivo);
        request.addProperty("usrActualizacion", 0);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("respOk",response2.toString());
            if(response2.hasProperty("IND_OPERACION")){
                data[0]=response2.getProperty("IND_OPERACION").toString();
                data[1]=response2.getProperty("DES_MENSAJE").toString();

            }else{
                data[0]="";
                data[1]="";
            }

            // Log.d("actualizarServicio", response2.toString());


            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
            data[0]="";
            data[1]="";
            //Log.d("error", e.printStackTrace());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        progressDialog.dismiss();
        if(data!=null){
            if(data[0].equals("1")){
                Toast.makeText(activity,data[1],Toast.LENGTH_SHORT).show();
                switch (idObjectos){
                    case 9:
                        compR.getBtnClienteEncontrado().setEnabled(false);

                        compR.getBtnServicioNoTerminado().setEnabled(true);
                        compR.getBtnServicioTerminadoOk().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);
                        break;
                    case 10:
                        //compR.getBtnServicioNoTerminado().setEnabled(false);
                        Intent intent=new Intent(activity,MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                        break;
                    case 11:
                        // compR.getBtnServicioTerminadoOk().setEnabled(false);
                        Intent intent1=new Intent(activity,MainActivity.class);
                        activity.startActivity(intent1);
                        activity.finish();
                        break;
                }

            }
        }

    }

}
