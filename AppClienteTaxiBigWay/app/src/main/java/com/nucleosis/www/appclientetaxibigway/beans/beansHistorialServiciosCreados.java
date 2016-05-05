package com.nucleosis.www.appclientetaxibigway.beans;

import android.graphics.drawable.Drawable;
import android.os.StrictMode;

/**
 * Created by carlos.lopez on 02/05/2016.
 */
public class  beansHistorialServiciosCreados {
/*
    <ID_SERVICIO xsi:type="xsd:string">30</ID_SERVICIO>
    <FEC_SERVICIO xsi:type="xsd:string">02/05/2016</FEC_SERVICIO>
    <DES_HORA xsi:type="xsd:string">16:50:00</DES_HORA>
    <IMP_SERVICIO xsi:type="xsd:string">62.00</IMP_SERVICIO>
    <DES_SERVICIO xsi:type="xsd:string"/>
    <IND_EN_SERVICIO xsi:type="xsd:string">0</IND_EN_SERVICIO>
    <IMP_AIRE_ACONDICIONADO xsi:type="xsd:string">0.00</IMP_AIRE_ACONDICIONADO>
    <IMP_PEAJE xsi:nil="true"/>
    <NUM_MINUTO_TIEMPO_ESPERA xsi:nil="true"/>
    <IMP_TIEMPO_ESPERA xsi:nil="true"/>
    <DES_DIRECCION_INICIO xsi:type="xsd:string"/>
    <DES_DIRECCION_FINAL xsi:type="xsd:string"/>
    <NOM_DISTRITO_INICIO xsi:type="xsd:string">VILLA MARIA</NOM_DISTRITO_INICIO>
    <NOM_ZONA_INICIO xsi:type="xsd:string">VILLA MARIA I</NOM_ZONA_INICIO>
    <NOM_DISTRITO_FIN xsi:type="xsd:string">SAN JUAN DE LURIGANCHO</NOM_DISTRITO_FIN>
    <NOM_ZONA_FIN xsi:type="xsd:string">SAN JUAN DE LURIGANCHO VI</NOM_ZONA_FIN>
    <NUM_MOVIL xsi:nil="true"/>
    <NOM_APE_CONDUCTOR xsi:nil="true"/>
    <ID_ESTADO_SERVICIO xsi:type="xsd:string">1</ID_ESTADO_SERVICIO>
    <NOM_ESTADO_SERVICIO xsi:type="xsd:string">CREADO</NOM_ESTADO_SERVICIO>
*/

    private String idServicio;
    private String importeServicio;
    private String DescripcionServicion;
    private String importeAireAcondicionado;
    private String importePeaje;
    private String numeroMinutoTiempoEspera;
    private String importeTiempoEspera;
    private String DireccionIncio;
    private String direccionFinal;
    private String nombreConductor;
    private String nombreStadoServicio;
    private String statadoServicio;
    private String Fecha;
    private String Hora;
    private String infoAddress;
    private Drawable imageHistorico;

    public Drawable getImageHistorico() {
        return imageHistorico;
    }

    public void setImageHistorico(Drawable imageHistorico) {
        this.imageHistorico = imageHistorico;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getStatadoServicio() {
        return statadoServicio;
    }

    public void setStatadoServicio(String statadoServicio) {
        this.statadoServicio = statadoServicio;
    }

    public String getNombreStadoServicio() {
        return nombreStadoServicio;
    }

    public void setNombreStadoServicio(String nombreStadoServicio) {
        this.nombreStadoServicio = nombreStadoServicio;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getDireccionFinal() {
        return direccionFinal;
    }

    public void setDireccionFinal(String direccionFinal) {
        this.direccionFinal = direccionFinal;
    }

    public String getDireccionIncio() {
        return DireccionIncio;
    }

    public void setDireccionIncio(String direccionIncio) {
        DireccionIncio = direccionIncio;
    }

    public String getImporteTiempoEspera() {
        return importeTiempoEspera;
    }

    public void setImporteTiempoEspera(String importeTiempoEspera) {
        this.importeTiempoEspera = importeTiempoEspera;
    }

    public String getNumeroMinutoTiempoEspera() {
        return numeroMinutoTiempoEspera;
    }

    public void setNumeroMinutoTiempoEspera(String numeroMinutoTiempoEspera) {
        this.numeroMinutoTiempoEspera = numeroMinutoTiempoEspera;
    }

    public String getImportePeaje() {
        return importePeaje;
    }

    public void setImportePeaje(String importePeaje) {
        this.importePeaje = importePeaje;
    }

    public String getImporteAireAcondicionado() {
        return importeAireAcondicionado;
    }

    public void setImporteAireAcondicionado(String importeAireAcondicionado) {
        this.importeAireAcondicionado = importeAireAcondicionado;
    }

    public String getDescripcionServicion() {
        return DescripcionServicion;
    }

    public void setDescripcionServicion(String descripcionServicion) {
        DescripcionServicion = descripcionServicion;
    }

    public String getImporteServicio() {
        return importeServicio;
    }

    public void setImporteServicio(String importeServicio) {
        this.importeServicio = importeServicio;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(String infoAddress) {
        this.infoAddress = infoAddress;
    }
}
