package com.nucleosis.www.appclientetaxibigway;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.ServiceBackground.AlarmaLLegadaConductor;

/**
 * Created by carlos.lopez on 24/05/2016.
 */
public class FinalizarAlarma extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_finalize_alarm);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            if(getIntent()!=null){
            String idIntenExtra=getIntent().getStringExtra("idAlarmaNotificacion");
            if(idIntenExtra!=null){
                if(idIntenExtra.equals("1")){
                    Intent intent=new Intent(FinalizarAlarma.this, AlarmaLLegadaConductor.class);
                    stopService(intent);
                    alert();
                }
            }else {
                Log.d("idIntenExtra","nulll");
            }


        }else{
            Log.d("idALarma","nullll");
        }
    }

    private void alert(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(FinalizarAlarma.this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("Cancelar alarma !!!");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent intent=new Intent(FinalizarAlarma.this,ListaServicios.class);
                startActivity(intent);
                finish();
            }
        });
        dialogo1.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*  Intent intent=new Intent(ListaServicios.this, EstadoServiciosCreados.class);
        stopService(intent);*/
    }
}
