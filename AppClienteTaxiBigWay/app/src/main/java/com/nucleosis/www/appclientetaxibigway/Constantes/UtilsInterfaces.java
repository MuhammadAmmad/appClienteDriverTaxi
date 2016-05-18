package com.nucleosis.www.appclientetaxibigway.Constantes;

import com.nucleosis.www.appclientetaxibigway.Dao.ClienteDao;
import com.nucleosis.www.appclientetaxibigway.Fragments.FragmentListaServicios;
import com.nucleosis.www.appclientetaxibigway.Interfaces.ClienteIface;
import com.nucleosis.www.appclientetaxibigway.Interfaces.OnItemClickListenerDetalle;

/**
 * Created by karlos on 16/04/2016.
 */
public class UtilsInterfaces {
    /*INTERFACESSSS*/
    public static final ClienteIface LOGGIN_USUARIO =new ClienteDao();
    public static final OnItemClickListenerDetalle ON_ITEM_CLICK_LISTENER_DETALLE=new FragmentListaServicios();
}
