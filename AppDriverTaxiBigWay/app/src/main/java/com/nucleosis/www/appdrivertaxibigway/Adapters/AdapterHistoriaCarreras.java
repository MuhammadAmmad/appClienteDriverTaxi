package com.nucleosis.www.appdrivertaxibigway.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistoriaCarrera;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appdrivertaxibigway.R;
import java.util.List;

/**
 * Created by karlos on 03/04/2016.
 */
public class AdapterHistoriaCarreras extends RecyclerView.Adapter<AdapterHistoriaCarreras.ViewHolder> {
    private final Context contexto;
    private List<beansHistorialServiciosCreados> items;

    private OnItemClickListener escucha;
    public static MyTypeFace myTypeFace;

    public interface OnItemClickListener {
        public void onClick(ViewHolder holder, String id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Referencias UI
        public TextView fechaHora;
        public TextView info;
        public TextView lblButton;
        public ImageView imageViewStatus;

        public ViewHolder(View v) {
            super(v);
            fechaHora = (TextView) v.findViewById(R.id.txtFechaHora);
            info = (TextView) v.findViewById(R.id.nombre);
            lblButton=(TextView)v.findViewById(R.id.lblCancelarServicio);
            imageViewStatus=(ImageView)v.findViewById(R.id.imageStatusServicio);
          //  imageButtonVerNotificacion=(ImageButton)v.findViewById(R.id.imageVerNotificacion);
            info.setTypeface(myTypeFace.openRobotoLight());
            lblButton.setOnClickListener(this);
            // v.setOnClickListener(this);
            // visitas.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onClick(this, obtenerNotificacion(getAdapterPosition()));
        }
    }

    private String obtenerNotificacion(int posicion) {
        if (items != null) {
            return items.get(posicion).getFecha();
        }

        return null     ;
    }

    public AdapterHistoriaCarreras(
            List<beansHistorialServiciosCreados> items,Context contexto,OnItemClickListener escucha) {
        this.contexto = contexto;
        this.items = items;
        this.escucha = escucha;
        myTypeFace=new MyTypeFace(contexto);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_items_historia_carreras, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        //viewHolder.imagen.setImageResource(items.get(i).getImageHistoria());
       // viewHolder.nombre.setText(items.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
