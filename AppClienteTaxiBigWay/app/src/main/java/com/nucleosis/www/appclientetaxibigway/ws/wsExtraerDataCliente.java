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
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.FrmSigUp;
import com.nucleosis.www.appclientetaxibigway.LoginActivity;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 17/04/2016.
 */
public class wsExtraerDataCliente extends AsyncTask<String,String,List<dataClienteSigUp>> {
    private String Email;
    private int CasoActivity;
    private Context context;
    private Intent intent;
    private FrmSigUp CX_FRM_SIG_UP=new FrmSigUp();
    private LoginActivity CX_LOGIN_ACTIVITY=new LoginActivity();
    PreferencesCliente preferecesCliente;
    private ProgressDialog progressDialog;
    private int TIME_OUT=15000;
    private boolean tiempoEsperaConexion=false;
    private Fichero fichero;
    public wsExtraerDataCliente(String email,Context context,int CasoActivity) {
        this.Email = email;
        this.CasoActivity=CasoActivity;
        this.context=context;
        preferecesCliente=new PreferencesCliente(context);
        fichero=new Fichero(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.show();
//      progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.custom_progres_dialog);

    }

    @Override
    protected List<dataClienteSigUp> doInBackground(String... params) {

        dataClienteSigUp row=null;
        List<dataClienteSigUp> listCliente=null;
        Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String msnLogin="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo3());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        request.addProperty("desEmail", Email);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(),TIME_OUT);

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction3(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            listCliente=new ArrayList<dataClienteSigUp>();
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("respuesSoap", response1.toString());
            SoapObject response2=(SoapObject)response1.getProperty("return");
            row=new dataClienteSigUp();
            row.setIdCliente(response2.getPropertyAsString("ID_CLIENTE").toString());
            row.setNombre(response2.getPropertyAsString("NOM_CLIENTE").toString());
            row.setaPaterno(response2.getPropertyAsString("APE_PATERNO").toString());
            row.setaMaterno(response2.getPropertyAsString("APE_MATERNO").toString());
            row.setDni(response2.getPropertyAsString("NUM_DOCUMENTO_IDENTIDAD").toString());
            row.setEmail(response2.getPropertyAsString("DES_EMAIL").toString());
            row.setCelular(response2.getPropertyAsString("NUM_CELULAR").toString());
            listCliente.add(row);
        } catch (InterruptedIOException e){
            tiempoEsperaConexion=true;
        }catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.getMessage());
        }
        return listCliente;
    }

    @Override
    protected void onPostExecute(List<dataClienteSigUp> dataList) {
        super.onPostExecute(dataList);
        if(tiempoEsperaConexion){
            Toast.makeText(context,"No se puedo Conectar",Toast.LENGTH_LONG).show();
        }

        /*Log.d("size_", String.valueOf(dataList.size()));
        Log.d("datoRespuesta-->",dataList.get(0).getCelular());*/
        progressDialog.dismiss();
        switch (CasoActivity){
            case 101:
                intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
                preferecesCliente.InsertDataCliente(dataList);
                JSONObject jsonSesion1=new JSONObject();
                try {
                    jsonSesion1.put("idSesion","1");
                    fichero.InsertarSesion(jsonSesion1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CX_FRM_SIG_UP.FRM_SIGUP.finish();
                break;
            case 102:
                intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
                preferecesCliente.InsertDataCliente(dataList);
                JSONObject jsonSesion2=new JSONObject();
                try {
                    jsonSesion2.put("idSesion","1");
                    fichero.InsertarSesion(jsonSesion2.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CX_LOGIN_ACTIVITY.LOGIN_ACTIVITY.finish();
                break;
        }
    }
}
