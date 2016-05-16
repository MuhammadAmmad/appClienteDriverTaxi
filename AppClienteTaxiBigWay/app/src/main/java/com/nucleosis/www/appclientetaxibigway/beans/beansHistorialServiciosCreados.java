package com.nucleosis.www.appclientetaxibigway.beans;

import android.graphics.drawable.Drawable;
import android.os.StrictMode;

/**
 * Created by carlos.lopez on 02/05/2016.
 */
public class  beansHistorialServiciosCreados {
/*
     <ID_SERVICIO xsi:type="xsd:string">43</ID_SERVICIO>
               <FEC_SERVICIO xsi:type="xsd:string">15/05/2016</FEC_SERVICIO>
               <FEC_SERVICIO_YMD xsi:type="xsd:string">2016-05-15</FEC_SERVICIO_YMD>
               <DES_HORA xsi:type="xsd:string">22:11:00</DES_HORA>
               <IMP_SERVICIO xsi:type="xsd:string">62.00</IMP_SERVICIO>
               <DES_SERVICIO xsi:type="xsd:string"/>
               <IND_EN_SERVICIO xsi:type="xsd:string">0</IND_EN_SERVICIO>
               <IND_AIRE_ACONDICIONADO xsi:type="xsd:string">0</IND_AIRE_ACONDICIONADO>
               <IMP_AIRE_ACONDICIONADO xsi:type="xsd:string">0.00</IMP_AIRE_ACONDICIONADO>
               <IMP_PEAJE xsi:type="xsd:string">0.00</IMP_PEAJE>
               <NUM_MINUTO_TIEMPO_ESPERA xsi:type="xsd:string">0</NUM_MINUTO_TIEMPO_ESPERA>
               <IMP_TIEMPO_ESPERA xsi:type="xsd:string">0.00</IMP_TIEMPO_ESPERA>
               <DES_DIRECCION_INICIO xsi:type="xsd:string">El Manantial El Paraiso/{"latitud":"-12.141345999999999","longitud":"-76.924525"}</DES_DIRECCION_INICIO>
               <DES_DIRECCION_FINAL xsi:type="xsd:string">José Carlos Mariátegui Calle 32</DES_DIRECCION_FINAL>
               <NOM_DISTRITO_INICIO xsi:type="xsd:string">VILLA MARIA</NOM_DISTRITO_INICIO>
               <NOM_ZONA_INICIO xsi:type="xsd:string">VILLA MARIA I</NOM_ZONA_INICIO>
               <NOM_DISTRITO_FIN xsi:type="xsd:string">SAN JUAN DE LURIGANCHO</NOM_DISTRITO_FIN>
               <NOM_ZONA_FIN xsi:type="xsd:string">SAN JUAN DE LURIGANCHO VI</NOM_ZONA_FIN>
               <NUM_MOVIL xsi:type="xsd:string">-----</NUM_MOVIL>
               <NOM_APE_CONDUCTOR xsi:type="xsd:string">-----</NOM_APE_CONDUCTOR>
               <ID_ESTADO_SERVICIO xsi:type="xsd:string">1</ID_ESTADO_SERVICIO>
               <NOM_ESTADO_SERVICIO xsi:type="xsd:string">CREADO</NOM_ESTADO_SERVICIO>
               <ID_TURNO xsi:type="xsd:string">0</ID_TURNO>
               <FEC_ACTUAL xsi:type="xsd:string">2016-05-15</FEC_ACTUAL>
               <DES_HORA_ACTUAL xsi:type="xsd:string">22:14</DES_HORA_ACTUAL>
               <ID_AUTO_TIPO_PIDIO_CLIENTE xsi:type="xsd:string">2</ID_AUTO_TIPO_PIDIO_CLIENTE>
               <DES_AUTO_TIPO_PIDIO_CLIENTE xsi:type="xsd:string">ECONOMICO</DES_AUTO_TIPO_PIDIO_CLIENTE>
               <ID_AUTO_TIPO xsi:type="xsd:string">2</ID_AUTO_TIPO>
               <DES_AUTO_TIPO xsi:type="xsd:string">-----</DES_AUTO_TIPO>
               <ID_CONDUCTOR xsi:type="xsd:string">0</ID_CONDUCTOR>
               <ID_CLIENTE xsi:type="xsd:string">13</ID_CLIENTE>
               <NUM_CELULAR xsi:type="xsd:string">987654321</NUM_CELULAR>

*/

    private String idServicio;
    private String importeServicio;
    private String DescripcionServicion;

    private String indAireAcondicionado;
    private String importeAireAcondicionado;
    private String importePeaje;

    private String numeroMinutoTiempoEspera;
    private String importeTiempoEspera;
    private String nameDistritoIncio;
    private String nameZonaIncio;
    private String DireccionIncio;

    private String nameDistritoFIn;
    private String nameZonaFin;
    private String direccionFinal;
    private String nombreConductor;

    private String idTipoAutoPidioCliente;
    private String desAutoTipoCliente;
    private String nombreStadoServicio;
    private String statadoServicio;
    private String Fecha;
    private String FechaFormat;
    private String Hora;

    private String idConductor;
    private String idCliente;
    private String numberCelularCliente;
    private String infoAddress;
    private Drawable imageHistorico;

    public String getNumberCelularCliente() {
        return numberCelularCliente;
    }

    public void setNumberCelularCliente(String numberCelularCliente) {
        this.numberCelularCliente = numberCelularCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(String idConductor) {
        this.idConductor = idConductor;
    }

    public String getFechaFormat() {
        return FechaFormat;
    }

    public void setFechaFormat(String fechaFormat) {
        FechaFormat = fechaFormat;
    }

    public String getDesAutoTipoCliente() {
        return desAutoTipoCliente;
    }

    public void setDesAutoTipoCliente(String desAutoTipoCliente) {
        this.desAutoTipoCliente = desAutoTipoCliente;
    }

    public String getIdTipoAutoPidioCliente() {
        return idTipoAutoPidioCliente;
    }

    public void setIdTipoAutoPidioCliente(String idTipoAutoPidioCliente) {
        this.idTipoAutoPidioCliente = idTipoAutoPidioCliente;
    }

    public String getNameZonaFin() {
        return nameZonaFin;
    }

    public void setNameZonaFin(String nameZonaFin) {
        this.nameZonaFin = nameZonaFin;
    }

    public String getNameDistritoFIn() {
        return nameDistritoFIn;
    }

    public void setNameDistritoFIn(String nameDistritoFIn) {
        this.nameDistritoFIn = nameDistritoFIn;
    }

    public String getNameZonaIncio() {
        return nameZonaIncio;
    }

    public void setNameZonaIncio(String nameZonaIncio) {
        this.nameZonaIncio = nameZonaIncio;
    }

    public String getNameDistritoIncio() {
        return nameDistritoIncio;
    }

    public void setNameDistritoIncio(String nameDistritoIncio) {
        this.nameDistritoIncio = nameDistritoIncio;
    }


    public String getIndAireAcondicionado() {
        return indAireAcondicionado;
    }

    public void setIndAireAcondicionado(String indAireAcondicionado) {
        this.indAireAcondicionado = indAireAcondicionado;
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
