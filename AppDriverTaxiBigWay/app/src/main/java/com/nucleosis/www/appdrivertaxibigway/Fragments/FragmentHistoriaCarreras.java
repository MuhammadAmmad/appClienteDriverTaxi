package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.nucleosis.www.appdrivertaxibigway.Adapters.AdapterHistoriaCarreras;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistoriaCarrera;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 03/04/2016.
 */
public class FragmentHistoriaCarreras extends Fragment implements AdapterHistoriaCarreras.OnItemClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentHistoriaCarreras() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Historia Carreras");
        View rootView = inflater.inflate(R.layout.view_contenedor_recycler, container, false);
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager lManager;
        lManager = new LinearLayoutManager(getActivity());
        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recycler.setLayoutManager(lManager);
        List<beansHistoriaCarrera> items = new ArrayList<>();
        beansHistoriaCarrera row=null;
        for(int i=21;i>0;i--){
            row=new beansHistoriaCarrera();
            row.setImageHistoria(R.mipmap.ic_historia_ubicacion);
            row.setName("Historia carrera  "+String.valueOf(i)+"A");
            items.add(row);
        }
        adapter=new AdapterHistoriaCarreras(items,getActivity(),this);
        recycler.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(AdapterHistoriaCarreras.ViewHolder holder, String id) {
        Toast.makeText(getActivity(),id,Toast.LENGTH_SHORT).show();
    }
}
