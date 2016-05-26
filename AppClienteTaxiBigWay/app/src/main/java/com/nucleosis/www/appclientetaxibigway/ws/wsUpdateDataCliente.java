package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.ListaServicios;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.json.JSONException;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 25/05/2016.
 */
public class wsUpdateDataCliente extends AsyncTask<String,String,String[]> {

    private Context context;
    private String idCliente;
    private String name;
    private String appP;
    private String appM;
    private String dni;
    private String nunCelular;
    private String email;
    private PreferencesCliente preferencesCliente;
    private ProgressDialog progressDialog;
    private List<dataClienteSigUp> listaData;
    private dataClienteSigUp row=null;
    Activity activity;
    public wsUpdateDataCliente(Context context,String nunCelular,String dni, String appM, String appP, String name,String email) {
        this.nunCelular = nunCelular;
        this.appM = appM;
        this.appP = appP;
        this.name = name;
        this.email=email;
        this.dni=dni;
        this.context=context;
        preferencesCliente=new PreferencesCliente(context);
        idCliente=preferencesCliente.OpenIdCliente();
        progressDialog=new ProgressDialog(context);
        listaData=new ArrayList<dataClienteSigUp>();
        activity=(Activity)context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
    }

    @Override
    protected String[] doInBackground(String... params) {
        Log.d("eta_aqui", "doInBackGround");
        String[] data=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo18());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("idCliente",Integer.parseInt(idCliente) );
        request.addProperty("nomCliente", name);
        request.addProperty("apePaterno", appP);
        request.addProperty("apeMaterno", appM);
        request.addProperty("numCelular", nunCelular);
        request.addProperty("numDocumentoIdentidad", dni);
        request.addProperty("idArea", 0);
        request.addProperty("idEmpresa", 0);
        request.addProperty("desEmail", email);
        request.addProperty("idUsuario", 0);

        Log.d("resquesUpdateCliente",request.toString());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction18(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
            Log.d("responseUdateCliente",response2.toString());
            if(response2.hasProperty("IND_OPERACION")){
                data[0]=response2.getPropertyAsString("IND_OPERACION").toString();
                data[1]=response2.getPropertyAsString("DES_MENSAJE").toString();
            }else {
                data[0]="";
                data[1]="";
            }
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
    protected void onPostExecute(String[] data_) {
        progressDialog.dismiss();
        if(data_!=null){
            row=new dataClienteSigUp();
            row.setIdCliente(idCliente);
            row.setCelular(nunCelular);
            row.setDni(dni);
            row.setaMaterno(appM);
            row.setaPaterno(appM);
            row.setNombre(name);
            row.setEmail(email);
            listaData.add(row);
            preferencesCliente.InsertDataCliente(listaData);
            Toast.makeText(context,data_[1],Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, ListaServicios.class);
            context.startActivity(intent);
            activity.finish();
        }
        super.onPostExecute(data_);

    }
}
