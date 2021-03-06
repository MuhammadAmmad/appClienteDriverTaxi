package com.nucleosis.www.appdrivertaxibigway.Constans;

/**
 * Created by karlos on 21/03/2016.
 */
public class ConstantsWS {
    /* DESCRIPCION DE LOS WEBSERVICES*/
    private static final String NAME_SPACE="http://sistema.taxibigway.com/soap";
    private static final String URL="http://sistema.taxibigway.com/soap";
    //CONDUCTOR ACCESAR
    private static final String METHODO_1="WS_CONDUCTOR_ACCESAR";
    private static final String SOAP_ACTION_1=NAME_SPACE+"/"+METHODO_1;

    private static final String METHODO_2="WS_CONDUCTOR_ENCONTRAR_POR_DNI";
    private static final String SOAP_ACTION_2=NAME_SPACE+"/"+METHODO_2;

    //WS_CONDUCTOR_ACTUALIZAR_POSICION
  //USP_CONDUCTOR_ACTUALIZAR_POSICION_TURNO_ESTADO
    private static final String METHODO_3="USP_CONDUCTOR_ACTUALIZAR_POSICION_TURNO_ESTADO";
    private static final String SOAP_ACTION_3=NAME_SPACE+"/"+METHODO_3;

    private static final String METHODO_4="WS_TURNO_ACTIVAR";
    private static final String SOAP_ACTION_4=NAME_SPACE+"/"+METHODO_4;

    private static final String METHODO_5="WS_AUTO_LISTAR";
    private static final String SOAP_ACTION_5=NAME_SPACE+"/"+METHODO_5;

    private static final String METHODO_6="WS_TURNO_DESACTIVAR";
    private static final String SOAP_ACTION_6=NAME_SPACE+"/"+METHODO_6;

    private static final String METHODO_7="WS_SERVICIO_LISTAR";
    private static final String SOAP_ACTION_7=NAME_SPACE+"/"+METHODO_7;


    private static final String METHODO_8="WS_TURNO_OBTENER_TURNO_ACTUAL";
    private static final String SOAP_ACTION_8=NAME_SPACE+"/"+METHODO_8;

    private static final String METHODO_9="WS_SERVICIO_ASIGNAR_CONDUCTOR";
    private static final String SOAP_ACTION_9=NAME_SPACE+"/"+METHODO_9;

    private static final String METHODO_10="WS_OBTENER_FECHA_HORA_ACTUAL";
    private static final String SOAP_ACTION_10=NAME_SPACE+"/"+METHODO_10;

    private static final String METHODO_11="WS_SERVICIO_ACTUALIZAR_ID_ESTADO_SERVICIO";
    private static final String SOAP_ACTION_11=NAME_SPACE+"/"+METHODO_11;

    private static final String METHODO_12="WS_OBTENER_FECHA_HORA_ACTUAL";
    private static final String SOAP_ACTION_12=NAME_SPACE+"/"+METHODO_12;


    private static final String METHODO_13="WS_CONFIGURACION_OBTENER";
    private static final String SOAP_ACTION_13=NAME_SPACE+"/"+METHODO_13;

    private static final String METHODO_14="WS_ZONA_ENTREGAR_POR_NOM_ZONA";
    private static final String SOAP_ACTION_14=NAME_SPACE+"/"+METHODO_14;

    //EXTRAER  PRECIO ZONA X ZONA
    private static final String METHODO_15="WS_TARIFA_ZONAL_OBTENER_IMP_ZONAL";
    private static final String SOAP_ACTION_15=NAME_SPACE+"/"+METHODO_15;

    //LISTAR_DISTRITOS
    private static final String METHODO_16="WS_DISTRITO_ENTREGAR_NOM_DISTRITO";
    private static final String SOAP_ACTION_16=NAME_SPACE+"/"+METHODO_16;

    //ACUTALIZAR_SERVICIO
    private static final String METHODO_17="WS_SERVICIO_ACTUALIZAR";
    private static final String SOAP_ACTION_17=NAME_SPACE+"/"+METHODO_17;

    //ACUTALIZAR_SERVICIO
    private static final String METHODO_18="WS_CLIENTE_OBTENER_POSICION";
    private static final String SOAP_ACTION_18=NAME_SPACE+"/"+METHODO_18;

    //ACUTALIZAR_SERVICIO
    private static final String METHODO_19="WS_CALIFICACION_CLIENTE_REGISTRAR";
    private static final String SOAP_ACTION_19=NAME_SPACE+"/"+METHODO_19;

    public static String getMethodo19() {
        return METHODO_19;
    }

    public static String getSoapAction19() {
        return SOAP_ACTION_19;
    }

    public static String getSoapAction18() {
        return SOAP_ACTION_18;
    }

    public static String getMethodo18() {
        return METHODO_18;
    }

    public static String getMethodo17() {
        return METHODO_17;
    }

    public static String getSoapAction17() {
        return SOAP_ACTION_17;
    }

    public static String getSoapAction16() {
        return SOAP_ACTION_16;
    }

    public static String getMethodo16() {
        return METHODO_16;
    }

    public static String getMethodo15() {
        return METHODO_15;
    }

    public static String getSoapAction15() {
        return SOAP_ACTION_15;
    }

    public static String getMethodo14() {
        return METHODO_14;
    }

    public static String getSoapAction14() {
        return SOAP_ACTION_14;
    }

    public static String getMethodo13() {
        return METHODO_13;
    }

    public static String getSoapAction13() {
        return SOAP_ACTION_13;
    }

    public static String getMethodo12() {
        return METHODO_12;
    }

    public static String getSoapAction12() {
        return SOAP_ACTION_12;
    }

    public static String getMethodo11() {
        return METHODO_11;
    }

    public static String getSoapAction11() {
        return SOAP_ACTION_11;
    }

    public static String getMethodo10() {
        return METHODO_10;
    }

    public static String getSoapAction10() {
        return SOAP_ACTION_10;
    }

    public static String getMethodo9() {
        return METHODO_9;
    }

    public static String getSoapAction9() {
        return SOAP_ACTION_9;
    }

    public static String getMethodo8() {
        return METHODO_8;
    }

    public static String getSoapAction8() {
        return SOAP_ACTION_8;
    }

    public static String getMethodo7() {
        return METHODO_7;
    }

    public static String getSoapAction7() {
        return SOAP_ACTION_7;
    }

    public static String getSoapAction6() {
        return SOAP_ACTION_6;
    }

    public static String getMethodo6() {
        return METHODO_6;
    }

    public static String getMethodo5() {
        return METHODO_5;
    }

    public static String getSoapAction5() {
        return SOAP_ACTION_5;
    }

    public static String getMethodo4() {
        return METHODO_4;
    }

    public static String getSoapAction4() {
        return SOAP_ACTION_4;
    }

    public static String getNameSpace() {
        return NAME_SPACE;
    }

    public static String getURL() {
        return URL;
    }

    public static String getMethodo1() {
        return METHODO_1;
    }

    public static String getSoapAction1() {
        return SOAP_ACTION_1;
    }

    public static String getMethodo2() {
        return METHODO_2;
    }

    public static String getSoapAction2() {
        return SOAP_ACTION_2;
    }

    public static String getMethodo3() {
        return METHODO_3;
    }

    public static String getSoapAction3() {
        return SOAP_ACTION_3;
    }
}
