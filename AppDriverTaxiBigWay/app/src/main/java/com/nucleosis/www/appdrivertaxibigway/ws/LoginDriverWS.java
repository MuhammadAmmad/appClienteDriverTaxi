package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nucleosis.www.appdrivertaxibigway.Beans.User;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.LoingDriverApp;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by karlos on 21/03/2016.
 */
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.locationDriver;
import com.nucleosis.www.appdrivertaxibigway.Sqlite.SqlGestion;

import java.util.ArrayList;

public class LoginDriverWS extends AsyncTask<User,String,String> {
    private Context context;
    private Intent intent;
    private User user;
    private componentesR compR;
    private Activity ACTIVITY= LoingDriverApp.LoingDriverApp;
    private int sw=0;
    private ProgressDialog progressDialog;
    private Fichero fichero;
    public LoginDriverWS(Context context, User user) {
        this.context = context;
        this.user = user;
        progressDialog=new ProgressDialog(context);
        fichero=new Fichero(context);
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
    protected String doInBackground(User... params) {
        Log.d("eta_aqui","doInBackGround");
        String codLogin="";
        String msnLogin="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo1());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        request.addProperty("numDocumento", user.getUser());
        request.addProperty("desPassword", user.getPassword());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call("http://sistema.taxibigway.com/soap/WS_CONDUCTOR_ACCESAR", envelope, headerPropertyArrayList);
         //   httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("response1_",response1.toString());
            SoapObject response2=(SoapObject)response1.getProperty("return");
            Log.d("responseW", response1.getPropertyAsString("return").toString());
            codLogin=response2.getPropertyAsString("CHECK_CONTRASENA").toString();
            msnLogin=response2.getPropertyAsString("DES_MENSAJE").toString();
            if(codLogin.equals("1")){
                sw=1;
            }else if(codLogin.equals("0")){
                sw=0;
            }
          //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
           //Log.d("error", e.printStackTrace());
        }
        return msnLogin;
    }

    @Override
    protected void onPostExecute(String msn) {
        super.onPostExecute(msn);
        progressDialog.dismiss();
        if(sw==1){
            Toast.makeText(context,msn,Toast.LENGTH_SHORT).show();
            intent=new Intent(ACTIVITY, MainActivity.class);
            ACTIVITY.startActivity(intent);
            ACTIVITY.finish();
            JSONObject jsonSesion2=new JSONObject();
            try {
                jsonSesion2.put("idSesion","1");
                fichero.InsertarSesion(jsonSesion2.toString());

                Intent intent=new Intent(context,locationDriver.class);
                intent.putExtra("idStadoConductor","1");
                context.startService(intent);
                //CREAMOS LA BASE DE DATOS SQLITE

                SqlGestion  sqlGestion=new SqlGestion(context);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            new DonwloadDataUser(context).execute(user.getUser());
            new wsExtraerHoraServer(context).execute();
            new wsExtraerDistritos(context).execute();
            new wsExtraerConfiguracionAdicionales(context).execute();

        } else if(sw==0){
            Toast.makeText(context,msn,Toast.LENGTH_SHORT).show();
        }
    }
}
