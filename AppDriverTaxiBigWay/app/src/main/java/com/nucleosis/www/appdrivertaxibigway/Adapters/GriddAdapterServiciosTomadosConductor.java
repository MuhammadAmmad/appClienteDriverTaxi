package com.nucleosis.www.appdrivertaxibigway.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;

import org.w3c.dom.Text;

import java.util.List;
import static com.nucleosis.www.appdrivertaxibigway.Constans.Utils.ON_CLICK_LISTENER;
import static com.nucleosis.www.appdrivertaxibigway.Constans.Utils.ON_ITEM_CLICK_LISTENER_DETALLE;
/**
 * Created by carlos.lopez on 06/05/2016.
 */
public class GriddAdapterServiciosTomadosConductor extends BaseAdapter {

    private final Context mContext;
    private final List<beansHistorialServiciosCreados> items;
    private MyTypeFace myTypeFace;
    private int posicion;
    private Activity activity;
    public GriddAdapterServiciosTomadosConductor(Context c,
                                                 List<beansHistorialServiciosCreados>  items) {
        mContext = c;
        activity=(Activity)mContext;
        this.items = items;
        myTypeFace=new MyTypeFace(c);
    }

    @Override
    public int getCount() {
        return items.size() ;
    }

    @Override
    public Object getItem(int position) {
        posicion=position;
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHoler holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_items_historia_carreras, viewGroup, false);

            holder=new ViewHoler();
            holder.setFechaHora((TextView)view.findViewById(R.id.txtFechaHora));
            holder.setInfo((TextView)view.findViewById(R.id.txtInfoAddres));
            holder.setLblButton((TextView)view.findViewById(R.id.lblCancelarServicio));
            holder.setImageViewStatus((ImageView)view.findViewById(R.id.imageStatusServicio));
            holder.setLblButtonDetalleService((TextView)view.findViewById(R.id.lblDetalleServicio));
            view.setTag(holder);

        }else {
            holder=(ViewHoler)view.getTag();
        }

        beansHistorialServiciosCreados item = items.get(position);
        holder.getFechaHora().setText(item.getFecha()+"   "+item.getHora());
       // holder.getFechaHora().setTypeface(myTypeFace.openRobotoLight());

        holder.getInfo().setText(item.getInfoAddress());
        holder.getInfo().setTypeface(myTypeFace.openRobotoLight());
        holder.getImageViewStatus().setImageDrawable(item.getImageStatusServicio());
       // holder.getImageViewStatus().setBackgroundColor(item.getStatusServicioTomadoColor());
        //holder.getImageViewStatus().setBackground(item.getImageStatusServicio());
      //  holder.getLblButton().setOnClickListener((View.OnClickListener) activity);
        holder.getLblButton().setTypeface(myTypeFace.openRobotoLight());

        holder.getLblButton().setVisibility(View.GONE);
        holder.getLblButtonDetalleService().setVisibility(View.GONE);

        if(items.get(position).getStatadoServicio().equals("3")){
            Log.d("stado",items.get(position).getStatadoServicio());
            holder.getLblButton().setVisibility(View.GONE);
            holder.getLblButtonDetalleService().setVisibility(View.VISIBLE);
        }else if(items.get(position).getStatadoServicio().equals("2")){
            Log.d("stado",items.get(position).getStatadoServicio());
            holder.getLblButton().setVisibility(View.VISIBLE);
            holder.getLblButtonDetalleService().setVisibility(View.GONE);
        }else {
            holder.getLblButton().setVisibility(View.GONE);
            holder.getLblButtonDetalleService().setVisibility(View.VISIBLE);
        }
        holder.getLblButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ON_CLICK_LISTENER.onClick(mContext,items.get(position).getIdServicio(),
                                            items.get(position).getStatadoServicio());
            }
        });

        holder.getLblButtonDetalleService().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ON_ITEM_CLICK_LISTENER_DETALLE.onClickDetalle(mContext,items.get(position).getIdServicio(),
                        items.get(position).getStatadoServicio(),items.get(position).getFechaFormat());
            }
        });


        return view;
    }

    public class  ViewHoler{
        private TextView fechaHora;
        private TextView info;
        private TextView lblButton;
        private ImageView imageViewStatus;
        private TextView lblButtonDetalleService;

        public TextView getLblButtonDetalleService() {
            return lblButtonDetalleService;
        }

        public void setLblButtonDetalleService(TextView lblButtonDetalleService) {
            this.lblButtonDetalleService = lblButtonDetalleService;
        }

        public TextView getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(TextView fechaHora) {
            this.fechaHora = fechaHora;
        }

        public ImageView getImageViewStatus() {
            return imageViewStatus;
        }

        public void setImageViewStatus(ImageView imageViewStatus) {
            this.imageViewStatus = imageViewStatus;
        }

        public TextView getLblButton() {
            return lblButton;
        }

        public void setLblButton(TextView lblButton) {
            this.lblButton = lblButton;
        }

        public TextView getInfo() {
            return info;
        }

        public void setInfo(TextView info) {
            this.info = info;
        }


    }

}

