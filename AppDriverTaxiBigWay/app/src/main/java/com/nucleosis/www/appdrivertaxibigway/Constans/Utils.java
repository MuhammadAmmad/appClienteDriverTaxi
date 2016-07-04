package com.nucleosis.www.appdrivertaxibigway.Constans;

import com.nucleosis.www.appdrivertaxibigway.Dao.ImplLogin;
import com.nucleosis.www.appdrivertaxibigway.Fragments.FragmentHistoriNew;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.LoginUser;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListenerDetalle;

/**
 * Created by karlos on 08/03/2016.
 */
public class Utils {
    public static final String ACTION_RUN_SERVICE = "com.nucleosis.www.appdrivertaxibigway.action.RUN_INTENT_SERVICE";
    public static final String ACTION_PROGRESS_EXIT = "com.nucleosis.www.appdrivertaxibigway.action.PROGRESS_EXIT";
    public static final String ACTION_MEMORY_EXIT = "com.nucleosis.www.appdrivertaxibigway.action.MEMORY_EXIT";
    public static final String EXTRA_MEMORY = "com.nucleosis.www.appdrivertaxibigway.extra.MEMORY";

    public static final String ACTION_RUN_SERVICE_2 = "com.nucleosis.www.appdrivertaxibigway.action.RUN_INTENT_SERVICE_2";
    public static final String ACTION_PROGRESS_EXIT_2 = "com.nucleosis.www.appdrivertaxibigway.action.PROGRESS_EXIT_2";
    public static final String ACTION_MEMORY_EXIT_2 = "com.nucleosis.www.appdrivertaxibigway.action.MEMORY_EXIT_2";
    public static final String EXTRA_MEMORY_2 = "com.nucleosis.www.appdrivertaxibigway.extra.MEMORY_2";

    public static final String ACTION_RUN_SERVICE_3 = "com.nucleosis.www.appdrivertaxibigway.action.RUN_INTENT_SERVICE_3";
    public static final String ACTION_PROGRESS_EXIT_3 = "com.nucleosis.www.appdrivertaxibigway.action.PROGRESS_EXIT_3";
    public static final String ACTION_MEMORY_EXIT_3 = "com.nucleosis.www.appdrivertaxibigway.action.MEMORY_EXIT_3";
    public static final String EXTRA_MEMORY_3 = "com.nucleosis.www.appdrivertaxibigway.extra.MEMORY_3";


    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_1 =1;
    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_2 =2;
    public static final   int MY_PERMISSION_ACCESS_COURSE_LOCATION_3 =3;
    /*INTERFACESSSS*/
    public static final LoginUser LOGGIN_USUARIO =new ImplLogin();
    public static final OnItemClickListener ON_CLICK_LISTENER =new FragmentHistoriNew();
    public static final OnItemClickListenerDetalle ON_ITEM_CLICK_LISTENER_DETALLE=new FragmentHistoriNew();

    public static final int TIME_OUT=15000;
    public static final int TIME_LISTAR_SERVICIOS=15000;
    public static final int TIME_SERVICIO_TURNO  =15000;
    public static final int TIME_LOCATION_DRIVER=15000;
}
