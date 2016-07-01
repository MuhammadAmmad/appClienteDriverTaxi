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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nucleosis.www.appdrivertaxibigway.Adapters.PlaceAutocompleteAdapter;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansListaPolygono;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.Alerta;
import com.nucleosis.www.appdrivertaxibigway.Constans.Constans;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.PointPolygono.PointDentroPolygono;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceListarServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appdrivertaxibigway.kmlPolygonos.KmlCreatorPolygono;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarServicio;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarStadosAdicionales;
import com.nucleosis.www.appdrivertaxibigway.ws.wsCalificarCliente;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerIdZonaIdDistrito;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerPrecioZonaDistrito;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by carlos.lopez on 06/05/2016.
 */
public class MapsConductorClienteServicio
        extends AppCompatActivity
                implements OnMapReadyCallback
        ,View.OnClickListener,
                GoogleApiClient.OnConnectionFailedListener {
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
    private JSONObject jsonConfiguraciones;
    private PlaceAutocompleteAdapter mAdapter;
    protected GoogleApiClient mGoogleApiClient;
    public static final String TAG = "SampleActivityBase";
    private KmlCreatorPolygono kmlCreatorPolygono;
    private JSONArray jsonServicioUnicoXid;
    private String AddresIncioCliente;
    private List<beansListaPolygono> listPolyGo;
    private  String jsonServiciosConductor;
    private String latitud_;
    private String longitud_;
    private String indMostrarCelularCliente;
    private String celularCliente;
    private String idCliente;
    private int idCalificacion=0;
    private static final LatLngBounds BOUNDS_LIMA = new LatLngBounds(
            new LatLng(-12.34202, -77.04231), new LatLng(-12.00103, -77.03269));
    //suereste  -12.34202, -77.04231-11.6818, -76.74636
//noreste -11.6818, -76.74636

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
        compR.cargar_toolbar(MAPS_CONDUCTOR_CLIENTE);
        progressDialog=new ProgressDialog(MapsConductorClienteServicio.this);
        fichero=new Fichero(MapsConductorClienteServicio.this);
        if(fichero.ExtraerConfiguraciones()!=null){
            jsonConfiguraciones=fichero.ExtraerConfiguraciones();
            Log.d("configuracion_",fichero.ExtraerConfiguraciones().toString());
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
         //CARGARMOS EL ESCUCHADOR PARA MODIFICAR LA DIRECCION INCIAL DEL CLIENTE
        CreaBroadcasReceiver();

        if(getIntent()!=null){
            idServicio=  getIntent().getStringExtra("idServicio");
            String zonaInicio=getIntent().getStringExtra("ZonaIncio");
            String zonaFin=getIntent().getStringExtra("ZonaFin");
            AddresIncioCliente=getIntent().getStringExtra("addresIncio");
            latitud_=getIntent().getStringExtra("latitudService");
            longitud_=getIntent().getStringExtra("longitudService");
            indMostrarCelularCliente=getIntent().getStringExtra("indMostrarCelularCliente");
            celularCliente=getIntent().getStringExtra("celularCliente");
            idCliente=getIntent().getStringExtra("idCliente");
            if(indMostrarCelularCliente.equals("1")){
                Log.d("celularCliente",celularCliente);
                compR.getBtnLLamarCliente().setVisibility(View.VISIBLE);
            }
            Log.d("zonAinicio_",AddresIncioCliente+"-->"+zonaInicio+"**"+zonaFin);

            new wsExtraerIdZonaIdDistrito(MapsConductorClienteServicio.this,zonaInicio, 3).execute();
            new wsExtraerIdZonaIdDistrito(MapsConductorClienteServicio.this,zonaFin, 4).execute();
           // new AsytaskServicioExtraido(MapsConductorClienteServicio.this,idServicio).execute();

            String stadoServicio=getIntent().getStringExtra("stadoService");
            Log.d("idServicio_stadoSevcio",idServicio+"-->"+stadoServicio);

            switch (Integer.parseInt(stadoServicio)){
                case 3:
                    compR.getBtnClienteEncontrado().setEnabled(false);
                    compR.getBtnAdicionales().setEnabled(true);
                    break;
                case 2:
                    compR.getBtnClienteEncontrado().setEnabled(true);
                    compR.getBtnAdicionales().setEnabled(false);
                    break;
            }

            mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_LIMA,
                    null);
        }

        preferencesDriver=new PreferencesDriver(MapsConductorClienteServicio.this);
        idDriver=preferencesDriver.OpenIdDriver();

        //LEVANTAR PROCESO EN SEGUNDO PLANO
        /*Intent intent=new Intent(MapsConductorClienteServicio.this, ServiceListarServiciosCreados.class);
        startService(intent);*/

    }

    private void CreaBroadcasReceiver(){

        IntentFilter filter=new IntentFilter(Utils.ACTION_RUN_SERVICE_3);
        ResponseReceiverListarServiciosCreados receiver=new
                ResponseReceiverListarServiciosCreados();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);


    }

    private class ResponseReceiverListarServiciosCreados extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Utils.ACTION_RUN_SERVICE_3:
                      //Toast.makeText(MapsConductorClienteServicio.this,intent.getStringExtra(Utils.EXTRA_MEMORY_3),Toast.LENGTH_LONG).show();
                    String  json=intent.getStringExtra(Utils.EXTRA_MEMORY_3);
                        if(json!=null){
                            jsonServiciosConductor=json;
                            try {
                                JSONArray jsonArray=new JSONArray(json);
                                for(int i=0; i<jsonArray.length();i++){
                                    if(idServicio.equals(jsonArray.getJSONObject(i).getString("idServicio"))){
                                        AddresIncioCliente=jsonArray.getJSONObject(i).getString("DireccionIncio");
                                        i=jsonArray.length();
                                    }
                                    //jsonArray.getJSONObject(i).getString("idServicio");
                                    //jsonArray.getJSONObject(i).getString("DireccionIncio")
                                }
                                // Log.d("x_array", String.valueOf(jsonArray.length()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    break;
                case Utils.ACTION_MEMORY_EXIT_3:
                    break;
            }
        }
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
     /*       int id = item.getItemId();
            if (id == R.id.menuAlert) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        Log.d("stado_","onStar");
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }
    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            Log.d("stado_","stop");
        }
        Log.d("stado_","stop");
        super.onStop();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onMapReady(final GoogleMap map) {

        kmlCreatorPolygono=new KmlCreatorPolygono(map,MapsConductorClienteServicio.this);
        listPolyGo= kmlCreatorPolygono.LeerKml();
        final double[] lat = new double[1];
        final double[] lon = new double[1];
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(MapsConductorClienteServicio.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now+
            // map.clear();
            ActivityCompat.requestPermissions(MapsConductorClienteServicio.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_1);

        } else {
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
                        if(latitud_!=null && longitud_!=null){
                            if(latitud_.length()!=0 && longitud_.length()!=0){
                                if(isNumeric(latitud_) && isNumeric(longitud_)){
                                    double latitudd=Double.parseDouble(latitud_);
                                    double longitudd=Double.parseDouble(longitud_);
                                    MarcadorServicio(map, latitudd, longitudd);
                                }else{
                                    Log.d("VerficicarValorNumerico","No es un numero"+"-->"+latitud_+"-->"+longitud_);
                                }

                            }
                        }

                        /*
                        JSONObject jsonCoordenadaClienteAddresRecojo=fichero.ExtraerCoordendaDirrecionIncioCliente();
                        try {
                            if(jsonCoordenadaClienteAddresRecojo.getString("latitud").length()!=0 &&
                                    jsonCoordenadaClienteAddresRecojo.getString("longitud").length()!=0){
                                double latitudd=Double.parseDouble(jsonCoordenadaClienteAddresRecojo.getString("latitud"));
                                double longitudd=Double.parseDouble(jsonCoordenadaClienteAddresRecojo.getString("longitud"));
                                MarcadorServicio(map, latitudd, longitudd);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        map.animateCamera(cam);
                    }
                }
            });
        }

    }
    private static boolean isNumeric(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_1) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
               /* Location myLocation =
                        LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);*/
            } else {
                // Permission was denied or request was cancelled
            }
        }
    }

    private void MarcadorServicio(GoogleMap mapa,double lat,double lon){
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(12);
        mapa.animateCamera(ZoomCam);
        final LatLng PERTH = new LatLng(lat, lon);
        final LatLng LatLonFin=new LatLng(lat,lon);
        // final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
        final Marker markerInicio = mapa.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Cliente")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cliente_2)));



     /*   CustomInfoWindow customInfoWindow = new CustomInfoWindow(MainActivity.this);
        mapa.setInfoWindowAdapter(customInfoWindow);*/
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener_1
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback_1);
        }
    };

    private AdapterView.OnItemClickListener mAutocompleteClickListener_2
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);

            final String placeId = item.getPlaceId();

            final CharSequence primaryText = item.getPrimaryText(null);
            final CharSequence fullText=item.getFullText(null);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback_2);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_1
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            Activity activity=MapsConductorClienteServicio.this;

            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            Log.d("location_",places.get(0).getLatLng().toString());
            final CharSequence thirdPartyAttribution = places.getAttributions();
            Log.d("latLong_adres", String.valueOf(place.getLatLng()));
            Log.i(TAG, "Place details received: " + place.getAddress());
          //  Log.d("distancia", String.valueOf(place.getLocale()));
            LatLng puntoEnZona=place.getLatLng();

            //OBTIENE EL ID_DE DE ZONA Y DE DISTRITO
            String zona =DeterminaZona(listPolyGo.size(),puntoEnZona);
            int casoEntrada=1;

            new wsExtraerIdZonaIdDistrito(activity,zona,casoEntrada).execute();
            Log.d("zonaAdres1",zona);
            places.release();
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_2
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            Activity activity=MapsConductorClienteServicio.this;
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            Log.d("location_",places.get(0).getLatLng().toString());
            final CharSequence thirdPartyAttribution = places.getAttributions();
            Log.d("latLong_adres", String.valueOf(place.getLatLng()));
            Log.i(TAG, "Place details received: " + place.getName());
            //  Log.d("distancia", String.valueOf(place.getLocale()));
            LatLng puntoEnZona=place.getLatLng();
            String zona=DeterminaZona(listPolyGo.size(),puntoEnZona);
            Log.d("zonaAdres2",zona);
            //OBTIENE EL ID_DE DE ZONA Y DE DISTRITO
            int casoEntrada=2;
