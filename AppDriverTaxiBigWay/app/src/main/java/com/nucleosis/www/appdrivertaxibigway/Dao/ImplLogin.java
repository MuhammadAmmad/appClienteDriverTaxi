package com.nucleosis.www.appdrivertaxibigway.Dao;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.User;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.LoginUser;
import com.nucleosis.www.appdrivertaxibigway.ws.LoginDriverWS;

/**
 * Created by karlos on 07/03/2016.
 */
public class ImplLogin implements LoginUser {
    @Override
    public void Login(User user,Context context) throws Exception {

        if(user.getUser().length()!=0 && user.getPassword().length()!=0){
            Log.d("obs_",user.getUser());
            new LoginDriverWS(context,user).execute();

        }else{
            Toast.makeText(context,"todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
        }

        
    }
}
