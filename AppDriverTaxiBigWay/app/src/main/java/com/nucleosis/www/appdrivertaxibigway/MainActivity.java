package com.nucleosis.www.appdrivertaxibigway;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nucleosis.www.appdrivertaxibigway.Adapters.AdapterListVehiculos;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansVehiculoConductor;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.Alerta;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.DonwloadImage.CircleTransform;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentHistoriNew;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceListarServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceTurno;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActivarTurno;
import com.nucleosis.www.appdrivertaxibigway.ws.wsDesactivarTurno;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListVehiculos;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.kobjects.util.Util;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by karlos on 21/03/2016.
 */
public class MainActivity extends AppCompatActivity
    implements OnMapReadyCallback,View.OnClickListener {
    private componentesR compR;
    public static Activity MAIN_ACTIVITY;
    private LatLng mapCenter;
    private MapFragment mapFragment;
    private LinearLayout linearFragment;
    private LayerDrawable icon;
    private MenuItem item;

  /*  private Button btnActivarTurno;
    private Button btnDesactivarTurno;*/
    private MyTypeFace myTypeFace;
    private PreferencesDriver preferencesDriver;
    //private List<beansVehiculoConductor> listVehiculos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        MAIN_ACTIVITY=this;
      //  listVehiculos=new ArrayList<beansVehiculoConductor>();
        myTypeFace=new MyTypeFace(MainActivity.this);
        compR=new componentesR(MainActivity.this);
        compR.cargar_toolbar_2(MAIN_ACTIVITY);
        preferencesDriver=new PreferencesDriver(MainActivity.this);
        //|compR.getToolbar_2().setTitle("Mi ubicacion");

        compR.Contros_main_activity(MAIN_ACTIVITY);

        if (compR.getNavigationView() != null) {
            setupNavigationDrawerContent(compR.getNavigationView());
        }
            setupNavigationDrawerContent(compR.getNavigationView());

        linearFragment=(LinearLayout)findViewById(R.id.LinearFragment);
        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        levantarServicioBackground();
        CreaBroadcasReceiver();

        levantarServicioBackground_ListaServiciosCreados();

    }


    private void levantarServicioBackground_ListaServiciosCreados(){
        Intent intent=new Intent(MainActivity.this, ServiceListarServiciosCreados.class);
        startService(intent);
    }
    private void CreaBroadcasReceiver(){
        IntentFilter filter = new IntentFilter(Utils.ACTION_RUN_SERVICE);
        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver =       new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);

        IntentFilter filter1=new IntentFilter(Utils.ACTION_RUN_SERVICE_2);
        ResponseReceiverListarServiciosCreados receiver2=new
                ResponseReceiverListarServiciosCreados();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver2,filter1);


    }
    private void levantarServicioBackground(){
        Intent intent=new Intent(MainActivity.this, ServiceTurno.class);
        startService(intent);

    }
    private class ResponseReceiverListarServiciosCreados extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Utils.ACTION_RUN_SERVICE_2:
                    Toast.makeText(MainActivity.this,intent.getStringExtra(Utils.EXTRA_MEMORY_2),Toast.LENGTH_LONG).show();
                    String json=intent.getStringExtra(Utils.EXTRA_MEMORY_2);
                    try {
                        JSONArray jsonArray=new JSONArray(json);
                        for(int i=0; i<jsonArray.length();i++){
                            
                        }
                        Log.d("x_array", String.valueOf(jsonArray.length()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Utils.ACTION_MEMORY_EXIT_2:
                    break;
            }
        }
    }
    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {        }
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Utils.ACTION_RUN_SERVICE:
                    String data=intent.getStringExtra(Utils.EXTRA_MEMORY);
                //   Toast.makeText(MainActivity.this,data,Toast.LENGTH_LONG).show();
                    if(data.equals("0")){
                        compR.getBtnActivarTurno().setVisibility(View.VISIBLE);
                        compR.getBtnDesactivarTurno().setVisibility(View.GONE);
                    }else if(data.equals("1")){
                        compR.getBtnActivarTurno().setVisibility(View.GONE);
                        compR.getBtnDesactivarTurno().setVisibility(View.VISIBLE);
                    }
                    ///compR.getBtnDesactivarTurno().setText(data);
                    break;

                case Utils.ACTION_MEMORY_EXIT:
                   // lblCoordenada.setText("coordendas");
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.menuAlert);
        icon = (LayerDrawable) item.getIcon();
        Alerta.setBadgeCount(this, icon, 4);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                compR.getDrawer().openDrawer(GravityCompat.START);
                final ImageView imageDriver=(ImageView)findViewById(R.id.imagDriver);
                TextView txtName=(TextView)findViewById(R.id.txtName);
                TextView txtEmail=(TextView)findViewById(R.id.txtEmail);
                txtEmail.setTypeface(myTypeFace.openRobotoLight());
                txtName.setTypeface(myTypeFace.openRobotoLight());
                String[] dataDriver=  preferencesDriver.OpenDataDriver();
                txtName.setText(dataDriver[1]);
                txtEmail.setText(dataDriver[4]);
                Picasso.with(MainActivity.this)
                        .load(dataDriver[6])
                        .placeholder(R.mipmap.ic_imagen_driver)
                        .error(R.mipmap.ic_imagen_driver)
                        .transform(new CircleTransform())
                        .into(imageDriver);

                return true;
            case R.id.menuAlert:
                Toast.makeText(MainActivity.this,"hoala",Toast.LENGTH_LONG).show();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.miUbicacion:
                                //Toast.makeText(getApplicationContext(), "HOLA", Toast.LENGTH_SHORT).show();
                                menuItem.setChecked(true);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                linearFragment.setVisibility(View.VISIBLE);
                                getSupportActionBar().setTitle("Mi Ubicacion");
                                return true;
                            case R.id.HistorialCarreras:
                                menuItem.setChecked(true);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                linearFragment.setVisibility(View.GONE);
                                 setFragment(0);
                                break;
                            case R.id.misDatos:
                                menuItem.setChecked(true);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                linearFragment.setVisibility(View.GONE);
                                setFragment(1);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
