package com.nucleosis.www.appclientetaxibigway.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.nucleosis.www.appclientetaxibigway.Adpaters.AdapterDistritos;
import com.nucleosis.www.appclientetaxibigway.Adpaters.PlaceAutocompleteAdapter;
import com.nucleosis.www.appclientetaxibigway.Interfaces.IfaceAdrres;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.beans.beansDistritos;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;
import com.nucleosis.www.appclientetaxibigway.ws.wsListarDistritos;

import java.util.List;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class FragmentSolicitarServicioCliente extends Fragment
    implements GoogleApiClient.OnConnectionFailedListener {
   // public static Activity FRAGMENT_SOLICITAR_SERVICIO;
    protected GoogleApiClient mGoogleApiClient;
    public static final String TAG = "SampleActivityBase";
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private ComponentesR compR;
    private AdapterDistritos adapterDistritos;
   // private List<beansDistritos> listDistritos=wsListarDistritos.LISTA_DISTRITOS;

    private IfaceAdrres mCallback = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compR=new ComponentesR(getActivity());
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                   // .enableAutoManage(getActivity(), 0 /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();
    }

    @Override
    public void onStart() {
        Log.d("stado_","onStar");
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            Log.d("stado_","stop");
        }
        Log.d("stado_","stop");
        super.onStop();
    }


    public static FragmentSolicitarServicioCliente newInstance(Bundle arguments){
        FragmentSolicitarServicioCliente f = new FragmentSolicitarServicioCliente();
        if(arguments != null){
            Log.d("data-->", arguments.get("ini_").toString()+"\n"+arguments.get("fin_").toString());

            f.setArguments(arguments);
        }
        return f;
    }

    public FragmentSolicitarServicioCliente(){

    }
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (IfaceAdrres) activity;
        } catch (ClassCastException e) {}
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Solicitar Servicio");
        View rootView = inflater.inflate(R.layout.activity_solicitar__servicio__cliente, container, false);
        //new wsListarDistritos(getActivity(),rootView).execute();
        compR.Fragment_Solicitar_Servicio(rootView);
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        compR.getmAutocompleteView_AddressIncio().setOnItemClickListener(mAutocompleteClickListener);
        compR.getmAutocompleteView_AddressIncio().setAdapter(mAdapter);
       // compR.getmAutocompleteView().setText(getArguments().get("ini_").toString());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState !=null){
           // compR.getmAutocompleteView().setText(savedInstanceState.getString("helloWorld");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

           /* Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();*/
          //  Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            Log.d("location_",places.get(0).getLatLng().toString());
            final CharSequence thirdPartyAttribution = places.getAttributions();

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };


    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }
}



