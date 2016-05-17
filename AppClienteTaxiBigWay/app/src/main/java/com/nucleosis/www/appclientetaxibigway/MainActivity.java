package com.nucleosis.www.appclientetaxibigway;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nucleosis.www.appclientetaxibigway.Adpaters.CustomInfoWindow;
import com.nucleosis.www.appclientetaxibigway.Adpaters.PlaceAutocompleteAdapter;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.Fragments.FragmentDataClient;
import com.nucleosis.www.appclientetaxibigway.Fragments.FragmentMiUbicacion;
import com.nucleosis.www.appclientetaxibigway.Fragments.FragmentSolicitarServicioCliente;
import com.nucleosis.www.appclientetaxibigway.PointPolygono.PointDentroPolygono;
import com.nucleosis.www.appclientetaxibigway.RestMap.AddresRestmap;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appclientetaxibigway.beans.beansListaPolygono;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;
import com.nucleosis.www.appclientetaxibigway.kmlPolygonos.KmlCreatorPolygono;
import com.nucleosis.www.appclientetaxibigway.ws.wsEnviarLatLonClienteDireccionIncio;
import com.nucleosis.www.appclientetaxibigway.ws.wsExtraerConfiguracionAdicionales;
import com.nucleosis.www.appclientetaxibigway.ws.wsExtraerIdZonaIdDistrito;
import com.nucleosis.www.appclientetaxibigway.ws.wsExtraerPrecioZonaDistrito;
import com.nucleosis.www.appclientetaxibigway.ws.wsValidarHoraServicio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private MapFragment mapFragment;
    private ComponentesR compR;

    public static Activity MAIN_ACTIVITY;

    private MyTypeFace myTypeFace;
    private LinearLayout linearFragment;
    private PreferencesCliente preferencesCliente;
    private int sw = 0;
    private KmlCreatorPolygono kmlCreatorPolygono;
    private List<beansListaPolygono> listPolyGo;
    private String ZonaIncio="";
    private String ZonaFin="";
    private int hora,minuto,mYear, mMonth, mDay;
    private String Fecha;
    private Fichero fichero;
    private PlaceAutocompleteAdapter mAdapter;
    protected GoogleApiClient mGoogleApiClient;
    private long mLastClickTime = 0;
    public static final String TAG = "SampleActivityBase";
    private static final LatLngBounds BOUNDS_LIMA = new LatLngBounds(
            new LatLng(-12.34202, -77.04231), new LatLng(-12.00103, -77.03269));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MAIN_ACTIVITY = this;
        fichero=new Fichero(MainActivity.this);
        myTypeFace = new MyTypeFace(MainActivity.this);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        preferencesCliente = new PreferencesCliente(MainActivity.this);
        compR = new ComponentesR(MainActivity.this);
        compR.Contros_MainActivity(MAIN_ACTIVITY);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, compR.getDrawer(), compR.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        compR.getDrawer().setDrawerListener(toggle);
        toggle.syncState();
        linearFragment = (LinearLayout) findViewById(R.id.LinearFragment);
        if (compR.getNavigationView() != null) {
            setupNavigationDrawerContent(compR.getNavigationView());
        }
        setupNavigationDrawerContent(compR.getNavigationView());
        //API GOOGLE PLACE
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_LIMA,
                null);
        compR.getAutoCompletText1().setOnItemClickListener(mAutocompleteClickListener_1);
        compR.getAutoCompletText1().setAdapter(mAdapter);
        compR.getAutoCompletText2().setOnItemClickListener(mAutocompleteClickListener_2);
        compR.getAutoCompletText2().setAdapter(mAdapter);

        //OBTIENE INFORMACION DE COSTOS GENERALES   TIEMPO ESPERA/PEAJE/VIP/ECONOMICO/AIREACONDICIONADO
        //RUTA DE LA URL FOTO CONDUCTOR
        JSONObject jsonConfiguracion=fichero.ExtraerConfiguraciones();
        if(jsonConfiguracion==null){
            new wsExtraerConfiguracionAdicionales(MainActivity.this).execute();
        }

    }
    @Override
    public void onStart() {
        Log.d("stado_","onStar");
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                compR.getDrawer().openDrawer(GravityCompat.START);
                ImageView imagCliente = (ImageView) findViewById(R.id.imageCliente);
                TextView txtNameCliente = (TextView) findViewById(R.id.txtName);
                TextView txtEmailCliente = (TextView) findViewById(R.id.txtEmail);
                txtNameCliente.setTypeface(myTypeFace.openRobotoLight());
                txtEmailCliente.setTypeface(myTypeFace.openRobotoLight());

                String[] dataCliente = preferencesCliente.OpenDataCliente();
                txtNameCliente.setText(dataCliente[1]);
                txtEmailCliente.setText(dataCliente[5]);

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
                                linearFragment.setVisibility(View.VISIBLE);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                getSupportActionBar().setTitle("Mi Ubicacion");
                                setFragment(0);
                                return true;
                            case R.id.datosCliente:
                                menuItem.setChecked(true);
                                linearFragment.setVisibility(View.GONE);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                setFragment(1);
                                break;
                            case R.id.misServicios:
                                Intent intent =new Intent(MainActivity.this,ListaServicios.class);
                                startActivity(intent);
                                finish();
                                break;
                           /* case R.id.MenuPedirServicio:
                                linearFragment.setVisibility(View.GONE);
                                compR.getDrawer().closeDrawer(GravityCompat.START);
                                setFragment(1);
                                break;*/
                        }
                        return true;
                    }
                });
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onMapReady(final GoogleMap map) {

        kmlCreatorPolygono=new KmlCreatorPolygono(map,MainActivity.this);
        listPolyGo= kmlCreatorPolygono.LeerKml();
        // Log.d("sisePolygonos", String.valueOf(listPolyGo.get(0).getIdPoligono()));
        final double[] lat = new double[1];
        final double[] lon = new double[1];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location pos) {
                /// Toast.makeText(getApplicationContext(),String.valueOf(pos.getLatitude()+"****"+pos.getLongitude()),Toast.LENGTH_SHORT).show();
                lat[0] = pos.getLatitude();
                lon[0] = pos.getLongitude();
                LatLng latLngIncial=new LatLng(pos.getLatitude(),pos.getLongitude());
                if (sw == 0) {
                    sw = 1;
                    CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(new LatLng(
                            lat[0], lon[0]), 12);
                    ZonaIncio =DeterminaZona(listPolyGo.size(),latLngIncial);
                    new AddresRestmap(MainActivity.this,String.valueOf( pos.getLatitude()), String.valueOf(pos.getLongitude()), 1).execute();
                    new wsExtraerIdZonaIdDistrito(MainActivity.this,ZonaIncio,1).execute();
                    MarcardorIncio(map, lat[0], lon[0]);

                    map.animateCamera(cam);
                }

            }
        });
    }
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager =getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentMiUbicacion fragmentMiUbicacion=new
                        FragmentMiUbicacion();
                fragmentTransaction.replace(R.id.fragment, fragmentMiUbicacion);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                // FragmentHistoriaCarreras HistorialCarreras = new FragmentHistoriaCarreras();
                FragmentDataClient fragmentDataClient=new FragmentDataClient();
                // fragmentDataClient.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, fragmentDataClient);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                String addresIncial=compR.getAutoCompletText1().getText().toString();
                String addresFinal =compR.getAutoCompletText2().getText().toString();

                Bundle arguments = new Bundle();
                arguments.putString("ini_", addresIncial);
                arguments.putString("fin_",addresFinal);
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSolicitarServicioCliente fragmentSolicitarServicioCliente=new
                        FragmentSolicitarServicioCliente();
                FragmentSolicitarServicioCliente.newInstance(arguments);
                fragmentTransaction.replace(R.id.fragment, fragmentSolicitarServicioCliente);
                fragmentTransaction.commit();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);;
            finish();
            startActivity(i);
            sw=0;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPedirServicio:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            /*    String addresIncio=compR.getEditAddresss().getText().toString().trim();
                String addresFin=compR.getEditAddresssFinal().getText().toString().trim();*/
                String addresIncio=compR.getAutoCompletText1().getText().toString().trim();
                String addresFin=compR.getAutoCompletText2().getText().toString().trim();

                Log.d("incio***",String.valueOf(addresIncio.length())+"-->"+String.valueOf(addresFin.length()));
                String msnServicioTaxi=getResources().getString(R.string.NoServicio);
                if(addresIncio.length()!=0 && addresFin.length()!=0 ){
                    if(ZonaIncio.length()!=0 && ZonaFin.length()!=0){
                        Toast.makeText(MainActivity.this,ZonaIncio+"***"+ZonaFin,Toast.LENGTH_LONG).show();
                        fichero.InsertarDireccionIncioFin(addresIncio,addresFin);
                        new wsExtraerPrecioZonaDistrito(
                                MainActivity.this,
                                fichero.ExtraerZonaIdDistrito_Origen(),
                                fichero.ExtraerZonaIdDistrito_Destino()).execute();
                        //AlertPedirServicio();
                    }else{
                        Toast.makeText(MainActivity.this,msnServicioTaxi,Toast.LENGTH_LONG).show();
                    }
                    // linearFragment.setVisibility(View.GONE);
                    //compR.getDrawer().closeDrawer(GravityCompat.START);
                    // setFragment(2);
                }else{
                    Toast.makeText(MainActivity.this,"Ingrese Direccion de inicio y destino",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
    private void MarcardorIncio(final GoogleMap mapa, final double lat,double lon){
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(12);
        mapa.animateCamera(ZoomCam);
        final LatLng PERTH = new LatLng(lat, lon);
        final LatLng LatLonFin=new LatLng(lat+0.0060,lon+0.0390);
        // final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
        final Marker markerInicio = mapa.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Arrastre a una ubicacion de inicio")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cliente))
                .draggable(true));

        final Marker markerFin =mapa.addMarker(new MarkerOptions()
                .position(LatLonFin)
                .title("Ubique una direccion destino")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_final))
                .draggable(true));

        CustomInfoWindow customInfoWindow = new CustomInfoWindow(MainActivity.this);
        mapa.setInfoWindowAdapter(customInfoWindow);

        /*mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MainActivity.this, marker.getId(), Toast.LENGTH_SHORT).show();

            }
        });*/
        mapa.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(final Marker marker) {
                String id = marker.getId();
                final LatLng latLng = marker.getPosition();
                String lat = String.valueOf(latLng.latitude);
                String lng = String.valueOf(latLng.longitude);

                if (marker.getId().equals("m0")) {
                    //  Log.d("coordenadas", String.valueOf(lat) + "--->" + String.valueOf(lng));
                    int casoMarker = 1;
                    new AddresRestmap(MainActivity.this, lat, lng, casoMarker).execute();
                    final int sisePoligonos = listPolyGo.size();
                    ZonaIncio=  DeterminaZona(sisePoligonos,marker.getPosition());
                    LatLng coordenada=marker.getPosition();
                    Log.d("latitudLongidu_",String.valueOf(coordenada));
                    /*coordenada.latitude;
                    coordenada.longitude;*/
                    JSONObject jsonCoordenadaIncioAddres=new JSONObject();
                    try {
                        jsonCoordenadaIncioAddres.put("latitud",String.valueOf(coordenada.latitude));
                        jsonCoordenadaIncioAddres.put("longitud",String.valueOf(coordenada.longitude));
                        fichero.InsertarCoordendaDirrecionIncio(jsonCoordenadaIncioAddres.toString());
                        new wsEnviarLatLonClienteDireccionIncio(MainActivity.this).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new wsExtraerIdZonaIdDistrito(MainActivity.this,ZonaIncio,1).execute();

                } else if (marker.getId().equals("m1")) {
                    int casoMarker = 2;
                    new AddresRestmap(MainActivity.this, lat, lng, casoMarker).execute();
                    final int sisePoligonos = listPolyGo.size();
                    ZonaFin =  DeterminaZona(sisePoligonos,marker.getPosition());
                    new wsExtraerIdZonaIdDistrito(MainActivity.this,ZonaFin,2).execute();
                }
            }
        });
    }

    private String DeterminaZona(int totalPoligono,LatLng positionMarker){
        String zona="";
        for (int x = 0; x < totalPoligono; x++) {
            //  Log.d("nameXXX", String.valueOf(x) + " " + listPolyGo.get(x).getNameZona() + "\t\t" + listPolyGo.get(x).getListLatLngPolygono().toString());
            boolean punto = PointDentroPolygono.containsLocation(positionMarker, listPolyGo.get(x).getListLatLngPolygono(), true);
            if (punto) {
                // Toast.makeText(MainActivity.this, "DENTRO", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, listPolyGo.get(x).getNameZona().toString(), Toast.LENGTH_LONG).show();
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
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            Log.d("stado_","stop");
        }
        Log.d("stado_","stop");
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

           /* Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();*/
            //  Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
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

          /*  Toast.makeText(getApplicationContext(), "Clicked: " + fullText,
                    Toast.LENGTH_SHORT).show();*/
            //  Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_1
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            Activity activity=MainActivity.this;

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
            ZonaIncio =DeterminaZona(listPolyGo.size(),puntoEnZona);
            new wsExtraerIdZonaIdDistrito(MainActivity.this,ZonaIncio,1).execute();
            int casoEntrada=1;

         //   new wsExtraerIdZonaIdDistrito(activity,zona,casoEntrada).execute();
            Log.d("zonaAdres1",ZonaIncio);
            places.release();
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_2
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            Activity activity=MainActivity.this;
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
            ZonaFin=DeterminaZona(listPolyGo.size(),puntoEnZona);
            Log.d("zonaAdres2",ZonaFin);
            new wsExtraerIdZonaIdDistrito(MainActivity.this,ZonaFin,2).execute();
            //OBTIENE EL ID_DE DE ZONA Y DE DISTRITO
            int casoEntrada=2;
//            Log.d("observandoFichero",fichero.ExtraerConfiguraciones().toString());
          //  new wsExtraerIdZonaIdDistrito(activity,zona,casoEntrada).execute();
            places.release();
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
