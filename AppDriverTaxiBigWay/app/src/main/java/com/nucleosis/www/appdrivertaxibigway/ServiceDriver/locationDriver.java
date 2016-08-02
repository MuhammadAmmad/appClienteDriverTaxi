package com.nucleosis.www.appdrivertaxibigway.ServiceDriver;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.User;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.ws.UpdateLocationDriver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.nucleosis.www.appdrivertaxibigway.Constans.Utils.LOGGIN_USUARIO;

/**
 * Created by carlos.lopez on 04/04/2016.
 */
public class locationDriver extends Service
        implements LocationListener {
    private TimerTask timerTask;
    private LocationListener locationListener;
    private String[] LatLong = new String[2];
    private int sw = 0;
    private Activity activity;
    private Context context;
    private Fichero fichero;
    @Override
    public void onCreate() {
        super.onCreate();
        context=(Context)this;
        fichero=new Fichero(context);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now+
            activity=(Activity)context;
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Utils.MY_PERMISSION_ACCESS_COURSE_LOCATION_1);
        } else {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            updateLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Utils.TIME_LOCATION_DRIVER, 0, this);

        }

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("servicio_x---->", "servicio Inciado");
        final Timer timer = new Timer();

        timerTask =new TimerTask() {
            int x=0;
            @Override
            public void run() {
                x++;
                Log.d("incio_timer", "iniciando........" + String.valueOf(x));
                JSONObject jsonCoordenadas=new JSONObject();
                try {
                    jsonCoordenadas.put("latitud",LatLong[0]);
                    jsonCoordenadas.put("longitud",LatLong[1]);
                    fichero.InsertarCoordendaConductor(jsonCoordenadas.toString());
                    /*if(fichero.ExtraerCoordendaConductor()!=null){
                        Log.d("coordernadas_",fichero.ExtraerCoordendaConductor().toString());
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new UpdateLocationDriver(locationDriver.this,LatLong[0],
                        LatLong[1])
                        .execute();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, Utils.TIME_LOCATION_DRIVER);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.d("servisLocation->","servicio terminado");
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
     //   Log.d("latLon_x",String.valueOf(location.getLatitude())+"-->"+String.valueOf(location.getLongitude()));
        if(location!=null){
           // Log.d("latLon_x",String.valueOf(location.getLatitude())+String.valueOf(location.getLongitude()));
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
