package com.nucleosis.www.appclientetaxibigway;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
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
import com.nucleosis.www.appclientetaxibigway.Constantes.Utils;
import com.nucleosis.www.appclientetaxibigway.ServiceBackground.PosicionConductor;

/**
 * Created by carlos.lopez on 04/05/2016.
 */
public class MapsClienteConductorServicio extends AppCompatActivity
        implements OnMapReadyCallback {
    private MapFragment mapFragment;
    private int sw = 0;
    TextView lblCoordenada;
    Button btnCoordenada, btnStopCoordenada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_maps_cliente_conductor_servicio);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lblCoordenada = (TextView) findViewById(R.id.lblCoordenada);
        btnCoordenada = (Button) findViewById(R.id.btnCoordenadas);
        btnStopCoordenada = (Button) findViewById(R.id.btnStopCoordenadas);
        btnCoordenada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MapsClienteConductorServicio.this,"hola",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MapsClienteConductorServicio.this, PosicionConductor.class);
                startService(intent);
                btnCoordenada.setVisibility(View.GONE);
                btnStopCoordenada.setVisibility(View.VISIBLE);
            }
        });
        btnStopCoordenada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCoordenada.setVisibility(View.VISIBLE);
                btnStopCoordenada.setVisibility(View.GONE);
                Intent intent = new Intent(MapsClienteConductorServicio.this, PosicionConductor.class);
                stopService(intent);
            }
        });
        IntentFilter filter = new IntentFilter(Utils.ACTION_RUN_SERVICE);
        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver = new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);
    }

    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Utils.ACTION_RUN_SERVICE:
                    String[] data = intent.getStringArrayExtra(Utils.EXTRA_MEMORY);
                    lblCoordenada.setText(data[0] + "-->" + data[1]);
                    break;

                case Utils.ACTION_MEMORY_EXIT:
                    lblCoordenada.setText("coordendas");
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
}
