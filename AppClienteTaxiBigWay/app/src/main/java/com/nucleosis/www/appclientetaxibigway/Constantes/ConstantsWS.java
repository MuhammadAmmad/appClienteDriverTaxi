package com.nucleosis.www.appclientetaxibigway.Constantes;

/**
 * Created by karlos on 21/03/2016.
 */
public class ConstantsWS {
    /* DESCRIPCION DE LOS WEBSERVICES*/
    private static final String NAME_SPACE="http://taxibigway.com/soap";
    private static final String URL="http://taxibigway.com/soap";


    //CONDUCTOR REGISTRAR
    private static final String METHODO_1="WS_CLIENTE_REGISTRAR";
    private static final String SOAP_ACTION_1=NAME_SPACE+"/"+METHODO_1;


    //CONDUCTOR ACCESAR
    private static final String METHODO_2="WS_CLIENTE_ACCESAR";
    private static final String SOAP_ACTION_2=NAME_SPACE+"/"+METHODO_2;

    //CONDUCTOR ACCESAR
    private static final String METHODO_3="WS_CLIENTE_ENCONTRAR_POR_DES_EMAIL";
    private static final String SOAP_ACTION_3=NAME_SPACE+"/"+METHODO_3;


    //LISTAR_DISTRITOS
    private static final String METHODO_4="WS_DISTRITO_ENTREGAR_NOM_DISTRITO";
    private static final String SOAP_ACTION_4=NAME_SPACE+"/"+METHODO_4;

    //OBTENER_HORA_SERVIDOR
    private static final String METHODO_5="WS_OBTENER_FECHA_HORA_ACTUAL";
    private static final String SOAP_ACTION_5=NAME_SPACE+"/"+METHODO_5;

    //EXTRAER ID ZONA ID DISTTRITO POR NOMBRE ZONA
    private static final String METHODO_6="WS_ZONA_ENTREGAR_POR_NOM_ZONA";
    private static final String SOAP_ACTION_6=NAME_SPACE+"/"+METHODO_6;


    //EXTRAER  PRECIO ZONA X ZONA
    private static final String METHODO_7="WS_TARIFA_ZONAL_OBTENER_IMP_ZONAL";
    private static final String SOAP_ACTION_7=NAME_SPACE+"/"+METHODO_7;

    //EXTRAER  PRECIO ZONA X ZONA
    private static final String METHODO_8="WS_SERVICIO_REGISTRAR";
    private static final String SOAP_ACTION_8=NAME_SPACE+"/"+METHODO_8;

    //EXTRAER  WS_SERVICIO_LISTAR
    private static final String METHODO_9="WS_SERVICIO_LISTAR";
    private static final String SOAP_ACTION_9=NAME_SPACE+"/"+METHODO_9;

    //EXTRAER REGISTRAR SERVICIO
    private static final String METHODO_10="WS_SERVICIO_REGISTRAR";
    private static final String SOAP_ACTION_10=NAME_SPACE+"/"+METHODO_10;


    //EXTRAER LISTA SERVICIOS DE CLIENTE
    private static final String METHODO_11="WS_SERVICIO_LISTAR";
    private static final String SOAP_ACTION_11=NAME_SPACE+"/"+METHODO_11;

    //EXTRAER POSICION DE CONDUCTOR ASIGNADO
    private static final String METHODO_12="WS_CONDUCTOR_OBTENER_POSICION";
    private static final String SOAP_ACTION_12=NAME_SPACE+"/"+METHODO_12;

    //ACTUALIZAR UBICION RUTA INICIO CLIENTE
    private static final String METHODO_13="WS_CLIENTE_ACTUALIZAR_POSICION";
    private static final String SOAP_ACTION_13=NAME_SPACE+"/"+METHODO_13;

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

    public static String getNameSpace() {
        return NAME_SPACE;
    }

    public static String getSoapAction1() {
        return SOAP_ACTION_1;
    }

    public static String getMethodo1() {
        return METHODO_1;
    }

    public static String getURL() {
        return URL;
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

    public static String getMethodo4() {
        return METHODO_4;
    }

    public static String getSoapAction4() {
        return SOAP_ACTION_4;
    }

    public static String getMethodo5() {
        return METHODO_5;
    }

    public static String getSoapAction5() {
        return SOAP_ACTION_5;
    }

    public static String getMethodo6() {
        return METHODO_6;
    }

    public static String getSoapAction6() {
        return SOAP_ACTION_6;
    }
}
