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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansVehiculoConductor;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.Alerta;
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
        implements OnMapReadyCallback, View.OnClickListener, AdapterNotificaciones.OnItemClickListener {
    private componentesR compR;
    public static Activity MAIN_ACTIVITY;
    private MapFragment mapFragment;
    private LayerDrawable icon;
    private MenuItem item;
    private int NumeroNotificacion;
    private MyTypeFace myTypeFace;
    private PreferencesDriver preferencesDriver;
    private Menu menuNoti;
    private int swTurno = 0;
    private int swPermiteSoloUnServicioTomado = 0;
    private Fichero fichero;
    private List<beansHistorialServiciosCreados> ListaServiciosCreados;
    private AlertDialog alertDialogPanelNotificacion;

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
        JSONObject jsonConfiguracion = fichero.ExtraerConfiguraciones();
        if (jsonConfiguracion == null) {
            new wsExtraerConfiguracionAdicionales(MainActivity.this).execute();
        }

        ListaServiciosCreados = new ArrayList<beansHistorialServiciosCreados>();

        //LLENAR LISTA DE VEHICULOS
        //  new wsListVehiculosJson(MainActivity.this).execute();

        levantarServicioBackground();
        CreaBroadcasReceiver();
        levantarServicioBackground_ListaServiciosCreados();

    }

    private void levantarServicioBackground_ListaServiciosCreados() {
        Intent intent = new Intent(MainActivity.this, ServiceListarServiciosCreados.class);
        startService(intent);
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

    @Override
    public void onClick(AdapterNotificaciones.ViewHolder holder, String posicion) {

        /*Toast.makeText(getApplicationContext(),ListaServiciosCreados.get(Integer.parseInt(posicion)).getIdServicio(),
                Toast.LENGTH_SHORT).show();*/
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.view_detalle_servicio_custom, null);
        final int posicion_ = Integer.parseInt(posicion);
        TextView lblDetalleServicio = (TextView) view.findViewById(R.id.txtDetalleServicio);
        //   Toast.makeText(this, ListaServiciosCreados.get(posicion_).getIdServicio().toString(), Toast.LENGTH_SHORT).show();
        String detalle = "";
        JSONObject jsonConfiguracion = fichero.ExtraerConfiguraciones();
        String importTipoAutoSolicitoCliente = "0.0";
        Double sumaImportesServicio = 0.0;
        if (jsonConfiguracion != null) {
            Log.d("configu_", jsonConfiguracion.toString());
            if (ListaServiciosCreados.get(posicion_).getIdAutoTipoPidioCliente().equals("1")) {
                try {
                    importTipoAutoSolicitoCliente = jsonConfiguracion.getString("impAutoVip");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                importTipoAutoSolicitoCliente = "0.0";
            }
        }
        sumaImportesServicio = Double.parseDouble(importTipoAutoSolicitoCliente) +
                Double.parseDouble(ListaServiciosCreados.get(posicion_).getImporteAireAcondicionado().toString()) +
                Double.parseDouble(ListaServiciosCreados.get(posicion_).getImportePeaje().toString()) +
                Double.parseDouble(ListaServiciosCreados.get(posicion_).getImporteServicio().toString());
        detalle =
                "<font color=\"#11aebf\"><bold>Fecha:&nbsp;</bold></font>"
                        + "\t" + ListaServiciosCreados.get(posicion_).getFecha().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Hora:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getHora().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Distri Incio:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getNameDistritoInicio().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Direccion Incio:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getDireccionIncio().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Distri Fin:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getNameDistritoFin().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Direccion Fin:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getDireccionFinal().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Num mint espera:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getNumeroMinutoTiempoEspera().toString() + "\t" + " min" + "<br>"
                        + "<font color=\"#11aebf\"><bold>Tipo Servicio :&nbsp;</bold></font>"
                        + "( " + ListaServiciosCreados.get(posicion_).getDesAutoTipoPidioCliente().toString() + " )" + "<br><br>"

                        + "<font color=\"#11aebf\"><bold>Import Serv:&nbsp;</bold></font>"
                        + "S/." + ListaServiciosCreados.get(posicion_).getImporteServicio().toString() + "<br>"

                        + "<font color=\"#11aebf\"><bold>Import Aire:&nbsp;</bold></font>"
                        + "S/." + ListaServiciosCreados.get(posicion_).getImporteAireAcondicionado().toString() + "<br>"

                      /*  +"<font color=\"#11aebf\"><bold>Import Tiem espera:&nbsp;</bold></font>"
                        +"S/."+jsonDetalle.getString("importeTiempoEspera")+"<br>"*/

                        + "<font color=\"#11aebf\"><bold>Import Peaje:&nbsp;</bold></font>"
                        + "S/." + ListaServiciosCreados.get(posicion_).getImportePeaje().toString() + "<br>"

                        + "<font color=\"#11aebf\"><bold>Import Tipo auto:&nbsp;</bold></font>"
                        + "S/." + importTipoAutoSolicitoCliente + "<br><br>"

                        + "<font color=\"#11aebf\"><bold>Import Total:&nbsp;</bold></font>"
                        + "S/." + String.valueOf(sumaImportesServicio) + "<br><br>";
        //+"\n"+jsonDetalle.getString("numeroMovilTaxi")

        lblDetalleServicio.setText(Html.fromHtml(detalle));


        alertDialogBuilder.setView(view);
        AlertDialog alertDialog;

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Esta seguro de aceptar este servicio ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        alertDialogPanelNotificacion.dismiss();
                        new wsAsignarServicioConductor(MainActivity.this,
                                ListaServiciosCreados.get(posicion_).getIdServicio()).execute();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

                // Create the AlertDialog object and return it

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private class ResponseReceiverListarServiciosCreados extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Utils.ACTION_RUN_SERVICE_2:
                    //  Toast.makeText(MainActivity.this,intent.getStringExtra(Utils.EXTRA_MEMORY_2),Toast.LENGTH_LONG).show();
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

    private int validarUltimaCoordenadaEnviada(String HoraServer, String HoraCoordenada) {
        Log.d("horaServer", HoraServer + "--->Coor: " + HoraCoordenada);
        int minutos = 0;
        if (HoraServer.length() == 5 && HoraCoordenada.length() == 5) {
            /* HoraServer="18:01";//135 //HORA ACTUAL 22:50
             HoraTurno="06:00";//1130 // HORA DEL TURNO*/
            int minutosFormales = 12 * 60;

            int H1 = Integer.parseInt(HoraServer.substring(0, 2));
            int M1 = Integer.parseInt(HoraServer.substring(3, 5));
            int TotalMinutos1 = H1 * 60 + M1;

            int H2 = Integer.parseInt(HoraCoordenada.substring(0, 2));
            int M2 = Integer.parseInt(HoraCoordenada.substring(3, 5));

            int TotalMinutos2 = H2 * 60 + M2;

            if (H1 < H2) {
                int diaMinutos = 24 * 60;
                int RestaParcialMinutos = diaMinutos - TotalMinutos2;
                Log.d("horaTermino", String.valueOf(RestaParcialMinutos));
                int SumaTotal = RestaParcialMinutos + TotalMinutos1;
                Log.d("TotalMintuos", String.valueOf(SumaTotal));

                minutos = SumaTotal;
            } else if (H1 >= H2) {
                minutos = TotalMinutos1 - TotalMinutos2;
                Log.d("TotalMintuos", String.valueOf(minutos));
            }
        }


        Log.d("minutosRetorno", String.valueOf(minutos));
        return minutos;
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
                        Log.d("EstadoTurno", "-->inactivo");
                        compR.getBtnActivarTurno().setVisibility(View.VISIBLE);
                        compR.getBtnDesactivarTurno().setVisibility(View.GONE);
                        compR.getBtnIrAServicios().setVisibility(View.GONE);
                        swTurno = 2;
                    } else if (data.equals("1")) {

                        Log.d("EstadoTurno", "-->activo");

                        try {
                            JSONObject jsonSever = preferencesDriver.ExtraerHoraSistema();
                            JSONObject jsonCoordendas = fichero.ExtraerFechaHoraUltimaDeCoordenadas();
                            if (jsonSever != null && jsonCoordendas != null) {
                                int tiempoUltimaCoordenada = validarUltimaCoordenadaEnviada(
                                        jsonSever.getString("horaServidor").toString(),
                                        jsonCoordendas.getString("HoraCoordenda").toString());
                                Log.d("tiempoUltimaCoordenad", String.valueOf(tiempoUltimaCoordenada));
                                if (tiempoUltimaCoordenada >= 2) {
                                    Intent intent1 = new Intent(MainActivity.this, locationDriver.class);
                                    startService(intent1);
                                }
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
                if (swTurno == 1) {
                    if (swPermiteSoloUnServicioTomado == 1) {
                        cargarAlertNotificaciones();
                    } else {
                        Toast.makeText(MainActivity.this, "Tiene servicios tomados, Termine los servicios !!!",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Debe activar un turno", Toast.LENGTH_LONG).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarAlertNotificaciones() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.view_notificaicon_recycler, null);
        //   RecyclerView.Adapter adapter;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager lManager;
        //onLayoutChildren

        compR.Controls_notificaciones(view);
        //requestWindowFeature
        compR.getRecycler().setClipToPadding(true);
        compR.getRecycler().setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        compR.getRecycler().setLayoutManager(lManager);
        // Crear un nuevo adaptador

        adapter = new AdapterNotificaciones(ListaServiciosCreados, this, this);
        compR.getRecycler().setAdapter(adapter);
        alertDialogBuilder.setView(view);
        alertDialogPanelNotificacion = alertDialogBuilder.create();
        compR.getBtnDismisNotificaciones().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogPanelNotificacion.dismiss();
            }
        });
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialogPanelNotificacion.show();
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
                                JSONObject jsonSesion=new JSONObject();
                                try {
                                    jsonSesion.put("idSesion","0");
                                    fichero.InsertarSesion(jsonSesion.toString());
                                    Log.d("StracFichero",fichero.ExtraerSesion().toString());
                                    Intent intentLongin=new Intent(MainActivity.this,LoingDriverApp.class);
                                    startActivity(intentLongin);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
            finish();

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
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Esta seguro de desactivar su turno ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        new wsDesactivarTurno(MainActivity.this).execute();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();

                break;
            case R.id.btnIrA_Servicios:
                //Toast.makeText(MainActivity.this,"hola",Toast.LENGTH_LONG).show();
                compR.getLinearFragment().setVisibility(View.GONE);
                setFragment(1);
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
