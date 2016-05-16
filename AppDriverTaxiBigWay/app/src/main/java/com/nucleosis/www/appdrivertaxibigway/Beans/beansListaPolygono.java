package com.nucleosis.www.appdrivertaxibigway.Beans;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

/**
 * Created by karlos on 24/04/2016.
 */
public class beansListaPolygono {
    private String NameZona;
    private List<LatLng> ListLatLngPolygono;
    private PolygonOptions polygonOptions;
    private String idPoligono;
    public String getNameZona() {
        return NameZona;
    }

    public void setNameZona(String nameZona) {
        NameZona = nameZona;
    }

    public List<LatLng> getListLatLngPolygono() {
        return ListLatLngPolygono;
    }

    public void setListLatLngPolygono(List<LatLng> listLatLngPolygono) {
        ListLatLngPolygono = listLatLngPolygono;
    }

    public PolygonOptions getPolygonOptions() {
        return polygonOptions;
    }

    public void setPolygonOptions(PolygonOptions polygonOptions) {
        this.polygonOptions = polygonOptions;
    }

    public String getIdPoligono() {
        return idPoligono;
    }

    public void setIdPoligono(String idPoligono) {
        this.idPoligono = idPoligono;
    }
}
