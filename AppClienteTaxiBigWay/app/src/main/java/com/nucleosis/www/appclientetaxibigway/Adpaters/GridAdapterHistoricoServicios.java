package com.nucleosis.www.appclientetaxibigway.Adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nucleosis.www.appclientetaxibigway.beans.beansHistorialServiciosCreados;

import java.util.List;
import com.nucleosis.www.appclientetaxibigway.R;
/**
 * Created by carlos.lopez on 05/04/2016.
 */
public class GridAdapterHistoricoServicios extends BaseAdapter {

    private final Context mContext;
    private final List<beansHistorialServiciosCreados> items;

    public GridAdapterHistoricoServicios(Context c, List<beansHistorialServiciosCreados>  items) {
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
           view = inflater.inflate(R.layout.view_item_lista_servicios_cliente, viewGroup, false);
        }

        beansHistorialServiciosCreados item = items.get(position);

        TextView name = (TextView) view.findViewById(R.id.txtHoraFechaServicio);
        name.setText(item.getFecha()+"   "+item.getHora());

        TextView infoAddres=(TextView)view.findViewById(R.id.txtInfoAddres);
        infoAddres.setText(String.valueOf(item.getInfoAddress()));

        ImageView imageViewServicio=(ImageView)view.findViewById(R.id.imageViewServiciosCliente);
        imageViewServicio.setImageDrawable(item.getImageHistorico());


        return view;
    }
}