//                FragmentHistoriaCarreras HistorialCarreras = new FragmentHistoriaCarreras();
                FragmentHistoriNew fragmentHistoriNew=new FragmentHistoriNew();
                //inboxFragment.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, fragmentHistoriNew);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentDataDriver dataDriver = new FragmentDataDriver();
                //inboxFragment.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, dataDriver);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        final double[] lat = new double[1];
        final double[] lon = new double[1];
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location pos) {
                /// Toast.makeText(getApplicationContext(),String.valueOf(pos.getLatitude()+"****"+pos.getLongitude()),Toast.LENGTH_SHORT).show();
                lat[0] = pos.getLatitude();
                lon[0] = pos.getLongitude();
                // Log.d("lat-->",String.valueOf(lat[0]));
                /*CameraUpdate cam = CameraUpdateFactory.newLatLng(new LatLng(
                        lat[0], lon[0]));*/
                CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(new LatLng(
                        lat[0], lon[0]), 13);
                map.moveCamera(cam);


            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent i = new Intent(MainActivity.this,LoingDriverApp.class);;
            finish();
            startActivity(i);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnActivarTurno:
               // Toast.makeText(MainActivity.this,String.valueOf(preferencesDriver.OpenIdDriver()),Toast.LENGTH_LONG).show();
               Alert_Elegir_taxi_conductor();
                break;
            case R.id.btnDesactivarTurno:
                new wsDesactivarTurno(MainActivity.this).execute();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("acatividad", "Destrudia");
        Intent intent=new Intent(MainActivity.this,ServiceTurno.class);
        stopService(intent);

        Intent intent1=new Intent(MainActivity.this, ServiceListarServiciosCreados.class);
        stopService(intent1);
    }

    private void Alert_Elegir_taxi_conductor() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.view_elegir_taxi_conductor, null);
        alertDialogBuilder.setView(view);
        final int[] idVehiculo = {0};
        compR.Controls_alert_elegir_auto_conductor(view);
        //TextView lblElijaSuVehiculo=(TextView)view.findViewById(R.id.lblVehiculo);
        new wsListVehiculos(MainActivity.this,view).execute();

        compR.getSpinerVehiculo().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (wsListVehiculos.listVehiculos != null) {
                    List<beansVehiculoConductor> lista = wsListVehiculos.listVehiculos;
                    idVehiculo[0] = lista.get(position).getIdVehiculo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.CANCEL,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int iii) {
                        if (idVehiculo[0] != 0) {
                            new wsActivarTurno(MainActivity.this, idVehiculo[0]).execute();
                        }


                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
