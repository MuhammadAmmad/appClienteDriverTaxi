package com.nucleosis.www.appclientetaxibigway.Constantes;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by carlos.lopez on 04/05/2016.
 */
public class Utils {
    public static final String ACTION_RUN_SERVICE = "com.nucleosis.www.appclientetaxibigway.action.RUN_INTENT_SERVICE";
    public static final String ACTION_PROGRESS_EXIT = "com.nucleosis.www.appclientetaxibigway.action.PROGRESS_EXIT";
    public static final String ACTION_MEMORY_EXIT = "com.nucleosis.www.appclientetaxibigway.action.MEMORY_EXIT";
    public static final String EXTRA_MEMORY = "com.nucleosis.www.appclientetaxibigway.extra.MEMORY";

    public static final String ACTION_RUN_SERVICE_1 = "com.nucleosis.www.appclientetaxibigway.action.RUN_INTENT_SERVICE_1";
    public static final String ACTION_PROGRESS_EXIT_1 = "com.nucleosis.www.appclientetaxibigway.action.PROGRESS_EXIT_1";
    public static final String ACTION_MEMORY_EXIT_1 = "com.nucleosis.www.appclientetaxibigway.action.MEMORY_EXIT_1";
    public static final String EXTRA_MEMORY_1 = "com.nucleosis.www.appclientetaxibigway.extra.MEMORY_1";

    public static final LatLngBounds BOUNDS_LIMA = new LatLngBounds(
            new LatLng(-12.34202, -77.04231), new LatLng(-12.00103, -77.03269));
    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_1 =1;
    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_2 =2;
    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_3 =3;
    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_4 =4;


    public static boolean isNumeric(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}
