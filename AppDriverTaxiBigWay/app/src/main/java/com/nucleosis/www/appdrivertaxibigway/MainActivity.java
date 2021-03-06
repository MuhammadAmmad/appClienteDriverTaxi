package com.nucleosis.www.appdrivertaxibigway;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.nucleosis.www.appdrivertaxibigway.Adapters.AdapterNotificaciones;
import com.nucleosis.www.appdrivertaxibigway.AlertDialog.CustomAlertDialog;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansVehiculoConductor;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.Alerta;
import com.nucleosis.www.appdrivertaxibigway.Constans.Constans;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.DonwloadImage.CircleTransform;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentHistoriNew;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentMiUbicacion;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceListarServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceTurno;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.locationDriver;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActivarTurno;
import com.nucleosis.www.appdrivertaxibigway.ws.wsAsignarServicioConductor;
import com.nucleosis.www.appdrivertaxibigway.ws.wsDesactivarTurno;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerConfiguracionAdicionales;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerHoraServer;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListVehiculos;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 21/03/2016.
 */
public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener {
    private componentesR compR;
    public static Activity MAIN_ACTIVITY;
    private MapFragment mapFragment;
    private LayerDrawable icon;
    private MenuItem item;
    private int NumeroNotificacion;
    private MyTypeFace myTypeFace;
    private PreferencesDriver preferencesDriver;
    private Menu menuNoti;
    public static int swTurno = 0;
    private int swPermiteSoloUnServicioTomado = 0;
    private Fichero fichero;
    private List<beansHistorialServiciosCreados> ListaServiciosCreados;
    private AlertDialog alertDialogPanelNotificacion;
    private int MY_PERMISSION_ACCESS_COURSE_LOCATION = 16;
    private CustomAlertDialog customAlertDialog;
    // Assume thisActivity is the current activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        MAIN_ACTIVITY = this;
        //  listVehiculos=new ArrayList<beansVehiculoConductor>();
        myTypeFace = new MyTypeFace(MainActivity.this);
        compR = new componentesR(MainActivity.this);
        compR.cargar_toolbar_2(MAIN_ACTIVITY);
        preferencesDriver = new PreferencesDriver(MainActivity.this);
        //|compR.getToolbar_2().setTitle("Mi ubicacion");
        customAlertDialog=new CustomAlertDialog(this);
        compR.Contros_main_activity(MAIN_ACTIVITY);

        if (compR.getNavigationView() != null) {
            setupNavigationDrawerContent(compR.getNavigationView());
        }
        setupNavigationDrawerContent(compR.getNavigationView());

        fichero = new Fichero(MainActivity.this);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //OBTIENE INFORMACION DE COSTOS GENERALES   TIEMPO ESPERA/PEAJE/VIP/ECONOMICO/AIREACONDICIONADO
        //RUTA DE LA URL FOTO CONDUCTOR
        new wsExtraerConfiguracionAdicionales(MainActivity.this).execute();
        new wsExtraerHoraServer(MainActivity.this).execute();
        ListaServiciosCreados = new ArrayList<beansHistorialServiciosCreados>();
        levantarServicioBackground();
        CreaBroadcasReceiver();
    }
    private void CreaBroadcasReceiver() {
        IntentFilter filter = new IntentFilter(Utils.ACTION_RUN_SERVICE);
        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver = new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);

        IntentFilter filter1 = new IntentFilter(Utils.ACTION_RUN_SERVICE_2);
        ResponseReceiverListarServiciosCreados receiver2 = new
                ResponseReceiverListarServiciosCreados();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver2, filter1);
    }

    private void levantarServicioBackground() {
        Intent intent = new Intent(MainActivity.this, ServiceTurno.class);
        startService(intent);
    }


    private class ResponseReceiverListarServiciosCreados extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Utils.ACTION_RUN_SERVICE_2:
                    ListaServiciosCreados.clear();
                    String json = intent.getStringExtra(Utils.EXTRA_MEMORY_2);
                    if (json != null) {
                             Log.d("jsonList_", json.toString());
                        try {
                            final JSONArray jsonArray = new JSONArray(json);

                            NumeroNotificacion = jsonArray.length();
                            Alerta.setBadgeCount(getApplicationContext(), icon, NumeroNotificacion);
                            item = menuNoti.findItem(R.id.menuAlert);
                            icon = (LayerDrawable) item.getIcon();
                            beansHistorialServiciosCreados row = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                row = new beansHistorialServiciosCreados();
                                row.setFecha(jsonArray.getJSONObject(i).getString("Fecha"));
                                row.setFechaFormat(jsonArray.getJSONObject(i).getString("FechaFormat"));
                                row.setHora(jsonArray.getJSONObject(i).getString("Hora"));
                                row.setNombreStadoServicio(jsonArray.getJSONObject(i).getString("nombreStadoServicio"));
                                row.setImporteTiempoEspera(jsonArray.getJSONObject(i).getString("importeTiempoEspera"));
                                row.setNameDistritoInicio(jsonArray.getJSONObject(i).getString("nameDistritoInicio"));
                                row.setNameDistritoFin(jsonArray.getJSONObject(i).getString("nameDistritoFin"));
                                row.setDireccionIncio(jsonArray.getJSONObject(i).getString("DireccionIncio"));
                                row.setDireccionFinal(jsonArray.getJSONObject(i).getString("direccionFinal"));
                                row.setNumeroMinutoTiempoEspera(jsonArray.getJSONObject(i).getString("numeroMinutoTiempoEspera"));
                                row.setImporteServicio(jsonArray.getJSONObject(i).getString("importeServicio"));
                                row.setIdServicio(jsonArray.getJSONObject(i).getString("idServicio"));
                                row.setStatadoServicio(jsonArray.getJSONObject(i).getString("statadoServicio"));
                                row.setDescripcionServicion(jsonArray.getJSONObject(i).getString("DescripcionServicion"));
                                row.setImporteAireAcondicionado(jsonArray.getJSONObject(i).getString("importeAireAcondicionado"));
                                row.setImportePeaje(jsonArray.getJSONObject(i).getString("importePeaje"));
                                row.setInfoAddress(jsonArray.getJSONObject(i).getString("infoAddress"));
                                row.setIdTipoAuto(jsonArray.getJSONObject(i).getString("idTipoAuto"));
                                row.setDesAutoTipo(jsonArray.getJSONObject(i).getString("desAutoTipo"));
                                row.setIdAutoTipoPidioCliente(jsonArray.getJSONObject(i).getString("idAutoTipoPidioCliente"));
                                row.setDesAutoTipoPidioCliente(jsonArray.getJSONObject(i).getString("desAutoTipoPidioCliente"));
                                row.setImportePeaje(jsonArray.getJSONObject(i).getString("importePeaje"));
                                row.setIndAireAcondicionado(jsonArray.getJSONObject(i).getString("indAireAcondicionado"));
                                row.setImporteAireAcondicionado(jsonArray.getJSONObject(i).getString("importeAireAcondicionado"));
                                row.setNameCliente(jsonArray.getJSONObject(i).getString("nameCliente"));
                                row.setTipoPago(jsonArray.getJSONObject(i).getString("tipoPago"));
                                //   row.setImagenOptional(R.mipmap.ic_notifica_send);
                                ListaServiciosCreados.add(row);

                            }
                            // Log.d("x_array", String.valueOf(jsonArray.length()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Utils.ACTION_MEMORY_EXIT_2:
                    break;
            }
        }
    }
    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Utils.ACTION_RUN_SERVICE:
                    String data = intent.getStringExtra(Utils.EXTRA_MEMORY);
                    //Toast.makeText(MainActivity.this,data,Toast.LENGTH_LONG).show();
                    if (data.equals("0")) {
                        compR.getBtnActivarTurno().setVisibility(View.VISIBLE);
                        compR.getBtnDesactivarTurno().setVisibility(View.GONE);
                        compR.getBtnIrAServicios().setVisibility(View.GONE);
                        swTurno = 2;
                    } else if (data.equals("1")) {

                        try {
                            JSONObject jsonSever = preferencesDriver.ExtraerHoraSistema();
                            JSONObject jsonCoordendas = fichero.ExtraerFechaHoraUltimaDeCoordenadas();
                            if (jsonSever != null && jsonCoordendas != null) {
                                int tiempoUltimaCoordenada = Constans.validarUltimaCoordenadaEnviada(
                                        jsonSever.getString("horaServidor").toString(),
                                        jsonCoordendas.getString("HoraCoordenda").toString());

                                if (tiempoUltimaCoordenada >= 2) {
                                   Intent intent1 = new Intent(MainActivity.this, locationDriver.class);
                                    intent.putExtra("idStadoConductor","2");
                                   startService(intent1);
                                }
                            } else {
                                Log.d("stadoCordendasEnvio", "nulll");

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        JSONArray jsonArray = fichero.ExtraerListaServiciosTomadoConductor();
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    if (jsonArray.getJSONObject(i).getString("statadoServicio").equals("2")
                                            || jsonArray.getJSONObject(i).getString("statadoServicio").equals("3")) {
                                        swPermiteSoloUnServicioTomado = 0;
                                        i = jsonArray.length();
                                    } else {
                                        swPermiteSoloUnServicioTomado = 1;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (jsonArray.length() == 0) {
                                swPermiteSoloUnServicioTomado = 1;
                            }
                        }

                        compR.getBtnActivarTurno().setVisibility(View.GONE);
                        compR.getBtnDesactivarTurno().setVisibility(View.VISIBLE);
                        compR.getBtnIrAServicios().setVisibility(View.VISIBLE);

                        swTurno = 1;
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
        menuNoti = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.menuAlert);
        icon = (LayerDrawable) item.getIcon();
        Alerta.setBadgeCount(this, icon, NumeroNotificacion);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                compR.getDrawer().openDrawer(GravityCompat.START);
                final ImageView imageDriver = (ImageView) findViewById(R.id.imagDriver);
                TextView txtName = (TextView) findViewById(R.id.txtName);
                TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
                txtEmail.setTypeface(myTypeFace.openRobotoLight());
                txtName.setTypeface(myTypeFace.openRobotoLight());
                String[] dataDriver = preferencesDriver.OpenDataDriver();
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
               // Log.d("turnoxxInt",String.valueOf(swTurno));
                if (swTurno == 1) {
                    if (swPermiteSoloUnServicioTomado == 1) {
                        customAlertDialog.CargarNotificaciones(ListaServiciosCreados);
                    } else {
                        String msn=getResources().getString(R.string.tienesServicios);
                        Toast.makeText(MainActivity.this, msn,
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Debe activar un turno", Toast.LENGTH_LONG).show();
                }

                return true;
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
                                compR.getLinearFragment().setVisibility(View.VISIBLE);
                                getSupportActionBar().setTitle("Mi Ubicacion");
                                setFragment(0);
                                return true;
                            case R.id.HistorialCarreras:
                                menuItem.setChecked(true);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                compR.getLinearFragment().setVisibility(View.GONE);
                                setFragment(1);
                                break;
                            case R.id.misDatos:
                                menuItem.setChecked(true);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                compR.getLinearFragment().setVisibility(View.GONE);
                                setFragment(2);
                                break;
                            case R.id.cerrarSesion:
                                //cerrar_sesion_Alert();
                                Log.d("swTurno",String.valueOf(swTurno));
                                switch (swTurno){
                                    case 0:
                                        customAlertDialog.CerrarSesion();
                                        break;
                                    case 1:
                                        Toast.makeText(MainActivity.this,"Desactive el turno para Cerrar Sesión",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        customAlertDialog.CerrarSesion();
                                        break;

                                }


                                break;

                            case R.id.llamarCentral:
                              //  llamar_a_central();
                                customAlertDialog.LlamarACentral();
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
                FragmentMiUbicacion fragmentMiUbicacion = new
                        FragmentMiUbicacion();
                fragmentTransaction.replace(R.id.fragment, fragmentMiUbicacion);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
//                FragmentHistoriaCarreras HistorialCarreras = new FragmentHistoriaCarreras();
                FragmentHistoriNew fragmentHistoriNew = new FragmentHistoriNew();
                //inboxFragment.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, fragmentHistoriNew);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentDataDriver dataDriver = new FragmentDataDriver();
                //inboxFragment.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, dataDriver);
                fragmentTransaction.commit();
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onMapReady(final GoogleMap map) {
        final double[] lat = new double[1];
        final double[] lon = new double[1];

        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
        } else {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location pos) {
                    lat[0] = pos.getLatitude();
                    lon[0] = pos.getLongitude();
                    CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(new LatLng(
                            lat[0], lon[0]), 16);
                    map.moveCamera(cam);
                }
            });
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == MY_PERMISSION_ACCESS_COURSE_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission was denied or request was cancelled
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
           // finish();            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnActivarTurno:
                customAlertDialog.Alert_Elegir_taxi_conductor();
                break;
            case R.id.btnDesactivarTurno:
                customAlertDialog.DescativarTurno();
                break;
            case R.id.btnIrA_Servicios:
                compR.getLinearFragment().setVisibility(View.GONE);
                setFragment(1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(MainActivity.this,ServiceTurno.class);
        stopService(intent);
    }

}
