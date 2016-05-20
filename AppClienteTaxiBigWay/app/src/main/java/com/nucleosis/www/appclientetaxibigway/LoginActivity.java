package com.nucleosis.www.appclientetaxibigway;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.ConexionRed.conexionInternet;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Locale;

import static com.nucleosis.www.appclientetaxibigway.Constantes.UtilsInterfaces.LOGGIN_USUARIO;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {
private ComponentesR compR;
private long mLastClickTime = 0;
// private    TextToSpeech t1;
public  static Activity LOGIN_ACTIVITY;
  private Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LOGIN_ACTIVITY=this;
        compR=new ComponentesR(LoginActivity.this);
        compR.Controls_LoginActivity(LOGIN_ACTIVITY);
    /*    t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                   // t1.setLanguage(Locale.US);
                Locale local=new Locale("spa","es");
                     t1.setLanguage(local);

                }
            }
        });*/
      /*  compR.getEditEmail().setText("taxi@gmail.com");
        compR.getEditPassword().setText("123456");*/

    }
    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSigIn:
             //   String toSpeak = "su taxi a llegado";
             /*   String toSpeak = "Alerta, Hay servicios";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();

                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
*/
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                new AsyncTask<String, String, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... params) {
                        conexionInternet  conexion=new conexionInternet();
                        return  conexion.isInternet();
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if(aBoolean){
                            String email=compR.getEditEmail().getText().toString().trim();
                            String pass=compR.getEditPassword().getText().toString().trim();
                            String msn=getResources().getString(R.string.CamposObligatorios);
                            if(email.length()!=0 && pass.length()!=0 ){
                                try {
                                    LOGGIN_USUARIO.LoginCliente(email,pass,LoginActivity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),msn,Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,"No tiene Coneccion a intener !!",Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();




               // Toast.makeText(LoginActivity.this,"hola",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnSigUp:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
               // Toast.makeText(LoginActivity.this,"hola",Toast.LENGTH_SHORT).show();
                new AsyncTask<String, String, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... params) {
                        conexionInternet  conexion1=new conexionInternet();
                        return conexion1.isInternet();
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if(aBoolean){
                            intent=new Intent(LoginActivity.this,FrmSigUp.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"No tiene Coneccion a intener !!",Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();





                break;
        }
    }

    @Override
    public void onPause(){
    /*    if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }*/
        super.onPause();
    }
    private void probandoWS() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {

                SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo9());
                SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;
                String parametro1=null;
                String parametro2=null;
                String parametro3=null;
                String parametro4=null;
                //Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
                request.addProperty("idCliente",parametro1 );
                request.addProperty("fecServicio", parametro2);
                request.addProperty("idConductor", parametro3);
                request.addProperty("idEstadoServicio", 2);
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                try {
                    ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                    headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                    httpTransport.call(ConstantsWS.getSoapAction9(), envelope, headerPropertyArrayList);
                    // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                    SoapObject response1= (SoapObject) envelope.bodyIn;
                    Log.d("respuesSoap", response1.toString());
                    //  SoapObject response2=(SoapObject)response1.getProperty("return");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("error", e.getMessage());
                }
                return null;
            }
        }.execute();
    }


}
