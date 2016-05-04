package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.ws.UpdateLocationDriver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by carlos.lopez on 04/04/2016.
 */
public class locationDriver extends Service implements LocationListener {
    private TimerTask timerTask;
    private LocationListener locationListener;
    private String[]LatLong=new String[2];
    private int sw=0;
    @Override
    public void onCreate() {
        super.onCreate();
/*  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
            return;
        }*/
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        updateLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("servicio---->", "servicio Inciado");
        final Timer timer = new Timer();

        timerTask =new TimerTask() {
            int x=0;
            @Override
            public void run() {
                x++;
                Log.d("incio_timer", "iniciando........" + String.valueOf(x));
                new UpdateLocationDriver(locationDriver.this,LatLong[0],
                        LatLong[1])
                        .execute();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.d("servicio---->","servicio terminado");
        timerTask.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    protected void updateLocation(Location location){
        if(location!=null){
            LatLong[0]=String.valueOf(location.getLatitude());
            LatLong[1]=String.valueOf(location.getLongitude());
            if(sw==0){
                SharedPreferences prefs =
                        getSharedPreferences("miLocation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("latitud",  LatLong[0]);
                editor.putString("longitud", LatLong[1]);
                editor.commit();
                sw=1;
            }

        }

    }
}
