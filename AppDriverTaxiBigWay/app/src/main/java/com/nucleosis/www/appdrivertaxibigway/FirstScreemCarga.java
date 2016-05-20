package com.nucleosis.www.appdrivertaxibigway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carlos.lopez on 19/05/2016.
 */
public class FirstScreemCarga extends AppCompatActivity {
    private Fichero fichero;
    // Set the duration of the splash screen

  //  private static final long SPLASH_SCREEN_DELAY = 3000;
    private ProgressDialog progressDialog;
    private ProgressBar progressBarCargar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_screem_carga);
       /* Toolbar toolbar=(Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        */
        progressBarCargar=(ProgressBar)findViewById(R.id.progresLoading);
        fichero=new Fichero(FirstScreemCarga.this);
        progressBarCargar.setVisibility(View.VISIBLE);
       /* progressDialog=new ProgressDialog(FirstScreemCarga.this);
        progressDialog.setMessage("cargado...");
        progressDialog.show();*/
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);

                    JSONObject jsonSesion=fichero.ExtraerSesion();
                    if(jsonSesion!=null){
                        try {
                            if(jsonSesion.getString("idSesion").equals("1")){
                                Intent intent=new Intent(FirstScreemCarga.this,MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                Log.d("estadoSesion",jsonSesion.getString("idSesion"));
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
