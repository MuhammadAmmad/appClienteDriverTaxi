package com.nucleosis.www.appdrivertaxibigway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;

import org.json.JSONArray;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.lopez on 06/05/2016.
 */
public class MapsConductorClienteServicio extends AppCompatActivity implements OnMapReadyCallback ,View.OnClickListener{
    private MapFragment mapFragment;
    private int sw = 0;
    private componentesR compR;
    Activity MAPS_CONDUCTOR_CLIENTE;
    private   String idServicio="";
    private MyTypeFace myTypeFace;
    private Fichero fichero;
    private PreferencesDriver preferencesDriver;
    private String idDriver="";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map_conductor_cliente_ubicacion);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        MAPS_CONDUCTOR_CLIENTE=this;
        myTypeFace=new MyTypeFace(MapsConductorClienteServicio.this);
        compR=new componentesR(MapsConductorClienteServicio.this);
        compR.Controls_Maps_conducotor_cliente(MAPS_CONDUCTOR_CLIENTE);
        fichero=new Fichero(MapsConductorClienteServicio.this);
        progressDialog=new ProgressDialog(MapsConductorClienteServicio.this);
        JSONArray jsonServicios=fichero.ExtraerListaServiciosTomadoConductor();
        if(getIntent()!=null){
            idServicio=  getIntent().getStringExtra("idServicio");
            String stadoServicio=getIntent().getStringExtra("stadoService");
            Log.d("idServicio_stadoSevcio",idServicio+"-->"+stadoServicio);
            switch (Integer.parseInt(stadoServicio)){
                case 3:
                    compR.getBtnClienteEncontrado().setEnabled(false);
                    break;
                default:
                    compR.getBtnClienteEncontrado().setEnabled(true);
                    break;
            }
        }

        preferencesDriver=new PreferencesDriver(MapsConductorClienteServicio.this);
        idDriver=preferencesDriver.OpenIdDriver();

    }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdicionales:
                ///alert("");
                //   adicionales();

                /*compR.getBtnServicioNoTerminado().setEnabled(false);
                compR.getBtnServicioTerminadoOk().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(false);
                compR.getBtnClienteEncontrado().setEnabled(false);*/

                break;

            case R.id.btnClienteEncontrado:
                String mensaje1="Encontro al cliente ?";
                alert("3",mensaje1,9);
               Log.d("idTurnoVehiculo-->",preferencesDriver.ExtraerIdTurno()+"***"+preferencesDriver.ExtraerIdVehiculo());
             /*  clienteEcontrado();*/
               /* int idTurno=Integer.parseInt(preferencesDriver.ExtraerIdTurno());
                int idAuto=Integer.parseInt(preferencesDriver.ExtraerIdVehiculo());
                new wsActualizarStadoServicio(MapsConductorClienteServicio.this,
                        idDriver,idServicio,idTurno,idAuto,"3","").execute();*/

                compR.getBtnServicioNoTerminado().setEnabled(false);
                compR.getBtnServicioTerminadoOk().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(false);
                compR.getBtnAdicionales().setEnabled(false);
                break;

            case R.id.btnSercioNoTerminado:
                String mensaje2="No termino el servicio ?";
                alert("5",mensaje2,10);

                compR.getBtnClienteEncontrado().setEnabled(false);
                compR.getBtnServicioTerminadoOk().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(false);
                compR.getBtnAdicionales().setEnabled(false);

                break;
            case R.id.btnServicioTerminadoOk:
                String mensaje3="Termino el servicio correctamente ?";
                alert("4",mensaje3,11);

                compR.getBtnClienteEncontrado().setEnabled(false);
                compR.getBtnServicioNoTerminado().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(false);
                compR.getBtnAdicionales().setEnabled(false);

                break;
            case R.id.btnIrA_Servicios:
                Intent intent=new Intent(MapsConductorClienteServicio.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }



    private void ActualizarStadosServicio(String idStadoService, String motivo, final int idObjetos) {
        new AsyncTask<String, String, String[]>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Cargando...");
                progressDialog.show();
            }

            @Override
            protected  String[] doInBackground(String... params) {

                Log.d("eta_aqui","doInBackGround");
                String[] data=new String[2];
                SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
                SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;

                request.addProperty("idServicio", Integer.parseInt(idServicio));
                request.addProperty("idTurno",Integer.parseInt(preferencesDriver.ExtraerIdTurno()));
                request.addProperty("idConductor",Integer.parseInt(idDriver));
                request.addProperty("idAuto",Integer.parseInt(preferencesDriver.ExtraerIdVehiculo()));
                request.addProperty("idEstadoServicio",Integer.parseInt(params[0].toString()));
                request.addProperty("desMotivo", params[1].toString());
                request.addProperty("usrActualizacion", 0);
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                try {
                    ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                    headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                    httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
                    // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                    SoapObject response1= (SoapObject) envelope.bodyIn;
                    SoapObject response2= (SoapObject)response1.getProperty("return");
                    Log.d("respOk",response2.toString());
                    if(response2.hasProperty("IND_OPERACION")){
                        data[0]=response2.getProperty("IND_OPERACION").toString();
                        data[1]=response2.getProperty("DES_MENSAJE").toString();

                    }else{
                        data[0]="";
                        data[1]="";
                    }

                    // Log.d("actualizarServicio", response2.toString());


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
            protected void onPostExecute(String[] data) {
                super.onPostExecute(data);
                progressDialog.dismiss();
                if(data!=null){
                    if(data[0].equals("1")){
                        Toast.makeText(getApplicationContext(),data[1],Toast.LENGTH_SHORT).show();
                        switch (idObjetos){
                            case 9:
                                compR.getBtnClienteEncontrado().setEnabled(false);

                                compR.getBtnServicioNoTerminado().setEnabled(true);
                                compR.getBtnServicioTerminadoOk().setEnabled(true);
                                compR.getBtnIrAServicios().setEnabled(true);
                                compR.getBtnAdicionales().setEnabled(true);
                                break;
                            case 10:
                                //compR.getBtnServicioNoTerminado().setEnabled(false);
                                Intent intent=new Intent(MapsConductorClienteServicio.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 11:
                               // compR.getBtnServicioTerminadoOk().setEnabled(false);
                                Intent intent1=new Intent(MapsConductorClienteServicio.this,MainActivity.class);
                                startActivity(intent1);
                                finish();
                                break;
                        }

                    }
                }

            }
        }.execute(idStadoService,motivo);//paramaretro
    }


    private void clienteEcontrado() {

        new AsyncTask<String, String, String[]>() {
            @Override
            protected  String[] doInBackground(String... params) {

                Log.d("eta_aqui","doInBackGround");
                String[] data=new String[2];
                SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
                SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;

                request.addProperty("idServicio", Integer.parseInt(idServicio));
                request.addProperty("idTurno",Integer.parseInt(preferencesDriver.ExtraerIdTurno()));
                request.addProperty("idConductor",Integer.parseInt(idDriver));
                request.addProperty("idAuto",Integer.parseInt(preferencesDriver.ExtraerIdVehiculo()));
                request.addProperty("idEstadoServicio",3);
                request.addProperty("desMotivo", "");
                request.addProperty("usrActualizacion", 0);
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

                try {
                    ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                    headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                    httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
                    // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
                    SoapObject response1= (SoapObject) envelope.bodyIn;
                    SoapObject response2= (SoapObject)response1.getProperty("return");
                    Log.d("respOk",response2.toString());
                    if(response2.hasProperty("IND_OPERACION")){
                        data[0]=response2.getProperty("IND_OPERACION").toString();
                        data[1]=response2.getProperty("DES_MENSAJE").toString();

                    }else{
                        data[0]="";
                        data[1]="";
                    }

                    // Log.d("actualizarServicio", response2.toString());


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
            protected void onPostExecute(String[] data) {
                super.onPostExecute(data);
                if(data!=null){
                    if(data[0].equals("1")){
                        Toast.makeText(getApplicationContext(),data[1],Toast.LENGTH_SHORT).show();
                        compR.getBtnClienteEncontrado().setEnabled(false);
                    }
                }

            }
        }.execute();
    }




    private void alert(final String stadoServicio, final String mensaje, final int caseObjeto){
      final Activity activity=MapsConductorClienteServicio.this;
        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(activity);
        final View view1 = activity.getLayoutInflater().inflate(R.layout.view_alert_actualizar_stado, null);
        TextView lblTitutlo=(TextView) view1.findViewById(R.id.lblTxtTituloAlert) ;
        lblTitutlo.setTypeface(myTypeFace.openRounded_elegance());
        TextView lblMensaje=(TextView) view1.findViewById(R.id.lblMensajeAlert) ;
        lblMensaje.setText(mensaje);
        lblMensaje.setTypeface(myTypeFace.openRobotoLight());
        Button  btnOk=(Button)view1.findViewById(R.id.btnOk);
        ImageView imageVieButtonCerrarAlert=(ImageView)view1.findViewById(R.id.ImgButtonCancelAlert);


        alertDialogBuilder1.setView(view1);
        // alertDialogBuilder.setTitle(R.string.addContacto);


        final AlertDialog alertDialog1 = alertDialogBuilder1.create();
        alertDialog1.setCancelable(false);
        imageVieButtonCerrarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                switch (caseObjeto){
                    case 9:
                        compR.getBtnServicioNoTerminado().setEnabled(true);
                        compR.getBtnServicioTerminadoOk().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);
                        break;
                    case 10:
                        compR.getBtnClienteEncontrado().setEnabled(true);
                        compR.getBtnServicioTerminadoOk().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);
                        break;
                    case 11:
                        compR.getBtnClienteEncontrado().setEnabled(true);
                        compR.getBtnServicioNoTerminado().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);
                        break;
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarStadosServicio(stadoServicio,mensaje,caseObjeto);
                alertDialog1.dismiss();
            }
        });
        alertDialog1.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
