package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.R;
/**
 * Created by karlos on 02/04/2016.
 */
public class FragmentMapUbicacion extends Fragment implements OnMapReadyCallback {
    LatLng mapCenter;
    MapFragment mapFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public FragmentMapUbicacion() { }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Mi Ubicacion");
        View rootView = inflater.inflate(R.layout.view_map_mi_ubicacion_actual, container, false);
        mapFragment = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final double[] lat = new double[1];
        final double[] lon = new double[1];
        Log.d("Listner_","espera de coordenadas");
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.d("latLog_",String.valueOf(location.getLatitude())+"***"+String.valueOf(location.getLongitude()));
                lat[0]=location.getLatitude();
                lon[0]=location.getLongitude();
                CameraUpdate cam = CameraUpdateFactory.newLatLng(new LatLng(
                        lat[0],lon[0]));
                googleMap.moveCamera(cam);
            }
        });
        mapCenter = new LatLng(lat[0], lon[0]);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 16));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(16)
                .bearing(90)
                .build();
        // Animate the change in camera view over 2 seconds
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);

    }


}
