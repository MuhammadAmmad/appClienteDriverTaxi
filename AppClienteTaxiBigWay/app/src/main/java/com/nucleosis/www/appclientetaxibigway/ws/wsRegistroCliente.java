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
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.nucleosis.www.appclientetaxibigway.R;
import java.util.ArrayList;

/**
 * Created by karlos on 16/04/2016.
 */
public class wsRegistroCliente extends AsyncTask<String,String,String> {

    private dataClienteSigUp dataCliente;
    private Context context;
    private ProgressDialog progressDialog;
    private int CasoActivity;
    private int caso=0;
   // private FrmSigUp FRM_SIG_UP=new FrmSigUp();
    public wsRegistroCliente(dataClienteSigUp dataCliente,Context context,int CasoActivity) {
        this.dataCliente = dataCliente;
        this.context=context;
        this.CasoActivity=CasoActivity;
        progressDialog=new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
//      progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.custom_progres_dialog);
    }

    @Override
    protected String doInBackground(String... params) {
        String codLogin="";
        String msnLogin="";
        Log.d("eta_aqui", "doInBackGround");
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo1());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("nomCliente", dataCliente.getNombre());
        request.addProperty("apePaterno",dataCliente.getaPaterno());
        request.addProperty("apeMaterno",dataCliente.getaMaterno());
        request.addProperty("numDocumentoIdentidad",dataCliente.getDni());
        request.addProperty("numCelular",dataCliente.getCelular());
        request.addProperty("desEmail",dataCliente.getEmail());
        request.addProperty("desContrasena",dataCliente.getPassword());
        request.addProperty("idArea",1);
        request.addProperty("idEmpresa",1);
        request.addProperty("idUsuario",0);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction1(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response= (SoapObject) envelope.bodyIn;
            SoapObject response2=(SoapObject)response.getProperty("return");
            codLogin=response2.getPropertyAsString("IND_OPERACION").toString();
            msnLogin=response2.getPropertyAsString("DES_MENSAJE").toString();
            if(codLogin.equals("1")){
                caso=1;
            }else if(codLogin.equals("0")){
                caso=0;
            }else if(codLogin.equals("2")){
                caso=2;
            }


            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            caso =0;
           // Log.d("error", e.getMessage());
        }
        return msnLogin;
    }

    @Override
    protected void onPostExecute(String mensaje) {
        super.onPostExecute(mensaje);
        progressDialog.dismiss();
       switch (caso){
           case 0:
               Toast.makeText(context,"Error al registrar",Toast.LENGTH_SHORT).show();
               break;

           case 1:
               new wsLoginCliente(dataCliente.getEmail(),dataCliente.getPassword(),context,CasoActivity).execute();
                break;

           case 2:
                Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
                break;


       }

    }
}
