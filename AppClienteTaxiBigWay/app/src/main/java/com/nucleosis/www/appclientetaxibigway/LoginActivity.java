package com.nucleosis.www.appclientetaxibigway;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.ConexionRed.conexionInternet;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Constantes.Utils;
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
       /* compR.getEditEmail().setText("taxi@gmail.com");
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

                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    AlertNoGps();
                }else{
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
                                if (ActivityCompat.checkSelfPermission(LoginActivity.this,
                                        Manifest.permission.ACCESS_FINE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    // Check Permissions Now+
                                    // map.clear();
                                    ActivityCompat.requestPermissions(LoginActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_1);


                                } else {
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
                                }

                            }else {
                                Toast.makeText(LoginActivity.this,"No tiene Coneccion a intener !!",Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                }


               // Toast.makeText(LoginActivity.this,"hola",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnSigUp:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
               // Toast.makeText(LoginActivity.this,"hola",Toast.LENGTH_SHORT).show();
                final LocationManager manager1 = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if ( !manager1.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    AlertNoGps();
                }else{
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
                                if (ActivityCompat.checkSelfPermission(LoginActivity.this,
                                        Manifest.permission.ACCESS_FINE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    // Check Permissions Now+
                                    // map.clear();
                                    ActivityCompat.requestPermissions(LoginActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_2);
                                } else {
                                    // permission has been granted, continue as usual
                                    intent=new Intent(LoginActivity.this,FrmSigUp.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this,"No tiene Coneccion a intener !!",Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                }

                break;
        }
    }

    private void AlertNoGps() {
        AlertDialog alert = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msnGps=getResources().getString(R.string.gpsMensaje);
        builder.setMessage(msnGps)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_1) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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


            } else {
                Log.d("nada_","fsadfasdf");
                // Permission was denied or request was cancelled
            }
        }else if(requestCode == Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_2){
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intent=new Intent(LoginActivity.this,FrmSigUp.class);
                startActivity(intent);
                finish();


            } else {
                Log.d("nada_","fsadfasdf");
                // Permission was denied or request was cancelled
            }
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
