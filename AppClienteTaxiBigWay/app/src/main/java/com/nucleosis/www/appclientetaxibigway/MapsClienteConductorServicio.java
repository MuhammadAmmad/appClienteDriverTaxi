package com.nucleosis.www.appclientetaxibigway;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Constantes.Utils;
import com.nucleosis.www.appclientetaxibigway.ServiceBackground.EstadoServiciosCreados;
import com.nucleosis.www.appclientetaxibigway.ServiceBackground.PosicionConductor;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by carlos.lopez on 04/05/2016.
 */
public class MapsClienteConductorServicio extends AppCompatActivity
        implements OnMapReadyCallback {
    private MapFragment mapFragment;
    private int sw = 0;
    Activity MAPS_CLIENTE_CONDUCTOR;
    TextView lblCoordenada;
    Button btnCoordenada, btnStopCoordenada;
    private ComponentesR compR;
    private String idServicio="-1";
    private String idTurno="0";
    private String idDriver="0";
    private String idAuto="0";
    private String desMotivo="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_maps_cliente_conductor_servicio);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        MAPS_CLIENTE_CONDUCTOR=this;
        compR=new ComponentesR(MapsClienteConductorServicio.this);
        compR.Controls_Maps_Cliente_Conductor(MAPS_CLIENTE_CONDUCTOR);
        if(getIntent()!=null){
            idServicio=  getIntent().getStringExtra("idServicio");
            Log.d("idServicioIntent",idServicio);
        }
        Intent intent=new Intent(MapsClienteConductorServicio.this, EstadoServiciosCreados.class);
        startService(intent);

        IntentFilter filter = new IntentFilter(Utils.ACTION_RUN_SERVICE);
        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver = new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);
        compR.getImgButtonCancelarServicio().setEnabled(false);
        compR.getImgButtonCancelarServicio().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MapsClienteConductorServicio.this);
                dialogo1.setTitle("Alerta!!!");
                dialogo1.setMessage("¿ Esta seguro que desea cancelar el servicio?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelarServicio(idServicio);
                        Log.d("dataAA","idTurno: "+idTurno +" idauto: "+idAuto+"  idDruver: "+idDriver);
                    }
                });
                dialogo1.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();


            }
        });
        compR.getImgButtonInAServicios().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"hola2",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(MapsClienteConductorServicio.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cancelarServicio(final String idServicio_){
        new AsyncTask<String, String, String[]>() {
        ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(MapsClienteConductorServicio.this);
                progressDialog.setMessage("Espere...");
                progressDialog.show();
            }

            @Override
            protected String[] doInBackground(String... params) {
                Log.d("eta_aqui","doInBackGround");
                String[] data=new String[2];
                SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo17());
                SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;

                request.addProperty("idServicio", Integer.parseInt(idServicio_));
                request.addProperty("idTurno", Integer.parseInt(idTurno));
                request.addProperty("idConductor",Integer.parseInt(idDriver));
                request.addProperty("idAuto", idAuto);
                request.addProperty("idEstadoServicio", 6);//6 CANCELADO POR EL CLIENTE
                request.addProperty("desMotivo", desMotivo);
                request.addProperty("usrActualizacion", 0);
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                try {
                    ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                    headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                    httpTransport.call(ConstantsWS.getSoapAction17(), envelope, headerPropertyArrayList);
                    // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                    SoapObject response1= (SoapObject) envelope.bodyIn;
                    SoapObject response2= (SoapObject)response1.getProperty("return");
                    Log.d("respCcan",response2.toString());
                    if(response2.hasProperty("IND_OPERACION")){
                        data[0]=response2.getProperty("IND_OPERACION").toString();
                        data[1]=response2.getProperty("DES_MENSAJE").toString();

                    }else{
                        data[0]="";
                        data[1]="";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    data[0]="";
                    data[1]="";
                    //Log.d("error", e.printStackTrace());
                }
                //   tareaLarga();
                return data;
            }

            @Override
            protected void onPostExecute(String[] data) {
                super.onPostExecute(data);
                progressDialog.show();
                if(data!=null){
                    if(data[0].equals("1")){
                        Toast.makeText(MapsClienteConductorServicio.this,"Su servicio fue cancelado con exito",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MapsClienteConductorServicio.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                     }
                }
            }
        }.execute();

    }
    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Utils.ACTION_RUN_SERVICE:

                 String data = intent.getStringExtra(Utils.EXTRA_MEMORY);
                    if(data!=null){
                        try {
                            JSONArray jsonArrayServis=new JSONArray(data);

                            for(int i=0;i<jsonArrayServis.length();i++){
                                if(idServicio.equals(jsonArrayServis.getJSONObject(i).getString("idServicio"))){
                                    Log.d("jsonArrisss",idServicio+"*---"+jsonArrayServis.getJSONObject(i).getString("stadoServicio"));

                                    if(jsonArrayServis.getJSONObject(i).getString("idStadoServicio").equals("1")){
                                        //CREADO
                                        String msn="Espere....";
                                        compR.getTxtMensajeDeEstado().setText(msn);
                                        compR.getImageViewColorStado().setBackgroundColor(Color.rgb(241,127,244));
                                        compR.getImgButtonCancelarServicio().setEnabled(true);
                                        idTurno="0";
                                        idDriver="0";
                                    }else if(jsonArrayServis.getJSONObject(i).getString("idStadoServicio").equals("2")){
                                        //CONFIRMADO POR EL CONDUCTOR
                                        String msn="Servicio tomado...."+"\n"+"la movil acercandose.";
                                        compR.getTxtMensajeDeEstado().setText(msn);
                                        compR.getImageViewColorStado().setBackgroundColor(Color.rgb(216,22,189));
                                        compR.getImgButtonCancelarServicio().setEnabled(true);
                                        idTurno=jsonArrayServis.getJSONObject(i).getString("idTurno");
                                        idDriver=jsonArrayServis.getJSONObject(i).getString("idConductor");
                                    }else if(jsonArrayServis.getJSONObject(i).getString("idStadoServicio").equals("3")){
                                        //EN CURSO
                                        compR.getImgButtonCancelarServicio().setEnabled(false);
                                        String msn="Servicio en curso....";
                                        compR.getTxtMensajeDeEstado().setText(msn);
                                        compR.getImageViewColorStado().setBackgroundColor(Color.rgb(75,219,92));
                                    }
                                   /* Toast.makeText(MapsClienteConductorServicio.this,
                                            jsonArrayServis.getJSONObject(i).getString("stadoServicio"),Toast.LENGTH_SHORT).show();*/

                                    i=jsonArrayServis.length();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                 //   lblCoordenada.setText(data[0] + "-->" + data[1]);
                    //Toast.makeText(MapsClienteConductorServicio.this,data,Toast.LENGTH_SHORT).show();
                    break;

                case Utils.ACTION_MEMORY_EXIT:
                 //   lblCoordenada.setText("coordendas");
                    break;
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onMapReady(final GoogleMap map) {
        final double[] lat = new double[1];
        final double[] lon = new double[1];
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location pos) {
                lat[0] = pos.getLatitude();
                lon[0] = pos.getLongitude();
                if (sw == 0) {
                    sw = 1;
                    CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(new LatLng(
                            lat[0], lon[0]), 12);
                    MarcadorServicio(map, lat[0], lon[0]);
                    map.animateCamera(cam);
                }
            }
        });

    }

    private void MarcadorServicio(GoogleMap googleMap,double lat,double lon){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(MapsClienteConductorServicio.this, EstadoServiciosCreados.class);
        stopService(intent);
    }
}
