package com.nucleosis.www.appclientetaxibigway.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;

/**
 * Created by karlos on 17/04/2016.
 */
public class FragmentDataClient extends Fragment {
    private ComponentesR compR;
    private PreferencesCliente preferencesCliente;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compR=new ComponentesR(getActivity());
        preferencesCliente=new PreferencesCliente(getActivity());
    }



/*    public static FragmentDataClient newInstance(int title) {
        //Log.d("parametro_",String.valueOf(title));
        FragmentDataClient frag = new FragmentDataClient();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
     //   Log.d("title_",String.valueOf(getArguments().getInt("title")));
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Mis datos");
        View rootView = inflater.inflate(R.layout.view_data_cliente, container, false);
        compR.Controls_fragmentDataClient(rootView);
        String[]dataDriver=preferencesCliente.OpenDataCliente();
        compR.getEditName().setText(dataDriver[1]);
        compR.getEditApaterno().setText(dataDriver[2]);
        compR.getEditAmaterno().setText(dataDriver[3]);
        compR.getEditDni().setTextColor(Color.BLACK);
        compR.getEditEmail().setTextColor(Color.BLACK);
        compR.getEditDni().setText(dataDriver[4]);
        compR.getEditEmail().setText(dataDriver[5]);
        compR.getEditCelular().setText(dataDriver[6]);
        //  Log.d("sise_data_user", String.valueOf(dataDriver.length));
       /* Picasso.with(getActivity())
                .load(dataDriver[6])
                .placeholder(R.mipmap.ic_imagen_driver)
                .error(R.mipmap.ic_imagen_driver)
                .transform(new CircleTransform())
                .into(compR.getImageDriver());*/

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
