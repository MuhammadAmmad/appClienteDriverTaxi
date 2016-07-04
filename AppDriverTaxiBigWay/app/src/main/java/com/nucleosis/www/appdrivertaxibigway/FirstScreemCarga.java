package com.nucleosis.www.appdrivertaxibigway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.nucleosis.www.appdrivertaxibigway.ConexionRed.ConnectionUtils;
import com.nucleosis.www.appdrivertaxibigway.ConexionRed.conexionInternet;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carlos.lopez on 19/05/2016.
 */
public class FirstScreemCarga extends AppCompatActivity {
    private Fichero fichero;
    private ProgressDialog progressDialog;
    private ProgressBar progressBarCargar;
    private Boolean conec=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_screem_carga);
        fichero=new Fichero(FirstScreemCarga.this);
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);

                    JSONObject jsonSesion=fichero.ExtraerSesion();
                    if(jsonSesion!=null){
                        try {
                            if(jsonSesion.getString("idSesion").equals("1")){
                                conexionInternet conecicoin=new conexionInternet();
                                conec= conecicoin.isInternet();
                                if(conec){
                                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                                        ///NO TINE GPS ACTIVADO
                                        Intent intent=new Intent(FirstScreemCarga.this,LoingDriverApp.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();
                                    }else{
                                        Intent intent=new Intent(FirstScreemCarga.this,MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();
                                        Log.d("estadoSesion",jsonSesion.getString("idSesion"));
                                    }


                                }else {
                                    ///NO TIENE ACCESO A INTERNET
                                    Intent intent=new Intent(FirstScreemCarga.this,LoingDriverApp.class);
                                    intent.putExtra("inter","0");
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                }

                            }else if(jsonSesion.getString("idSesion").equals("0")){
                                Log.d("estadoSesion",jsonSesion.getString("idSesion"));
                                Intent intent=new Intent(FirstScreemCarga.this,LoingDriverApp.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Intent intent=new Intent(FirstScreemCarga.this,LoingDriverApp.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        Log.d("estadoSesion","nulll");
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                          }
            }
        };
        timerThread.start();

    }
}
