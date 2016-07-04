package com.nucleosis.www.appdrivertaxibigway.Constans;

import android.content.Context;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by carlos.lopez on 04/04/2016.
 */
public class Constans {
    private static final String KEY="qualityinfosolutions";

    public static String getKEY() {
        return KEY;
    }

    public static boolean isNumeric(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public static String DetalleServicioLista(Context context,
                                       List<beansHistorialServiciosCreados> ListaServiciosCreados,
                                       int posicion_){
        Fichero fichero =new Fichero(context);
        JSONObject jsonConfiguracion = fichero.ExtraerConfiguraciones();
        String importTipoAutoSolicitoCliente = "0.0";
        Double sumaImportesServicio = 0.0;
        if (jsonConfiguracion != null) {
            Log.d("configu_", jsonConfiguracion.toString());
            if (ListaServiciosCreados.get(posicion_).getIdAutoTipoPidioCliente().equals("1")) {
                try {
                    importTipoAutoSolicitoCliente = jsonConfiguracion.getString("impAutoVip");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                importTipoAutoSolicitoCliente = "0.0";
            }
        }
        sumaImportesServicio = Double.parseDouble(importTipoAutoSolicitoCliente) +
                Double.parseDouble(ListaServiciosCreados.get(posicion_).getImporteAireAcondicionado().toString()) +
                Double.parseDouble(ListaServiciosCreados.get(posicion_).getImportePeaje().toString()) +
                Double.parseDouble(ListaServiciosCreados.get(posicion_).getImporteServicio().toString());

     String    detalle =
                "<font color=\"#11aebf\"><bold>Fecha:&nbsp;</bold></font>"
                        + "\t" + ListaServiciosCreados.get(posicion_).getFecha().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Hora:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getHora().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Distri Incio:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getNameDistritoInicio().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Direccion Incio:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getDireccionIncio().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Distri Fin:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getNameDistritoFin().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Direccion Fin:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getDireccionFinal().toString() + "<br>"
                        + "<font color=\"#11aebf\"><bold>Num mint espera:&nbsp;</bold></font>"
                        + ListaServiciosCreados.get(posicion_).getNumeroMinutoTiempoEspera().toString() + "\t" + " min" + "<br>"
                        + "<font color=\"#11aebf\"><bold>Tipo Servicio :&nbsp;</bold></font>"
                        + "( " + ListaServiciosCreados.get(posicion_).getDesAutoTipoPidioCliente().toString() + " )" + "<br><br>"

                        + "<font color=\"#11aebf\"><bold>Import Serv:&nbsp;</bold></font>"
                        + "S/." + ListaServiciosCreados.get(posicion_).getImporteServicio().toString() + "<br>"

                        + "<font color=\"#11aebf\"><bold>Import Aire:&nbsp;</bold></font>"
                        + "S/." + ListaServiciosCreados.get(posicion_).getImporteAireAcondicionado().toString() + "<br>"

                      /*  +"<font color=\"#11aebf\"><bold>Import Tiem espera:&nbsp;</bold></font>"
                        +"S/."+jsonDetalle.getString("importeTiempoEspera")+"<br>"*/

                        + "<font color=\"#11aebf\"><bold>Import Peaje:&nbsp;</bold></font>"
                        + "S/." + ListaServiciosCreados.get(posicion_).getImportePeaje().toString() + "<br>"

                        + "<font color=\"#11aebf\"><bold>Import Tipo auto:&nbsp;</bold></font>"
                        + "S/." + importTipoAutoSolicitoCliente + "<br><br>"

                        + "<font color=\"#11aebf\"><bold>Import Total:&nbsp;</bold></font>"
                        + "S/." + String.valueOf(sumaImportesServicio) + "<br><br>";
        return detalle;
    }




    public static String DetalleServicioJson(JSONObject jsonDetalle ){
        String   detalle="";
        try {
               detalle=
                       "<font color=\"#11aebf\"><bold>Fecha:&nbsp;</bold></font>"
                               +"\t"+jsonDetalle.getString("Fecha")+"<br>"
                               +"<font color=\"#11aebf\"><bold>Hora:&nbsp;</bold></font>"
                               +jsonDetalle.getString("Hora")+"<br>"
                               +"<font color=\"#11aebf\"><bold>Distri Incio:&nbsp;</bold></font>"
                               +jsonDetalle.getString("nameDistritoInicio")+"<br>"
                               +"<font color=\"#11aebf\"><bold>Direccion Incio:&nbsp;</bold></font>"
                               +jsonDetalle.getString("DireccionIncio")+"<br>"
                               +"<font color=\"#11aebf\"><bold>Distri Fin:&nbsp;</bold></font>"
                               +jsonDetalle.getString("nameDistritoFin")+"<br>"
                               +"<font color=\"#11aebf\"><bold>Direccion Fin:&nbsp;</bold></font>"
                               +jsonDetalle.getString("direccionFinal")+"<br>"
                               +"<font color=\"#11aebf\"><bold>Num mint espera:&nbsp;</bold></font>"
                               +jsonDetalle.getString("numeroMinutoTiempoEspera")+"\t"+" min"+"<br>"
                               +"<font color=\"#11aebf\"><bold>Tipo Servicio:&nbsp;</bold></font>"
                               +"( "+jsonDetalle.getString("desAutoTipoPidioCliente")+" )"+"<br><br>"

                               +"<font color=\"#11aebf\"><bold>Import Serv:&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importeServicio")+"<br>"

                               +"<font color=\"#11aebf\"><bold>Import Aire:&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importeAireAcondicionado")+"<br>"

                               +"<font color=\"#11aebf\"><bold>Import Tiem espera:&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importeTiempoEspera")+"<br>"

                               +"<font color=\"#11aebf\"><bold>Import Peaje:&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importePeaje")+"<br>"

                               +"<font color=\"#11aebf\"><bold>Import Tipo auto:&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importeTipoAuto")+"<br><br>"

                               +"<font color=\"#11aebf\"><bold>Import adicional :&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importeGastosAdicionales")+"<br><br>"

                               +"<font color=\"#11aebf\"><bold>Import Total:&nbsp;</bold></font>"
                               +"S/."+jsonDetalle.getString("importeTotalServicio")+"<br><br>"
                               //+"\n"+jsonDetalle.getString("numeroMovilTaxi")
                               +"<font color=\"#11aebf\"><bold>Informacion Adicional :&nbsp;</bold></font>"
                               +jsonDetalle.getString("DescripcionServicion")+"<br><br>"
                               +"<font color=\"#11aebf\"><bold>Estado del servicio:&nbsp;</bold></font>"
                               +jsonDetalle.getString("nombreStadoServicio");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return detalle;
    }
}
