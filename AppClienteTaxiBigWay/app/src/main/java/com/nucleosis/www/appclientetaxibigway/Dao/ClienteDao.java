package com.nucleosis.www.appclientetaxibigway.Dao;

import android.content.Context;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Interfaces.ClienteIface;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;
import com.nucleosis.www.appclientetaxibigway.ws.wsLoginCliente;
import com.nucleosis.www.appclientetaxibigway.ws.wsRegistroCliente;

/**
 * Created by karlos on 16/04/2016.
 */
public class ClienteDao implements ClienteIface {
    private int CasoActivity=100;
    @Override
    public void RegisterCliente(dataClienteSigUp dataClienteSigUp,Context context) throws Exception {
        Log.d("datos",dataClienteSigUp.getNombre().toString());
        CasoActivity=101;
        new wsRegistroCliente(dataClienteSigUp,context,CasoActivity).execute();
    }

    @Override
    public void LoginCliente(String email, String pass, Context context) throws Exception {
        CasoActivity=102;
        new wsLoginCliente(email,pass,context,CasoActivity).execute();
    }
}
