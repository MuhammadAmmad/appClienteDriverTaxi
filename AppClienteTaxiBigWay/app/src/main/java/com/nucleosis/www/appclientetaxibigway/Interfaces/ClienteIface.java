package com.nucleosis.www.appclientetaxibigway.Interfaces;

import android.content.Context;

import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

/**
 * Created by karlos on 16/04/2016.
 */

public interface ClienteIface {
    public void RegisterCliente(dataClienteSigUp dataClienteSigUp,Context context)throws Exception;
    public void LoginCliente (String email,String pass, Context context) throws  Exception;
}
