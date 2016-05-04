package com.google.maps.android.utils.demo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class KmlDemoActivity extends BaseDemoActivity {

    private GoogleMap mMap;
    private KmlLayer kmlLayer;

    protected int getLayoutId() {
        return R.layout.kml_demo;
    }

    public void startDemo () {
        try {
            mMap = getMap();
            retrieveFileFromResource();
           // retrieveFileFromUrl();
        } catch (Exception e) {
            Log.e("Exception caught", e.toString());
        }
    }

    private void retrieveFileFromResource() {
        try {
            KmlLayer kmlLayer = new KmlLayer(mMap, R.raw.map_bit_way_taxi, getApplicationContext());
            kmlLayer.addLayerToMap();
            moveCameraToKml(kmlLayer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void retrieveFileFromUrl() {
        String url = "https://kml-samples.googlecode.com/svn/trunk/" +
                "morekml/Polygons/Polygons.Google_Campus.kml";
        new DownloadKmlFile(url).execute();
    }

    private void moveCameraToKml(KmlLayer kmlLayer) {
        //Retrieve the first container in the KML layer
        //Recuperar el primer contenedor en la capa KML
        KmlContainer container = kmlLayer.getContainers().iterator().next();
        Log.d("nameFile",container.getProperty("name").toString()+"\n"+container.getProperties().toString());

        //Retrieve a nested container within the first container
       // Recuperar un contenedor anidado dentro del primer contenedor
        container = container.getContainers().iterator().next();
        //Log.d("nodo",container.getProperty("name"));
        //Retrieve the first placemark in the nested container
        //Recuperar la primera posición en el contenedor anidado
        for (KmlPlacemark placemark1 : container.getPlacemarks()) {
            //Log.d("PolygonoName",placemark1.getProperty("name"));
            KmlPolygon polygon=(KmlPolygon)placemark1.getGeometry();
            ArrayList<LatLng> lista= polygon.getOuterBoundaryCoordinates();
            Log.d("CoordendasPoligono",placemark1.getProperty("name")+"==="+lista.toString());
        }
        KmlPlacemark placemark = container.getPlacemarks().iterator().next();
       // Log.d("namePolygono",placemark.getProperty("name"));
        //Retrieve a polygon object in a placemark
        //Recuperar un objeto polígono en una marca de posición
        KmlPolygon polygon = (KmlPolygon) placemark.getGeometry();
        //Create LatLngBounds of the outer coordinates of the polygon
        ArrayList<LatLng> list=polygon.getOuterBoundaryCoordinates();

       // Log.d("numeroPolygonos", list.toString());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon.getOuterBoundaryCoordinates()) {
            builder.include(latLng);
        }

       // Log.d("ListaPoligonos", String.valueOf(list.size());
        getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 1));
    }

    private void accessContainers(Iterable<KmlContainer> containers) {

    }

    private class DownloadKmlFile extends AsyncTask<String, Void, byte[]> {
        private final String mUrl;

        public DownloadKmlFile(String url) {
            mUrl = url;
        }

        protected byte[] doInBackground(String... params) {
            try {
                InputStream is =  new URL(mUrl).openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(byte[] byteArr) {
            try {
                kmlLayer = new KmlLayer(mMap, new ByteArrayInputStream(byteArr),
                        getApplicationContext());
                kmlLayer.addLayerToMap();
                moveCameraToKml(kmlLayer);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
