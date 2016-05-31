package com.nucleosis.www.appdrivertaxibigway.Constans;

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
}
