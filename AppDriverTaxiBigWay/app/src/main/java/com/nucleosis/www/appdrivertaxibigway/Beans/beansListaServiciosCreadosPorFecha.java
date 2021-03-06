package com.nucleosis.www.appdrivertaxibigway.Beans;

/**
 * Created by carlos.lopez on 11/05/2016.
 */
public class beansListaServiciosCreadosPorFecha {
    private String idServicio; //ID_SERVICIO
    private String importeServicio;  //IMP_SERVICIO
    private String DescripcionServicion;  //DES_SERVICIO
    private String indAireAcondicionado;//IND_AIRE_ACONDICIONADO
    private String importeAireAcondicionado;  //IMP_AIRE_ACONDICIONADO
    private String importePeaje;                //IMP_PEAJE
    private String numeroMinutoTiempoEspera;    //NUM_MINUTO_TIEMPO_ESPERA
    private String importeGastosAdicionales;
    private String importeTiempoEspera;   //IMP_TIEMPO_ESPERA
    private String nameDistritoInicio;      //NOM_DISTRITO_INICIO
    private String nameZonaIncio;           //NOM ZONA INICIO
    private String nameDistritoFin;         //NOM_DISTRITO_FIN
    private String nameZonaFin;             //NON ZONA FIN
    private String DireccionIncio;          //DES_DIRECCION_INICIO
    private String direccionFinal;            //
    private String nombreConductor;         //

    private String nombreStadoServicio;
    private String statadoServicio;
    private String Fecha;   //FEC_SERVICIO

    private String FechaFormat; //FEC_SERVICIO_YMD
    private String Hora;       //DES_HORA
    private String infoAddress;

    private String nucCelularCliente;
    private String idCliente;
    private String idConductor;
    private String idTipoAuto;
    private String desAutoTipo;
    private String desAutoTipoPidioCliente;
    private String idAutoTipoPidioCliente;
    private String numeroMovilTaxi;
    private String idTipoPagoServicio;

    private String idTipoRegistro; //1 SI REGISTRO MOVIL  2 SI SE REGISTRO WEB

    private String indMostrarCelularCliente;

    private String latitudServicio;
    private String longitudServicio;

    private String nameCliente;
    private String tipoPago;

    private String idDistritoIncio;
    private String idDistritoFin;
    private String idZonaInicio;
    private String idZonaFin;

    public String getIdDistritoIncio() {
        return idDistritoIncio;
    }

    public void setIdDistritoIncio(String idDistritoIncio) {
        this.idDistritoIncio = idDistritoIncio;
    }

    public String getIdDistritoFin() {
        return idDistritoFin;
    }

    public void setIdDistritoFin(String idDistritoFin) {
        this.idDistritoFin = idDistritoFin;
    }

    public String getIdZonaInicio() {
        return idZonaInicio;
    }

    public void setIdZonaInicio(String idZonaInicio) {
        this.idZonaInicio = idZonaInicio;
    }

    public String getIdZonaFin() {
        return idZonaFin;
    }

    public void setIdZonaFin(String idZonaFin) {
        this.idZonaFin = idZonaFin;
    }

    public String getNameCliente() {
        return nameCliente;
    }

    public void setNameCliente(String nameCliente) {
        this.nameCliente = nameCliente;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getImporteGastosAdicionales() {
        return importeGastosAdicionales;
    }

    public void setImporteGastosAdicionales(String importeGastosAdicionales) {
        this.importeGastosAdicionales = importeGastosAdicionales;
    }

    public String getLongitudServicio() {
        return longitudServicio;
    }

    public void setLongitudServicio(String longitudServicio) {
        this.longitudServicio = longitudServicio;
    }

    public String getLatitudServicio() {
        return latitudServicio;
    }

    public void setLatitudServicio(String latitudServicio) {
        this.latitudServicio = latitudServicio;
    }

    public String getIndMostrarCelularCliente() {
        return indMostrarCelularCliente;
    }

    public void setIndMostrarCelularCliente(String indMostrarCelularCliente) {
        this.indMostrarCelularCliente = indMostrarCelularCliente;
    }

    public String getIdTipoRegistro() {
        return idTipoRegistro;
    }

    public void setIdTipoRegistro(String idTipoRegistro) {
        this.idTipoRegistro = idTipoRegistro;
    }

    public String getIdTipoPagoServicio() {
        return idTipoPagoServicio;
    }

    public void setIdTipoPagoServicio(String idTipoPagoServicio) {
        this.idTipoPagoServicio = idTipoPagoServicio;
    }

    public String getIdAutoTipoPidioCliente() {
        return idAutoTipoPidioCliente;
    }

    public void setIdAutoTipoPidioCliente(String idAutoTipoPidioCliente) {
        this.idAutoTipoPidioCliente = idAutoTipoPidioCliente;
    }

    public String getDesAutoTipoPidioCliente() {
        return desAutoTipoPidioCliente;
    }

    public void setDesAutoTipoPidioCliente(String desAutoTipoPidioCliente) {
        this.desAutoTipoPidioCliente = desAutoTipoPidioCliente;
    }

    public String getIndAireAcondicionado() {
        return indAireAcondicionado;
    }

    public void setIndAireAcondicionado(String indAireAcondicionado) {
        this.indAireAcondicionado = indAireAcondicionado;
    }

    //numero de la movil

    public String getNameZonaIncio() {
        return nameZonaIncio;
    }

    public void setNameZonaIncio(String nameZonaIncio) {
        this.nameZonaIncio = nameZonaIncio;
    }

    public String getNameZonaFin() {
        return nameZonaFin;
    }

    public void setNameZonaFin(String nameZonaFin) {
        this.nameZonaFin = nameZonaFin;
    }

    public String getNumeroMovilTaxi() {
        return numeroMovilTaxi;
    }

    public void setNumeroMovilTaxi(String numeroMovilTaxi) {
        this.numeroMovilTaxi = numeroMovilTaxi;
    }

    public String getDesAutoTipo() {
        return desAutoTipo;
    }

    public void setDesAutoTipo(String desAutoTipo) {
        this.desAutoTipo = desAutoTipo;
    }

    public String getNucCelularCliente() {
        return nucCelularCliente;
    }

    public void setNucCelularCliente(String nucCelularCliente) {
        this.nucCelularCliente = nucCelularCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdTipoAuto() {
        return idTipoAuto;
    }

    public void setIdTipoAuto(String idTipoAuto) {
        this.idTipoAuto = idTipoAuto;
    }

    public String getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(String idConductor) {
        this.idConductor = idConductor;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getImporteServicio() {
        return importeServicio;
    }

    public void setImporteServicio(String importeServicio) {
        this.importeServicio = importeServicio;
    }

    public String getDescripcionServicion() {
        return DescripcionServicion;
    }

    public void setDescripcionServicion(String descripcionServicion) {
        DescripcionServicion = descripcionServicion;
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

    public String getNumeroMinutoTiempoEspera() {
        return numeroMinutoTiempoEspera;
    }

    public void setNumeroMinutoTiempoEspera(String numeroMinutoTiempoEspera) {
        this.numeroMinutoTiempoEspera = numeroMinutoTiempoEspera;
    }

    public String getImporteTiempoEspera() {
        return importeTiempoEspera;
    }

    public void setImporteTiempoEspera(String importeTiempoEspera) {
        this.importeTiempoEspera = importeTiempoEspera;
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

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getFechaFormat() {
        return FechaFormat;
    }

    public void setFechaFormat(String fechaFormat) {
        FechaFormat = fechaFormat;
    }

    public String getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(String infoAddress) {
        this.infoAddress = infoAddress;
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
}