//            Log.d("observandoFichero",fichero.ExtraerConfiguraciones().toString());
            new wsExtraerIdZonaIdDistrito(activity,zona,casoEntrada).execute();
            places.release();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDetalleServicio:
                 //   Toast.makeText(MapsConductorClienteServicio.this,"hola",Toast.LENGTH_LONG).show();
                if(jsonServiciosConductor!=null){
                    Log.d("jsonSerxx",jsonServiciosConductor.toString());
                }
                DetalleServicio();
                break;
            case R.id.btnAdicionales:
                    alertAdicionales();
                break;
            case R.id.btnClienteEncontrado:
                String mensaje1="Encontro al cliente ?";
                alert("3",mensaje1,9);
               Log.d("idTurnoVehiculo-->",preferencesDriver.ExtraerIdTurno()+"***"+preferencesDriver.ExtraerIdVehiculo());

             //   compR.getBtnServicioNoTerminado().setEnabled(false);
              //  compR.getBtnServicioTerminadoOk().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(true);
            //    compR.getBtnAdicionales().setEnabled(false);
                break;

            case R.id.btnSercioNoTerminado:
                String mensaje2="No termino el servicio ?";
                alert("5",mensaje2,10);

              //  compR.getBtnClienteEncontrado().setEnabled(false);
              //  compR.getBtnServicioTerminadoOk().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(true);
             //   compR.getBtnAdicionales().setEnabled(false);

                break;
            case R.id.btnServicioTerminadoOk:
                String mensaje3="Termino el servicio correctamente ?";
                alert("4",mensaje3,11);

             //   compR.getBtnClienteEncontrado().setEnabled(false);
            //   compR.getBtnServicioNoTerminado().setEnabled(false);
                compR.getBtnIrAServicios().setEnabled(true);
               // compR.getBtnAdicionales().setEnabled(false);

                break;
            case R.id.btnIrA_Servicios:
                Intent intent=new Intent(MapsConductorClienteServicio.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnLLamarCliente:
                Intent i = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:"+celularCliente)); //
                startActivity(i);
                break;
        }
    }
    private void DetalleServicio(){
        final JSONArray jsonServiciosConductor=fichero.ExtraerListaServiciosTomadoConductor();
        new AsyncTask<String, String, JSONObject>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(MapsConductorClienteServicio.this);
                progressDialog.setMessage("Cargando...");
                progressDialog.show();

            }

            @Override
            protected JSONObject doInBackground(String... params) {
                JSONObject JsonObjecServiceConductor=new JSONObject();
                if(jsonServiciosConductor!=null){
                    for(int i=0;i<jsonServiciosConductor.length();i++){
                        try {
                            if(idServicio.equals(jsonServiciosConductor.getJSONObject(i).getString("idServicio"))){

                                JsonObjecServiceConductor.put("importeServicio",jsonServiciosConductor.getJSONObject(i).getString("importeServicio"));
                                JsonObjecServiceConductor.put("DescripcionServicion",jsonServiciosConductor.getJSONObject(i).getString("DescripcionServicion"));
                                JsonObjecServiceConductor.put("Fecha",jsonServiciosConductor.getJSONObject(i).getString("Fecha"));
                                JsonObjecServiceConductor.put("Hora",jsonServiciosConductor.getJSONObject(i).getString("Hora"));
                                JsonObjecServiceConductor.put("nombreConductor",jsonServiciosConductor.getJSONObject(i).getString("nombreConductor"));
                                JsonObjecServiceConductor.put("nucCelularCliente",jsonServiciosConductor.getJSONObject(i).getString("nucCelularCliente"));
                                JsonObjecServiceConductor.put("importeAireAcondicionado",jsonServiciosConductor.getJSONObject(i).getString("importeAireAcondicionado"));

                                JsonObjecServiceConductor.put("importeGastosAdicionales",jsonServiciosConductor.getJSONObject(i).getString("importeGastosAdicionales"));
                                JsonObjecServiceConductor.put("numeroMinutoTiempoEspera",jsonServiciosConductor.getJSONObject(i).getString("numeroMinutoTiempoEspera"));
                                JsonObjecServiceConductor.put("importeTiempoEspera",jsonServiciosConductor.getJSONObject(i).getString("importeTiempoEspera"));
                                JsonObjecServiceConductor.put("importePeaje",jsonServiciosConductor.getJSONObject(i).getString("importePeaje"));
                                JsonObjecServiceConductor.put("nameDistritoInicio",jsonServiciosConductor.getJSONObject(i).getString("nameDistritoInicio"));
                                JsonObjecServiceConductor.put("DireccionIncio",jsonServiciosConductor.getJSONObject(i).getString("DireccionIncio"));
                                JsonObjecServiceConductor.put("nameDistritoFin",jsonServiciosConductor.getJSONObject(i).getString("nameDistritoFin"));
                                JsonObjecServiceConductor.put("direccionFinal",jsonServiciosConductor.getJSONObject(i).getString("direccionFinal"));
                                JsonObjecServiceConductor.put("numeroMovilTaxi",jsonServiciosConductor.getJSONObject(i).getString("numeroMovilTaxi"));
                                JsonObjecServiceConductor.put("nombreStadoServicio",jsonServiciosConductor.getJSONObject(i).getString("nombreStadoServicio"));
                                JsonObjecServiceConductor.put("idAutoTipoPidioCliente",jsonServiciosConductor.getJSONObject(i).getString("idAutoTipoPidioCliente"));
                                JsonObjecServiceConductor.put("desAutoTipoPidioCliente",jsonServiciosConductor.getJSONObject(i).getString("desAutoTipoPidioCliente"));


                                String importeGastoAdicional_=jsonServiciosConductor.getJSONObject(i).getString("importeGastosAdicionales");
                                double importeGastoAdicional=0.0;
                                String importeTipoAuto="0.00";
                                //1 VIP
                                //2 ECONOMICO
                                if(jsonServiciosConductor.getJSONObject(i).getString("idAutoTipoPidioCliente").equals("1")){

                                    JSONObject configuracionJson=fichero.ExtraerConfiguraciones();
                                    if(configuracionJson!=null){
                                        JsonObjecServiceConductor.put("importeTipoAuto",configuracionJson.getString("impAutoVip"));
                                        importeTipoAuto=configuracionJson.getString("impAutoVip");
                                    }else {
                                        JsonObjecServiceConductor.put("importeTipoAuto","0.00");
                                        importeTipoAuto="0.00";
                                    }

                                }else{
                                    JsonObjecServiceConductor.put("importeTipoAuto","0.00");
                                    importeTipoAuto="0.00";
                                }
                                if(importeGastoAdicional_!=null){
                                    if(Constans.isNumeric(importeGastoAdicional_)){
                                        importeGastoAdicional=Double.parseDouble(importeGastoAdicional_);
                                    }else{
                                        importeGastoAdicional=0.0;
                                    }

                                }

                                double sumaImportes=Double.parseDouble(jsonServiciosConductor.getJSONObject(i).getString("importeServicio"))+
                                        Double.parseDouble(jsonServiciosConductor.getJSONObject(i).getString("importeTiempoEspera"))+
                                        Double.parseDouble(jsonServiciosConductor.getJSONObject(i).getString("importeAireAcondicionado"))+
                                        Double.parseDouble(jsonServiciosConductor.getJSONObject(i).getString("importePeaje"))+
                                        Double.parseDouble(importeTipoAuto)+importeGastoAdicional;

                                JsonObjecServiceConductor.put("importeTotalServicio",String.valueOf(sumaImportes));

                                i=jsonServiciosConductor.length();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return JsonObjecServiceConductor;
            }

            @Override
            protected void onPostExecute(JSONObject jsonDetalle) {
                super.onPostExecute(jsonDetalle);
                progressDialog.dismiss();
                if(jsonDetalle!=null){

                    AlertDialog.Builder alerDialogoBilder = new AlertDialog.Builder(MapsConductorClienteServicio.this);

                    final View view = getLayoutInflater().inflate(R.layout.view_detalle_servicio_custom, null);
                    //  dialogo1.setTitle("Detalle del Servicio");
                    TextView txtDetalle=(TextView)view.findViewById(R.id.txtDetalleServicio);

                    alerDialogoBilder.setView(view);

                    String detalle = "";
                    AlertDialog alertDialog;
                    try {
                        detalle=
                                "<font color=\"#11aebf\"><bold>Fecha:&nbsp;</bold></font>"
                                        +"\t"+jsonDetalle.getString("Fecha")+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Hora:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("Hora")+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Distri Incio:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("nameDistritoInicio")+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Direccion Incio:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("DireccionIncio")+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Distri Fin:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("nameDistritoFin")+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Direccion Fin:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("direccionFinal")+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Num mint espera:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("numeroMinutoTiempoEspera")+"\t"+" min"+"<br>"
                                        +"<font color=\"#11aebf\"><bold>Tipo Servicio :&nbsp;</bold></font>"
                                        +"( "+jsonDetalle.getString("desAutoTipoPidioCliente")+" )"+"<br><br>"

                                        +"<font color=\"#11aebf\"><bold>Import Serv:&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importeServicio")+"<br>"

                                        +"<font color=\"#11aebf\"><bold>Import Aire:&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importeAireAcondicionado")+"<br>"

                                        +"<font color=\"#11aebf\"><bold>Import Tiem espera:&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importeTiempoEspera")+"<br>"

                                        +"<font color=\"#11aebf\"><bold>Import Peaje:&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importePeaje")+"<br>"

                                        +"<font color=\"#11aebf\"><bold>Import Tipo auto:&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importeTipoAuto")+"<br><br>"

                                        +"<font color=\"#11aebf\"><bold>Import adicional :&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importeGastosAdicionales")+"<br><br>"

                                        +"<font color=\"#11aebf\"><bold>Import Total:&nbsp;</bold></font>"
                                        +"S/."+jsonDetalle.getString("importeTotalServicio")+"<br><br>"
                                        //+"\n"+jsonDetalle.getString("numeroMovilTaxi")
                                        +"<font color=\"#11aebf\"><bold>Informacion Adicional :&nbsp;</bold></font>"
                                        +jsonDetalle.getString("DescripcionServicion")+"<br><br>"
                                        +"<font color=\"#11aebf\"><bold>Estado del servicio:&nbsp;</bold></font>"
                                        +jsonDetalle.getString("nombreStadoServicio");
                        txtDetalle.setText(Html.fromHtml(detalle));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //alerDialogoBilder.setMessage(detalle);
                    // alerDialogoBilder.setCancelable(false);
                    alerDialogoBilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    alertDialog = alerDialogoBilder.create();
                    alertDialog.show();
                }else{
                    Toast.makeText(MapsConductorClienteServicio.this,"No hay detalle para este servicio",Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
    @SuppressWarnings("deprecation")
    private void alertAdicionales(){
        final Activity activity=MapsConductorClienteServicio.this;
        final AlertDialog.Builder alertBilder = new AlertDialog.Builder(activity);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_alert_adicionales, null);

       /*Drawable imageRigth=getResources().getDrawable(R.drawable.ic_expand_more_black_24dp);
                btnAireAcondicionado1.setCompoundDrawablesWithIntrinsicBounds(null,null,imageRigth,null);*/
        final ImageView imagenCanceleAlertAdicionales=(ImageView)view.findViewById(R.id.ImgButtonCancelAlert);
         final Button btnAireAcondicionado1=(Button)view.findViewById(R.id.btnAireAcondicionado1);
         final Button btnAireAcondicionado2=(Button)view.findViewById(R.id.btnAireAcondicionado2);
         final Button btnEnviarAire=(Button)view.findViewById(R.id.btnEnviarAire);


        btnAireAcondicionado1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAireAcondicionado1.setVisibility(View.GONE);
                btnAireAcondicionado2.setVisibility(View.VISIBLE);
                btnEnviarAire.setVisibility(View.VISIBLE);
            }
        });

        btnAireAcondicionado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAireAcondicionado1.setVisibility(View.VISIBLE);
                btnAireAcondicionado2.setVisibility(View.GONE);
                btnEnviarAire.setVisibility(View.GONE);
            }
        });


        btnEnviarAire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                dialogo1.setTitle("Añadir Aire acondicionado");
                try {
                    dialogo1.setMessage("¿ Esta seguro de  realizar esta operacion ?"+"\n\n"+
                    "Costo:"+"\t\t"+"S/."+jsonConfiguraciones.getString("impAireAcondicionado"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnAireAcondicionado1.setVisibility(View.VISIBLE);
                        btnAireAcondicionado2.setVisibility(View.GONE);
                        btnEnviarAire.setVisibility(View.GONE);

                        try {
                            new wsActualizarServicio(
                                    activity,
                                    idServicio,
                                    "1",
                                     jsonConfiguraciones.getString("impAireAcondicionado"),
                                     "",//peaje
                                     "",//tiempo de espera
                                     "",//minutos tiempoEspera
                                     "",//importe servicio
                                     "",//idDistritoIncio
                                     "",//idZonaIncio
                                     "",//DireccionIncio
                                    "",//idDistritoFin
                                    "",//idZonaFin
                                    "",//DireccionFin
                                    "",//descricion pago extra
                                    "",  //IMPORTE EXTRAORDINARIO
                                    ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                                    ).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnAireAcondicionado1.setVisibility(View.VISIBLE);
                        btnAireAcondicionado2.setVisibility(View.GONE);
                        btnEnviarAire.setVisibility(View.GONE);
                    }
                });
                dialogo1.show();
            }
        });

        final Button btnPeaje1=(Button)view.findViewById(R.id.btnPeaje1);
        final Button btnPeaje2=(Button)view.findViewById(R.id.btnPeaje2);
        final Button btnEnviarPeaje=(Button)view.findViewById(R.id.btnEnviarPeaje);

        btnPeaje1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPeaje1.setVisibility(View.GONE);
                btnPeaje2.setVisibility(View.VISIBLE);
                btnEnviarPeaje.setVisibility(View.VISIBLE);
            }
        });

        btnPeaje2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPeaje2.setVisibility(View.GONE);
                btnPeaje1.setVisibility(View.VISIBLE);
                btnEnviarPeaje.setVisibility(View.GONE);
            }
        });

        btnEnviarPeaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                dialogo1.setTitle("Añadir Peaje");
                try {
                    dialogo1.setMessage("¿ Esta seguro de  realizar esta operacion ?"+"\n\n"+
                            "Costo:"+"\t\t"+"S/."+jsonConfiguraciones.getString("impServicioPeaje"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnPeaje2.setVisibility(View.GONE);
                        btnPeaje1.setVisibility(View.VISIBLE);
                        btnEnviarPeaje.setVisibility(View.GONE);
                        try {
                            new wsActualizarServicio(
                                    activity,
                                    idServicio,
                                    "",
                                    "",
                                    jsonConfiguraciones.getString("impServicioPeaje"),//peaje
                                    "",//tiempo de espera
                                    "",//minutos tiempoEspera
                                    "",//importe servicio
                                    "",//idDistritoIncio
                                    "",//idZonaIncio
                                    "",//DireccionIncio
                                    "",//idDistritoFin
                                    "",//idZonaFin
                                    "",//DireccionFin
                                    "",
                                    "",  //IMPORTE EXTRAORDINARIO
                                    ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                            ).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnPeaje2.setVisibility(View.GONE);
                        btnPeaje1.setVisibility(View.VISIBLE);
                        btnEnviarPeaje.setVisibility(View.GONE);
                    }
                });
                dialogo1.show();
            }
        });

        final Button btnTiempoEspera1=(Button)view.findViewById(R.id.btnTiempoEspera1);
        final Button btnTiempoEspera2=(Button)view.findViewById(R.id.btnTiempoEspera2);
        final Button btnEnviarTiempoEspera=(Button)view.findViewById(R.id.btnEnviarTiempoEspera);
        final EditText editTiempoEspera=(EditText)view.findViewById(R.id.editTiempoEspera);

        final double[] totalMinutosXtiempo = {0};

        final LinearLayout lienarTiempoEspera=(LinearLayout)view.findViewById(R.id.LinearContenedorTiempoEspera);
        btnTiempoEspera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTiempoEspera1.setVisibility(View.GONE);
                btnTiempoEspera2.setVisibility(View.VISIBLE);
                lienarTiempoEspera.setVisibility(View.VISIBLE);

            }
        });
        btnTiempoEspera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTiempoEspera1.setVisibility(View.VISIBLE);
                btnTiempoEspera2.setVisibility(View.GONE);
                lienarTiempoEspera.setVisibility(View.GONE);
            }
        });

        btnEnviarTiempoEspera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lblMinutosTiempo=editTiempoEspera.getText().toString().trim();
                Log.d("editTexttiempoA",lblMinutosTiempo);
                if(lblMinutosTiempo.length()!=0){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                    dialogo1.setTitle("Añadir Minutos de espera");
                    try {
                        double costoMinuto=Double.parseDouble(jsonConfiguraciones.getString("impMinutoEspera"));
                        Log.d("editTexttiempoB",lblMinutosTiempo);
                        totalMinutosXtiempo[0] =Double.parseDouble(lblMinutosTiempo)*costoMinuto;
                        dialogo1.setMessage("¿ Esta seguro de  realizar esta operacion ?"+"\n\n"+
                                "Costo:"+"\t\t"+"S/."+String.valueOf(totalMinutosXtiempo[0]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                            btnTiempoEspera1.setVisibility(View.VISIBLE);
                            btnTiempoEspera2.setVisibility(View.GONE);
                            lienarTiempoEspera.setVisibility(View.GONE);
                                new wsActualizarServicio(
                                        activity,
                                        idServicio,
                                        "",//indAireacondicionado
                                        "",//aireAcondicionado
                                        "",//peaje
                                        String.valueOf(totalMinutosXtiempo[0]),//importe tiempo espera
                                        lblMinutosTiempo,//minutos tiempoEspera
                                        "",//importe servicio
                                        "",//idDistritoIncio
                                        "",//idZonaIncio
                                        "",//DireccionIncio
                                        "",//idDistritoFin
                                        "",//idZonaFin
                                        "",//DireccionFin
                                        "",//descripcion pago extra
                                        "",  //IMPORTE EXTRAORDINARIO
                                        ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                                ).execute();

                        }
                    });
                    dialogo1.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            btnTiempoEspera1.setVisibility(View.VISIBLE);
                            btnTiempoEspera2.setVisibility(View.GONE);
                            lienarTiempoEspera.setVisibility(View.GONE);
                        }
                    });
                    dialogo1.show();
                }else {
                    Toast.makeText(activity,"Ingrese minutos",Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button btnGatosExtraordinario1=(Button)view.findViewById(R.id.btnPagoExtraOrdinadrio1);
        final Button btnGatosExtraordinario2=(Button)view.findViewById(R.id.btnPagoExtraOrdinadrio2);
        final LinearLayout linearPagosExtras=(LinearLayout)view.findViewById(R.id.linearPagosExtras);
        final EditText editPagosExras=(EditText)view.findViewById(R.id.editPagoExtraOrdinario);
        final Button btnEnviarPagosExras=(Button)view.findViewById(R.id.btnEnviarPagosExtraOrdinarios);

        final EditText editDescriopcionPagoExtra=(EditText)view.findViewById(R.id.editDescripcionPagoExtra);

        btnGatosExtraordinario1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGatosExtraordinario1.setVisibility(View.GONE);
                btnGatosExtraordinario2.setVisibility(View.VISIBLE);
                linearPagosExtras.setVisibility(View.VISIBLE);
            }
        });

        btnGatosExtraordinario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGatosExtraordinario1.setVisibility(View.VISIBLE);
                btnGatosExtraordinario2.setVisibility(View.GONE);
                linearPagosExtras.setVisibility(View.GONE);
                editPagosExras.setText("");
                editDescriopcionPagoExtra.setText("");
            }
        });

        btnEnviarPagosExras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPagosExras.getText().toString().trim().length()!=0){
                    String importePagoExtra=editPagosExras.getText().toString().trim();
                    String descripcion=editDescriopcionPagoExtra.getText().toString().trim();
                    if(!importePagoExtra.equals("0")){
                        new wsActualizarServicio(
                                activity,
                                idServicio,
                                "",//indAireacondicionado
                                "",//aireAcondicionado
                                "",//peaje
                                "",//importe tiempo espera
                                "",//minutos tiempoEspera
                                "",//importe servicio
                                "",//idDistritoIncio
                                "",//idZonaIncio
                                "",//DireccionIncio
                                "",//idDistritoFin
                                "",//idZonaFin
                                "",//DireccionFin
                                descripcion,
                                importePagoExtra,  //IMPORTE EXTRAORDINARIO
                                ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                        ).execute();
                    }

                }else {
                    Toast.makeText(MapsConductorClienteServicio.this,"Ingrese una cantidad",Toast.LENGTH_LONG).show();
                }
            }
        });

        final Button btnModificarRuta1=(Button)view.findViewById(R.id.btnModificarRuta1);
        final Button btnModificarRuta2=(Button)view.findViewById(R.id.btnModificarRuta2);
        final LinearLayout linearContenedor=(LinearLayout)view.findViewById(R.id.LinearContenedorRutaNueva);
        final Button btnEnviarRutaNueva=(Button)view.findViewById(R.id.btnEnviarNuevaRuta);
        final AutoCompleteTextView editDestino1=(AutoCompleteTextView)view.findViewById(R.id.autocompleteDireccion1);
        final AutoCompleteTextView editDestino2=(AutoCompleteTextView)view.findViewById(R.id.autocompleteDireccion2);



        editDestino1.setOnItemClickListener(mAutocompleteClickListener_1);
        editDestino1.setAdapter(mAdapter);

        editDestino2.setOnItemClickListener(mAutocompleteClickListener_2);
        editDestino2.setAdapter(mAdapter);
        btnModificarRuta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnModificarRuta1.setVisibility(View.GONE);
                btnModificarRuta2.setVisibility(View.VISIBLE);
                linearContenedor.setVisibility(View.VISIBLE);
                //SETENADO LA DIRECCION INCIAL DEL CLIENTE
                editDestino1.setText(AddresIncioCliente);
            }
        });

        btnModificarRuta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnModificarRuta1.setVisibility(View.VISIBLE);
                btnModificarRuta2.setVisibility(View.GONE);
                linearContenedor.setVisibility(View.GONE);
            }
        });

        alertBilder.setView(view);
        final AlertDialog alertAdicionales = alertBilder.create();

        btnEnviarRutaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addres1=editDestino1.getText().toString().trim();
                String addres2=editDestino2.getText().toString().trim();

                if(addres1.length()!=0 && addres2.length()!=0){

                    Log.d("dataComplex",editDestino1.getText().toString());
                    JSONObject jsonAdress1=new JSONObject();
                    try {
                        if(addres1.length()!=0){
                            jsonAdress1.put("addresOrigen",addres1);
                            fichero.InsertaDireccionIncio(jsonAdress1.toString());
                            Log.d("addreORIGIN",fichero.ExtraerDireccionIncio().toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //ENTRE EL PRECIO DE IR DE UNA ZONA A OTRA
                    JSONObject jsonAdress2=new JSONObject();
                    try {
                        if(addres2.length()!=0){
                            jsonAdress2.put("addresDestino",addres2);
                            fichero.InsertaDireccionFin(jsonAdress2.toString());
                            Log.d("addreORIGIN",fichero.ExtraerDireccionFin().toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Extrae el precio de zona por zona
                    new wsExtraerPrecioZonaDistrito(activity,alertAdicionales,idServicio).execute();
                }
            }
        });
        imagenCanceleAlertAdicionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAdicionales.dismiss();
            }
        });
        alertAdicionales.setCancelable(false);
        alertAdicionales.show();
    }

    private void alert(final String stadoServicio, final String mensaje, final int caseObjeto){
      final Activity activity=MapsConductorClienteServicio.this;
        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(activity);
        final View view1 = activity.getLayoutInflater().inflate(R.layout.view_alert_actualizar_stado, null);
        final String[] tipoPagoServicioFinal = {""};
        TextView lblTitutlo=(TextView) view1.findViewById(R.id.lblTxtTituloAlert) ;
        lblTitutlo.setTypeface(myTypeFace.openRounded_elegance());
        TextView lblMensaje=(TextView) view1.findViewById(R.id.lblMensajeAlert) ;
        lblMensaje.setText(mensaje);
        lblMensaje.setTypeface(myTypeFace.openRobotoLight());
        TextView lblTipoPago=(TextView)view1.findViewById(R.id.lblTipoPago);
        final CheckBox checkBoxPagoEfectivo=(CheckBox)view1.findViewById(R.id.checkPagoContado);
        final CheckBox checkBoxPagoCredito=(CheckBox)view1.findViewById(R.id.checkPagoCredito);

        final View viewLinea2=(View)view1.findViewById(R.id.viewLinea2);
        final CheckBox checkBoxCalificacionBuena=(CheckBox)view1.findViewById(R.id.checkCalificacionBueno);
        final CheckBox checkBoxCalificacionMalo=(CheckBox)view1.findViewById(R.id.checkCalificacionMalo);
        final TextView lblCalificacionCliente=(TextView)view1.findViewById(R.id.lblCalificacionCliente);
        View viewLinea=(View)view1.findViewById(R.id.viewLinea);
        EditText editMotivo=(EditText)view1.findViewById(R.id.editMotivo);
        final String motivo=editMotivo.getText().toString();
        Button  btnOk=(Button)view1.findViewById(R.id.btnOk);
        ImageView imageVieButtonCerrarAlert=(ImageView)view1.findViewById(R.id.ImgButtonCancelAlert);
        checkBoxPagoEfectivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tipoPagoServicioFinal[0] ="1";
                    checkBoxPagoCredito.setChecked(false);
                }


            }
        });

        checkBoxPagoCredito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tipoPagoServicioFinal[0] ="2";
                    checkBoxPagoEfectivo.setChecked(false);
                }


            }
        });

        checkBoxCalificacionBuena.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBoxCalificacionMalo.setChecked(false);
                    idCalificacion=3;
                }

            }
        });

        checkBoxCalificacionMalo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxCalificacionBuena.setChecked(false);
                idCalificacion=1;
            }
        });
        alertDialogBuilder1.setView(view1);
        // alertDialogBuilder.setTitle(R.string.addContacto);
        switch (caseObjeto){
            case 9:
                editMotivo.setVisibility(View.GONE);
                lblTipoPago.setVisibility(View.GONE);
                checkBoxPagoEfectivo.setVisibility(View.GONE);
                checkBoxPagoCredito.setVisibility(View.GONE);

                checkBoxCalificacionBuena.setVisibility(View.GONE);
                checkBoxCalificacionMalo.setVisibility(View.GONE);
                viewLinea.setVisibility(View.GONE);
                viewLinea2.setVisibility(View.GONE);

                lblCalificacionCliente.setVisibility(View.GONE);
                break;
            case 10:
                editMotivo.setVisibility(View.VISIBLE);
                lblTipoPago.setVisibility(View.GONE);
                checkBoxPagoEfectivo.setVisibility(View.GONE);
                checkBoxPagoCredito.setVisibility(View.GONE);
                checkBoxCalificacionBuena.setVisibility(View.GONE);
                checkBoxCalificacionMalo.setVisibility(View.GONE);
                viewLinea.setVisibility(View.GONE);
                viewLinea2.setVisibility(View.GONE);
                lblCalificacionCliente.setVisibility(View.GONE);
                break;
            case 11:
                editMotivo.setVisibility(View.GONE);
                lblTipoPago.setVisibility(View.VISIBLE);
                checkBoxPagoEfectivo.setVisibility(View.VISIBLE);
                checkBoxPagoCredito.setVisibility(View.VISIBLE);
                checkBoxCalificacionBuena.setVisibility(View.VISIBLE);
                checkBoxCalificacionMalo.setVisibility(View.VISIBLE);
                viewLinea.setVisibility(View.VISIBLE);
                viewLinea2.setVisibility(View.VISIBLE);
                lblCalificacionCliente.setVisibility(View.VISIBLE);
                break;
        }

        final AlertDialog alertDialog1 = alertDialogBuilder1.create();
        alertDialog1.setCancelable(false);
        imageVieButtonCerrarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                switch (caseObjeto){
                    case 9:
                      /*  compR.getBtnServicioNoTerminado().setEnabled(true);
                        compR.getBtnServicioTerminadoOk().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);*/
                        break;
                    case 10:
                       /* compR.getBtnClienteEncontrado().setEnabled(true);
                        compR.getBtnServicioTerminadoOk().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);*/
                        break;
                    case 11:
                        /*compR.getBtnClienteEncontrado().setEnabled(true);
                        compR.getBtnServicioNoTerminado().setEnabled(true);
                        compR.getBtnIrAServicios().setEnabled(true);
                        compR.getBtnAdicionales().setEnabled(true);*/
                        break;
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                if(motivo!=null){
                    if(caseObjeto==11){
                        //ACTUALIZAMOS EL TIPO DE PAGO DEL SERVICIO
                        Log.d("tipoPago_",tipoPagoServicioFinal[0] );
                        if(tipoPagoServicioFinal[0].length()!=0){
                            new wsCalificarCliente(activity,idCliente,idServicio,idCalificacion).execute();
                            new wsActualizarServicio(
                                    activity,
                                    idServicio,
                                    "",//ind aire acondicionado
                                    "",//inporteAireAcondicionado
                                    "",//peaje
                                    "",//tiempo de espera
                                    "",//minutos tiempoEspera
                                    "",//importe servicio
                                    "",//idDistritoIncio
                                    "",//idZonaIncio
                                    "",//DireccionIncio
                                    "",//idDistritoFin
                                    "",//idZonaFin
                                    "",//DireccionFin
                                    "",//descripcion de pago exraOordinario
                                    "",  //IMPORTE EXTRAORDINARIO
                                    tipoPagoServicioFinal[0]  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                            ).execute();
                            new wsActualizarStadosAdicionales(activity,idServicio,motivo,caseObjeto).execute(stadoServicio);

                        }else{
                            Toast.makeText(activity,"Elija un tipo de pago",Toast.LENGTH_LONG).show();
                        }


                    }else{
                        new wsActualizarStadosAdicionales(activity,idServicio,motivo,caseObjeto).execute(stadoServicio);

                    }
                    //ActualizarStadosServicio(stadoServicio,motivo,caseObjeto,activity);
                }else {
                    new wsActualizarStadosAdicionales(activity,idServicio,"",caseObjeto).execute(stadoServicio);
                  //  ActualizarStadosServicio(stadoServicio,"",caseObjeto,activity);
                }
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
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private String DeterminaZona(int totalPoligono,LatLng positoinDireccion){
        String zona="No hay datos";
        for (int x = 0; x < totalPoligono; x++) {
            //  Log.d("nameXXX", String.valueOf(x) + " " + listPolyGo.get(x).getNameZona() + "\t\t" + listPolyGo.get(x).getListLatLngPolygono().toString());
            boolean punto = PointDentroPolygono.containsLocation(positoinDireccion, listPolyGo.get(x).getListLatLngPolygono(), true);
            if (punto) {
                // Toast.makeText(MainActivity.this, "DENTRO", Toast.LENGTH_SHORT).show();
                Toast.makeText(MapsConductorClienteServicio.this, listPolyGo.get(x).getNameZona().toString(), Toast.LENGTH_LONG).show();
                Log.d("nombreZna", listPolyGo.get(x).getNameZona().toString());
                zona=listPolyGo.get(x).getNameZona().toString();
                x = totalPoligono;
            }
        }
        return zona;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();


        JSONObject jsonDataCoordenadaAddresClienteIncio=new JSONObject();

        try {
            jsonDataCoordenadaAddresClienteIncio.put("latitud","");
            jsonDataCoordenadaAddresClienteIncio.put("longitud","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fichero.InsertarCoordendaDirrecionIncioCliente(jsonDataCoordenadaAddresClienteIncio.toString());
        Log.d("ExtracLATLON",fichero.ExtraerCoordendaDirrecionIncioCliente().toString());

        /*Intent intent=new Intent(MapsConductorClienteServicio.this,ServiceListarServiciosCreados.class);
        stopService(intent);*/
    }
}
