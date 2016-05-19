package com.nucleosis.www.appclientetaxibigway.kmlPolygonos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.beans.beansListaPolygono;
import com.nucleosis.www.appclientetaxibigway.beans.beansPolyGonos;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 24/04/2016.
 */
public class KmlCreatorPolygono {
    private Context context;
    private GoogleMap mMap;
    private List<beansListaPolygono> listaPolygonos;
   private beansListaPolygono rowPolygono=null;

 /*   private List<beansPolyGonos> ListaPolyGonos;
    private beansPolyGonos rowPolygono=null;*/
    public KmlCreatorPolygono(GoogleMap mMap, Context context) {
        this.mMap = mMap;
        this.context = context;
        listaPolygonos=new ArrayList<beansListaPolygono>();
      //  listaPolygonos=new ArrayList<beansListaPolygono>();
        //LeerKml();
    }

    public List<beansListaPolygono> LeerKml(){
        List<beansListaPolygono> LisKML=null;
        try {
            KmlLayer kmlLayer = new KmlLayer(mMap, R.raw.zonas_map_bigway, context);
           // kmlLayer.addLayerToMap();
            LisKML= ParsearKml(kmlLayer);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LisKML;
    }

    private List<beansListaPolygono>  ParsearKml(KmlLayer kmlLayer){

        List<beansListaPolygono> lisKml=new ArrayList<beansListaPolygono>();
        KmlContainer container = kmlLayer.getContainers().iterator().next();
      ///  Log.d("xxNameFile", container.getProperty("name").toString() + "\n" + container.getProperties().toString());
        //Retrieve a nested container within the first container
        // Recuperar un contenedor anidado dentro del primer contenedor
        container = container.getContainers().iterator().next();
        //Log.d("nodo",container.getProperty("name"));
        //Retrieve the first placemark in the nested container
        //Recuperar la primera posición en el contenedor anidado
        for (KmlPlacemark placemark1 : container.getPlacemarks()) {
            rowPolygono=new beansListaPolygono();
        //  rowPolygono=new beansPolyGonos();
            //Log.d("PolygonoName",placemark1.getProperty("name"));
            KmlPolygon polygon=(KmlPolygon)placemark1.getGeometry();
            ArrayList<LatLng> lista= polygon.getOuterBoundaryCoordinates();
            Iterable<LatLng> points=lista;
        //    Log.d("Iterablex--->", points.toString());
            PolygonOptions polygonOptions=new PolygonOptions().geodesic(true)
                    .addAll(points)
                    .clickable(true)
                    .strokeWidth(0f);
            mMap.addPolygon(polygonOptions);
            rowPolygono.setPolygonOptions(polygonOptions);
            rowPolygono.setNameZona(placemark1.getProperty("name"));
            rowPolygono.setListLatLngPolygono(lista);
            lisKml.add(rowPolygono);
          //  rowPolygono.setNameZona(placemark1.getProperty("name"));
          //  rowPolygono.setListLatLngPolygono(lista);
          //  lisKml.add(rowPolygono);
       //     Log.d("CoordendasPoligono",placemark1.getProperty("name")+"==="+lista.toString());
        }

       /* mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
             //   Toast.makeText(context, polygon.getId(), Toast.LENGTH_LONG).show();


            }
        });*/


        return lisKml;
    }


}
