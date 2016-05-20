package com.nucleosis.www.appdrivertaxibigway;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.BooleanResult;
import com.nucleosis.www.appdrivertaxibigway.Beans.User;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.ConexionRed.ConnectionUtils;
import com.nucleosis.www.appdrivertaxibigway.ConexionRed.conexionInternet;
import com.nucleosis.www.appdrivertaxibigway.ws.wsAsignarServicioConductor;

import static com.nucleosis.www.appdrivertaxibigway.Constans.Utils.LOGGIN_USUARIO;
public class LoingDriverApp extends AppCompatActivity     implements View.OnClickListener{

private componentesR compR;
private boolean conec=false;
     public static Activity LoingDriverApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loggin_driver);
        LoingDriverApp=this;
        compR = new componentesR(LoingDriverApp.this);
        compR.cargar_toolbar(LoingDriverApp);
        compR.Controls_LoginDriverApp(LoingDriverApp);
       // compR.getEditUser().setText("45845785");
      /*  compR.getEditUser().setText("11111111");
        compR.getEditPass().setText("123456");*/
       // compR.getBtnSigIn().setOnClickListener(this);
         }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginDriver:
               /* String USERX=compR.getEditUser().getText().toString().trim();
                String PASSX=compR.getEditPass().getText().toString().trim();*/
                ///VERIFICA LA CONEXION A INTERNET ....... Y LUEGO SE CONECTA
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    AlertNoGps();
                }else{
                    new AsyncTask<String, String, Boolean>() {
                        @Override
                        protected Boolean doInBackground(String... params) {
                            conexionInternet conecicoin=new conexionInternet();
                            return conecicoin.isInternet();
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            super.onPostExecute(aBoolean);
                            if(aBoolean){
                                User user=null;
                                try {
                                    user=new User();
                                    user.setUser(compR.getEditUser().getText().toString().trim());
                                    user.setPassword(compR.getEditPass().getText().toString().trim());
                                    LOGGIN_USUARIO.Login(user,LoingDriverApp);
                                    //Toast.makeText(getApplicationContext(),saludo,Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                String msnInternet=getResources().getString(R.string.InternetAccessRevision);
                                Toast.makeText(LoingDriverApp.this,msnInternet,Toast.LENGTH_LONG).show();
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

}
