package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.FrmSigUp;
import com.nucleosis.www.appclientetaxibigway.LoginActivity;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.Sqlite.SqlGestion;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InterruptedIOException;
import java.util.ArrayList;

/**
 * Created by karlos on 16/04/2016.
 */
public class wsLoginCliente extends AsyncTask<String,String,String> {

    private String Email;
    private String Pass;
    private Context context;
    private ProgressDialog progressDialog;
    private int CasoActivity;
    private int sw=0;
    private int TIME_OUT=15000;
    private boolean tiempoEsperaConexion=false;
    private Intent intent;
    public wsLoginCliente(String email, String pass, Context context,int CasoActivity) {
        Email = email;
        Pass = pass;
        this.context = context;
        this.CasoActivity=CasoActivity;
        progressDialog=new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // Log.d("dataRequest","<-- "+Email+"---"+Pass);
        progressDialog.show();
//      progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.custom_progres_dialog);
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String msnLogin="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo2());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
    //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        request.addProperty("desEmail", Email);
        request.addProperty("desContrasena", Pass);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(),TIME_OUT);

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction2(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("responseLogin",response1.toString());
            SoapObject response2=(SoapObject)response1.getProperty("return");
            Log.d("xxresponse", response1.getPropertyAsString("return").toString());
            codLogin=response2.getPropertyAsString("CHECK_CONTRASENA").toString();
            msnLogin=response2.getPropertyAsString("DES_MENSAJE").toString();
            if(codLogin.equals("1")){
                sw=1;
            }else if(codLogin.equals("0")){
                sw=0;
            }
            //  Log.d("response",response2.toString());
        } catch (InterruptedIOException e){
            tiempoEsperaConexion=true;
        }catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return msnLogin;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(tiempoEsperaConexion){
            Toast.makeText(context,"No se puedo Conectar",Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
        //  Log.d("caso_","--->"+String.valueOf(CasoActivity));
        SqlGestion sqlGestion;
        if(sw==1){
            switch (CasoActivity){

                case 101:
                    //CREAMOS LA BASE DE DATOS SQLITE
                    sqlGestion=new SqlGestion(context);
                    new wsExtraerDataCliente(Email,context,CasoActivity).execute();
                    new wsExtraerDistritos(context).execute();
                    new wsExtraerConfiguracionAdicionales(context).execute();
                    break;
                case 102:
                    //CREAMOS LA BASE DE DATOS SQLITE
                     sqlGestion=new SqlGestion(context);
                    new wsExtraerDataCliente(Email,context,CasoActivity).execute();
                    new wsExtraerDistritos(context).execute();
                    new wsExtraerConfiguracionAdicionales(context).execute();
                    break;
                          }
        }else if(sw==0){
            Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        }


    }
}
