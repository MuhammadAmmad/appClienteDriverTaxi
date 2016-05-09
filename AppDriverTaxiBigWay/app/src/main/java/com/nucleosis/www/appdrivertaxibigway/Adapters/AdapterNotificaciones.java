package com.nucleosis.www.appdrivertaxibigway.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;

import java.util.List;

/**
 * Created by carlos.lopez on 01/04/2016.
 */
public class AdapterNotificaciones extends RecyclerView.Adapter<AdapterNotificaciones.ViewHolder> {
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
        public ImageView imagen;
        public TextView addres;
        public TextView fecha;
        public TextView distritos;
       // public ImageButton imageButtonVerNotificacion;

        public ViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            addres = (TextView) v.findViewById(R.id.nombre);
            fecha=(TextView)v.findViewById(R.id.lblFecha);
            distritos=(TextView)v.findViewById(R.id.lblDistritos);
         //   imageButtonVerNotificacion=(ImageButton)v.findViewById(R.id.imageVerNotificacion);

            addres.setTypeface(myTypeFace.openRobotoLight());
            fecha.setTypeface(myTypeFace.OpenSansRegular());
          //  distritos.setTypeface(myTypeFace.openRobotoLight());
           // imageButtonVerNotificacion.setOnClickListener(this);
            imagen.setOnClickListener(this);
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
            String pos=String.valueOf(posicion);
            //
          //  return items.get(posicion).getIdServicio();
            return  pos;
        }

        return null     ;
    }

    public AdapterNotificaciones(List<beansHistorialServiciosCreados> items, Context contexto, OnItemClickListener escucha) {
        this.contexto = contexto;
        this.items = items;
        this.escucha = escucha;
        myTypeFace=new MyTypeFace(contexto);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_notificaion_toolbar, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagenOptional());
        viewHolder.addres.setText("Incio: "+items.get(i).getDireccionIncio()+"\n"+"Fin: "+items.get(i).getDireccionFinal());
        viewHolder.fecha.setText(items.get(i).getFecha()+"\n"+items.get(i).getHora());
        viewHolder.distritos.setText(items.get(i).getNameDistritoInicio()+" / "+items.get(i).getNameDistritoFin());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
