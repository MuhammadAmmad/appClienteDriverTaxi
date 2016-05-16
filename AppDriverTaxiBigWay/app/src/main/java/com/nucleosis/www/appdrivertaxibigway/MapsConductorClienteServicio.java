package com.nucleosis.www.appdrivertaxibigway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.nucleosis.www.appdrivertaxibigway.Beans.beansListaPolygono;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.PointPolygono.PointDentroPolygono;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceListarServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appdrivertaxibigway.kmlPolygonos.KmlCreatorPolygono;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarServicio;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarStadosAdicionales;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerIdZonaIdDistrito;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerPrecioZonaDistrito;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by carlos.lopez on 06/05/2016.
 */
public class MapsConductorClienteServicio extends AppCompatActivity implements OnMapReadyCallback
        ,View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
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
    private List<beansListaPolygono> listPolyGo;
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


        if(getIntent()!=null){
            idServicio=  getIntent().getStringExtra("idServicio");
            String zonaInicio=getIntent().getStringExtra("idZonaIncio");
            String zonaFin=getIntent().getStringExtra("idZonaFin");
            Log.d("zonAinicio_",zonaInicio+"**"+zonaFin);

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
                default:
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
        Intent intent=new Intent(MapsConductorClienteServicio.this, ServiceListarServiciosCreados.class);
        startService(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       /* if (id == R.id.Historial_ubi) {
            return true;
        }    */
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
                    }
                    map.animateCamera(cam);
                }
            }
        });
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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_ubicacion)));



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
            case R.id.btnAdicionales:
                    alertAdicionales();
                break;

            case R.id.btnClienteEncontrado:
                String mensaje1="Encontro al cliente ?";
                alert("3",mensaje1,9);
               Log.d("idTurnoVehiculo-->",preferencesDriver.ExtraerIdTurno()+"***"+preferencesDriver.ExtraerIdVehiculo());

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
                                    ""//DireccionFin
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
                                    ""//DireccionFin
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
                                        ""//DireccionFin
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
        alertAdicionales.show();
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

        EditText editMotivo=(EditText)view1.findViewById(R.id.editMotivo);
        final String motivo=editMotivo.getText().toString();
        Button  btnOk=(Button)view1.findViewById(R.id.btnOk);
        ImageView imageVieButtonCerrarAlert=(ImageView)view1.findViewById(R.id.ImgButtonCancelAlert);
        alertDialogBuilder1.setView(view1);
        // alertDialogBuilder.setTitle(R.string.addContacto);
        switch (caseObjeto){
            case 9:
                editMotivo.setVisibility(View.GONE);
                break;
            case 10:
                editMotivo.setVisibility(View.VISIBLE);
                break;
            case 11:
                editMotivo.setVisibility(View.GONE);
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
                alertDialog1.dismiss();
                if(motivo!=null){
                    new wsActualizarStadosAdicionales(activity,idServicio,motivo,caseObjeto).execute(stadoServicio);
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

        Intent intent=new Intent(MapsConductorClienteServicio.this,ServiceListarServiciosCreados.class);
        stopService(intent);
    }
}
