package com.nucleosis.www.appdrivertaxibigway.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistoriaCarrera;
import  com.nucleosis.www.appdrivertaxibigway.R;
import java.util.List;

/**
 * Created by carlos.lopez on 05/04/2016.
 */
public class GridAdapterHistorialCarrera extends BaseAdapter {

    private final Context mContext;
    private final List<beansHistoriaCarrera> items;

    public GridAdapterHistorialCarrera(Context c, List<beansHistoriaCarrera>  items) {
        mContext = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size() ;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_cabecera_historial_carreras, viewGroup, false);
        }

        beansHistoriaCarrera item = items.get(position);

       /* TextView name = (TextView) view.findViewById(R.id.txtEntidadBankaria);
        name.setText(item.getEntidadBankaria());

        TextView monto=(TextView)view.findViewById(R.id.txtMontoSolicitado);
        monto.setText(item.getMontoSolicitado());

        TextView tasa=(TextView)view.findViewById(R.id.txtTaza);
        tasa.setText(item.getTaza());


        TextView descripcion = (TextView) view.findViewById(R.id.txtTaza);
        descripcion.setText(item.getTaza());*/



        return view;
    }
}
