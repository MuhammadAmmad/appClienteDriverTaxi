package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentHistoriNew;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentMiUbicacion;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by karlos on 08/05/2016.
 */
public class wsActualizarStadoServicio extends AsyncTask<String,String,String[]> {
    private Context context;
    private PreferencesDriver preferencesDriver;
    private String idDriver;
    private String idServicio;
    private String IdStadoServicio;
    private String desMovito;
    private int idTurno;
    private int idAuto;
    private Activity activity;
    private componentesR compR;
    AppCompatActivity appCompatActivity;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private ProgressDialog progressDialog;
    private GridViewWithHeaderAndFooter grid= FragmentHistoriNew.GRID_CANCEL;
    public wsActualizarStadoServicio(Context context,
                                     String idDriver,
                                     String idServicio,
                                     int idTurno,
                                     int idAuto,
                                     String idStadoServicio,
                                     String desMovito
                                     ) {
        this.context = context;
        IdStadoServicio = idStadoServicio;
        this.desMovito = desMovito;
        this.idServicio=idServicio;
        this.idTurno=idTurno;
        this.idAuto=idAuto;
        this.idDriver=idDriver;
        appCompatActivity=(AppCompatActivity) context;
        compR=new componentesR(context);
        compR.Contros_main_activity(appCompatActivity);
        Log.d("idObtener",idDriver);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Espere....");
        progressDialog.show();

    }

    @Override
    protected String[] doInBackground(String... params) {
        Log.d("eta_aqui","doInBackGround");
        String[] data=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;

        request.addProperty("idServicio", Integer.parseInt(idServicio));
        request.addProperty("idTurno", idTurno);
        request.addProperty("idConductor",Integer.parseInt(idDriver));
        request.addProperty("idAuto", idAuto);
        request.addProperty("idEstadoServicio", Integer.parseInt(IdStadoServicio));
        request.addProperty("desMotivo", desMovito);
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
            if(response2.hasProperty("IND_OPERACION")){
                data[0]=response2.getProperty("IND_OPERACION").toString();
                data[1]=response2.getProperty("DES_MENSAJE").toString();

            }else{
                data[0]="";
                data[1]="";
            }
        } catch (Exception e) {
            e.printStackTrace();
            data[0]="";
            data[1]="";
            //Log.d("error", e.printStackTrace());
        }
        tareaLarga();
        return data;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        progressDialog.dismiss();
        if(data!=null){
            if(data[0].equals("1")){
                Toast.makeText(context,data[1], Toast.LENGTH_SHORT).show();
                compR.getLinearFragment().setVisibility(View.VISIBLE);
                fragmentManager =appCompatActivity.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentMiUbicacion fragmentMiUbicacion=new
                        FragmentMiUbicacion();
                fragmentTransaction.replace(R.id.fragment, fragmentMiUbicacion);
                fragmentTransaction.commit();
            }
        }else {
            Toast.makeText(context,"Error al cancelar el servicio",Toast.LENGTH_LONG).show();
        }
    }


    private void tareaLarga()
    {
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {}
    }
}


