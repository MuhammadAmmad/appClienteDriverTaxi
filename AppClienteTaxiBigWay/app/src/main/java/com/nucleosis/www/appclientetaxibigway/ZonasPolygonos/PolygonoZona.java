package com.nucleosis.www.appclientetaxibigway.ZonasPolygonos;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.nucleosis.www.appclientetaxibigway.beans.beansPolyGonos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.lopez on 22/04/2016.
 */
public class PolygonoZona {
    public static List <beansPolyGonos> ListPolygonos(){
        List<beansPolyGonos> listaPolygonos=new ArrayList<beansPolyGonos>();
        beansPolyGonos rowPolygono=null;
        int TOTAL_ZONAS=40;

        for (int x=1;x<=TOTAL_ZONAS;x++){
            rowPolygono=new beansPolyGonos();
            switch (x){
                case 1:
                    //ZONA AEROPUERTO
                    PolygonOptions zonaAeroPuerto = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.00451, -77.11482),
                                    new LatLng(-12.022, -77.10582),
                                    new LatLng(-12.03981, -77.09767),
                                    new LatLng(-12.04009, -77.09923),
                                    new LatLng(-12.04182, -77.10737),
                                    new LatLng(-12.00573, -77.12385),
                                    new LatLng(-12.00409, -77.12042),
                                    new LatLng(-12.00409, -77.12042),
                                    new LatLng(-12.00656, -77.1192),
                                    new LatLng(-12.00451, -77.11482)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaAeroPuerto);
                    rowPolygono.setNameZona("AEROPUERTO");
                    listaPolygonos.add(rowPolygono);
                    break;

                case 2:
                    PolygonOptions zonaCALLAO_IX = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.04009, -77.09923),
                                    new LatLng(-12.03981, -77.09767),
                                    new LatLng(-12.0368, -77.08297),
                                    new LatLng(-12.03923, -77.08261),
                                    new LatLng(-12.03892, -77.08035),
                                    new LatLng(-12.04664, -77.07962),
                                    new LatLng(-12.04833, -77.09841),
                                    new LatLng(-12.04009, -77.09923)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_IX);
                    rowPolygono.setNameZona("zonaCALLAO_IX");
                    listaPolygonos.add(rowPolygono);
                    break;
                case 3:
                    PolygonOptions zonaCALLAO_I = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.04833, -77.09841),
                                    new LatLng(-12.0537, -77.09805),
                                    new LatLng(-12.05178, -77.08743),
                                    new LatLng(-12.0499, -77.07655),
                                    new LatLng(-12.0465, -77.07673),
                                    new LatLng(-12.04664, -77.07962),
                                    new LatLng(-12.04833, -77.09841)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_I);
                    rowPolygono.setNameZona("zonaCALLAO_I");
                    listaPolygonos.add(rowPolygono);
                    break;

                case 4:
                    PolygonOptions zonaCALLAO_X = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.05522, -77.1058),
                                    new LatLng(-12.06334, -77.10451),
                                    new LatLng(-12.06158, -77.08816),
                                    new LatLng(-12.05178, -77.08743),
                                    new LatLng(-12.0537, -77.09805),
                                    new LatLng(-12.05522, -77.1058)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_X);
                    rowPolygono.setNameZona("zonaCALLAO_X");
                    listaPolygonos.add(rowPolygono);
                    break;
                case 5:
                    PolygonOptions zonaCALLAO_II = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.0537, -77.09805),
                                    new LatLng(-12.04833, -77.09841),
                                    new LatLng(-12.04009, -77.09923),
                                    new LatLng(-12.04182, -77.10737),
                                    new LatLng(-12.04198, -77.10853),
                                    new LatLng(-12.03774, -77.12724),
                                    new LatLng(-12.0446, -77.12668),
                                    new LatLng(-12.05866, -77.12419),
                                    new LatLng(-12.0585, -77.12149),
                                    new LatLng(-12.05522, -77.1058),
                                    new LatLng(-12.0537, -77.09805)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_II);
                    rowPolygono.setNameZona("zonaCALLAO_X");
                    listaPolygonos.add(rowPolygono);
                    break;

                case 6:
                    PolygonOptions zonaCALLAO_XI = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.06334, -77.10451),//
                                    new LatLng(-12.05522, -77.1058),//
                                    new LatLng(-12.0585, -77.12149),//
                                    new LatLng(-12.05866, -77.12419),//
                                    new LatLng(-12.05969, -77.13438),//
                                    new LatLng(-12.06798, -77.13294),//
                                    new LatLng(-12.0701, -77.1326),//
                                    new LatLng(-12.06334, -77.10451)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_XI);
                    rowPolygono.setNameZona("zonaCALLAO_XI");
                    listaPolygonos.add(rowPolygono);
                    break;

                case 7:
                    PolygonOptions zonaCALLAO_III = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.0701, -77.1326),//
                                    new LatLng(-12.06798, -77.13294),//
                                    new LatLng(-12.05969, -77.13438),//
                                    new LatLng(-12.05866, -77.12419),//
                                    new LatLng(-12.0446, -77.12668),//
                                    new LatLng(-12.04855, -77.13852),//
                                    new LatLng(-12.05107, -77.1405),//
                                    new LatLng(-12.05451, -77.14599),
                                    new LatLng(-12.06299, -77.15406),
                                    new LatLng(-12.06492, -77.15612),
                                    new LatLng(-12.0713, -77.16796),
                                    new LatLng(-12.07465, -77.1635),
                                    new LatLng(-12.06693, -77.15496),
                                    new LatLng(-12.06733, -77.14481),
                                    new LatLng(-12.0701, -77.1326)
                            ).visible(false)
                            .visible(false).fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_III);
                    rowPolygono.setNameZona("zonaCALLAO_III");
                    listaPolygonos.add(rowPolygono);
                    break;
                case 8:
                    PolygonOptions zonaCALLAO_IV = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.01783, -77.14166),
                                    new LatLng(-12.02332, -77.12878),
                                    new LatLng(-12.02958, -77.12732),
                                    new LatLng(-12.03774, -77.12724),
                                    new LatLng(-12.0446, -77.12668),
                                    new LatLng(-12.04855, -77.13852),
                                    new LatLng(-12.05107, -77.1405),
                                    new LatLng(-12.05451, -77.14599),
                                    new LatLng(-12.04779, -77.15612),
                                    new LatLng(-12.03252, -77.14865),
                                    new LatLng(-12.01783, -77.14166)
                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_IV);
                    rowPolygono.setNameZona("zonaCALLAO_IV");
                    listaPolygonos.add(rowPolygono);
                    break;
                case 9:
                    PolygonOptions zonaCALLAO_VII = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.04198, -77.10853),//
                                    new LatLng(-12.04182, -77.10737),
                                    new LatLng(-12.00573, -77.12385),
                                    new LatLng(-12.00409, -77.12042),
                                    new LatLng(-12.00656, -77.1192),
                                    new LatLng(-12.00451, -77.11482),
                                    new LatLng(-12.0012, -77.11679),
                                    new LatLng(-11.999, -77.122),
                                    new LatLng(-11.99323, -77.13595),
                                    new LatLng(-12.01783, -77.14166),
                                    new LatLng(-12.02332, -77.12878),
                                    new LatLng(-12.02958, -77.12732),
                                    new LatLng(-12.03774, -77.12724),
                                    new LatLng(-12.04198, -77.10853)

                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_VII);
                    rowPolygono.setNameZona("zonaCALLAO_VII");
                    listaPolygonos.add(rowPolygono);
                    break;
                case 10:
                    PolygonOptions zonaCALLAO_V = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.03981, -77.09767),//
                                    new LatLng(-12.01734, -77.08782),
                                    new LatLng(-12.01237, -77.09336),
                                    new LatLng(-12.01749, -77.09964),
                                    new LatLng(-12.022, -77.10582),
                                    new LatLng(-12.03981, -77.09767)

                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_V);
                    rowPolygono.setNameZona("zonaCALLAO_V");
                    listaPolygonos.add(rowPolygono);
                    break;
                case 11:
                    PolygonOptions zonaCALLAO_VI = new PolygonOptions().geodesic(true)
                            .add(new LatLng(-12.022, -77.10582),//
                                    new LatLng(-12.00451, -77.11482),
                                    new LatLng(-12.0012, -77.11679),
                                    new LatLng(-11.999, -77.122),
                                    new LatLng(-11.99717, -77.1128),
                                    new LatLng(-11.99866, -77.10271),
                                    new LatLng(-12.01237, -77.09336),
                                    new LatLng(-12.01749, -77.09964),
                                    new LatLng(-12.022, -77.10582)

                            ).visible(false)
                            .fillColor(Color.TRANSPARENT).strokeWidth(6);
                    rowPolygono.setPolygonOptions(zonaCALLAO_VI);
                    rowPolygono.setNameZona("zonaCALLAO_VI");
                    listaPolygonos.add(rowPolygono);
                    break;

                case 12:

                    break;

            }

        }



        return listaPolygonos;
    }
}
