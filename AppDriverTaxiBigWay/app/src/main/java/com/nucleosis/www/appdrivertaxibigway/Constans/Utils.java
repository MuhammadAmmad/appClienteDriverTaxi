package com.nucleosis.www.appdrivertaxibigway.Constans;

import com.nucleosis.www.appdrivertaxibigway.Dao.ImplLogin;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.LoginUser;

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




    /*INTERFACESSSS*/
    public static final LoginUser LOGGIN_USUARIO =new ImplLogin();


}
