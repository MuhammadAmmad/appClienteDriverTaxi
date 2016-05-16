package com.nucleosis.www.appdrivertaxibigway.Beans;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * Created by carlos.lopez on 02/05/2016.
 */
public class beansHistorialServiciosCreados {
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
    private String indAireAcondicionado;
    private String importeAireAcondicionado;
    private String importePeaje;
    private String numeroMinutoTiempoEspera;
    private String importeTiempoEspera;
    private String nameDistritoInicio;
    private String nameDistritoFin;
    private String nameZonaIncio;
    private String nameZonaFin;
    private String DireccionIncio;
    private String direccionFinal;
    private String nombreConductor;
    private String nombreStadoServicio;
    private String statadoServicio;
    private String Fecha;
    private String FechaFormat;
    private String Hora;
    private String infoAddress;
    private Drawable imageHistorico;
    private Drawable imageStatusServicio;
    private int imagenOptional;
    private int statusServicioTomadoColor;
    private String idTurno;
    private String idCliente;
    private String numCelular;
    private String  nunMovilTaxi;

    public String getIndAireAcondicionado() {
        return indAireAcondicionado;
    }

    public void setIndAireAcondicionado(String indAireAcondicionado) {
        this.indAireAcondicionado = indAireAcondicionado;
    }

    public String getNameZonaFin() {
        return nameZonaFin;
    }

    public void setNameZonaFin(String nameZonaFin) {
        this.nameZonaFin = nameZonaFin;
    }

    public String getNameZonaIncio() {
        return nameZonaIncio;
    }

    public void setNameZonaIncio(String nameZonaIncio) {
        this.nameZonaIncio = nameZonaIncio;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNunMovilTaxi() {
        return nunMovilTaxi;
    }

    public void setNunMovilTaxi(String nunMovilTaxi) {
        this.nunMovilTaxi = nunMovilTaxi;
    }

    public String getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public String getFechaFormat() {
        return FechaFormat;
    }

    public void setFechaFormat(String fechaFormat) {
        FechaFormat = fechaFormat;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public int getStatusServicioTomadoColor() {
        return statusServicioTomadoColor;
    }

    public void setStatusServicioTomadoColor(int statusServicioTomadoColor) {
        this.statusServicioTomadoColor = statusServicioTomadoColor;
    }

    public Drawable getImageStatusServicio() {
        return imageStatusServicio;
    }

    public void setImageStatusServicio(Drawable imageStatusServicio) {
        this.imageStatusServicio = imageStatusServicio;
    }

    public String getNameDistritoInicio() {
        return nameDistritoInicio;
    }

    public void setNameDistritoInicio(String nameDistritoInicio) {
        this.nameDistritoInicio = nameDistritoInicio;
    }

    public String getNameDistritoFin() {
        return nameDistritoFin;
    }

    public void setNameDistritoFin(String nameDistritoFin) {
        this.nameDistritoFin = nameDistritoFin;
    }

    public int getImagenOptional() {
        return imagenOptional;
    }

    public void setImagenOptional(int imagenOptional) {
        this.imagenOptional = imagenOptional;
    }

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
