package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.DonwloadImage.CircleTransform;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.squareup.picasso.Picasso;

/**
 * Created by karlos on 03/04/2016.
 */
public class FragmentDataDriver extends Fragment {
    private componentesR compR;
    private PreferencesDriver preferencesDriver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compR=new componentesR(getActivity());
        preferencesDriver=new PreferencesDriver(getActivity());
    }

    public FragmentDataDriver() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Mis datos");
        View rootView = inflater.inflate(R.layout.view_data_driver, container, false);
        compR.Controls_fragmentDataUser(rootView);
        String[]dataDriver=preferencesDriver.OpenDataDriver();
        compR.getTxtNombre().setText(dataDriver[0]);
        compR.getTxtAppelli().setText(dataDriver[1]);
        compR.getTxtCelular().setText(dataDriver[2]);
        compR.getTxtTelefono().setText(dataDriver[3]);
        compR.getTxtEmail().setText(dataDriver[4]);
        compR.getTxtDNI().setText(dataDriver[5]);
        compR.getTxtLicenciaExpiration().setText(dataDriver[7]);
      //  Log.d("sise_data_user", String.valueOf(dataDriver.length));
 /*      new AsyncTask<String, String, String>() {
           @Override
           protected String doInBackground(String... params) {
               Picasso.with(getActivity())
                       .load(params[0].toString())
                       .placeholder(R.mipmap.ic_imagen_driver)
                       .error(R.mipmap.ic_imagen_driver)
                       .transform(new CircleTransform())
                       .into(compR.getImageDriver());

               return null;
           }
       }.execute(dataDriver[6]);*/
        Picasso.with(getActivity())
                .load(dataDriver[6])
                .placeholder(R.mipmap.ic_imagen_driver)
                .error(R.mipmap.ic_imagen_driver)
                .transform(new CircleTransform())
                .into(compR.getImageDriver());

        return rootView;
    }
}
