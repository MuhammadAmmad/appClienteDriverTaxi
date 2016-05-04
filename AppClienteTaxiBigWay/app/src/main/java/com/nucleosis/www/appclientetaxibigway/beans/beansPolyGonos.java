package com.nucleosis.www.appclientetaxibigway.beans;

import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by carlos.lopez on 22/04/2016.
 */
public class beansPolyGonos {
    public PolygonOptions polygonOptions;
    public String NameZona;
    public String idZona;
    public String idDistrito;
    public PolygonOptions getPolygonOptions() {

        return polygonOptions;
    }

    public void setPolygonOptions(PolygonOptions polygonOptions) {
        this.polygonOptions = polygonOptions;
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(String idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getNameZona() {
        return NameZona;
    }

    public void setNameZona(String nameZona) {
        NameZona = nameZona;
    }

    @Override
    public String toString() {
        return NameZona;
    }
}
