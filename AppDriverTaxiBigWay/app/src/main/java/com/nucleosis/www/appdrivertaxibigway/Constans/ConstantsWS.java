package com.nucleosis.www.appdrivertaxibigway.Constans;

/**
 * Created by karlos on 21/03/2016.
 */
public class ConstantsWS {
    /* DESCRIPCION DE LOS WEBSERVICES*/
    private static final String NAME_SPACE="http://taxibigway.com/soap";
    private static final String URL="http://taxibigway.com/soap";
    //CONDUCTOR ACCESAR
    private static final String METHODO_1="WS_CONDUCTOR_ACCESAR";
    private static final String SOAP_ACTION_1=NAME_SPACE+"/"+METHODO_1;

    private static final String METHODO_2="WS_CONDUCTOR_ENCONTRAR_POR_DNI";
    private static final String SOAP_ACTION_2=NAME_SPACE+"/"+METHODO_2;

    private static final String METHODO_3="WS_CONDUCTOR_ACTUALIZAR_POSICION";
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
