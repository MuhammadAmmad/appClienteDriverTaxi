package com.nucleosis.www.appclientetaxibigway.Adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nucleosis.www.appclientetaxibigway.Circle.CircleTransform;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appclientetaxibigway.beans.beansHistorialServiciosCreados;

import java.util.List;
import com.nucleosis.www.appclientetaxibigway.R;
import com.squareup.picasso.Picasso;
/**
 * Created by carlos.lopez on 05/04/2016.
 */
import org.json.JSONException;
import org.json.JSONObject;

import static  com.nucleosis.www.appclientetaxibigway.Constantes.UtilsInterfaces.ON_ITEM_CLICK_LISTENER_DETALLE;
public class GridAdapterHistoricoServicios extends BaseAdapter {

    private final Context mContext;
    private final List<beansHistorialServiciosCreados> items;
    private MyTypeFace myTypeFace;
    private Fichero fichero;
    private JSONObject jsonConfiguraciones;
    private String urlConductor;
    public GridAdapterHistoricoServicios(Context c, List<beansHistorialServiciosCreados>  items) {
        mContext = c;
        this.items = items;
        myTypeFace=new MyTypeFace(c);
        fichero=new Fichero(mContext);
        jsonConfiguraciones=fichero.ExtraerConfiguraciones();
        if(jsonConfiguraciones!=null){
            try {
                urlConductor=jsonConfiguraciones.getString("urlFotoConductor");
                Log.d("urlCon",urlConductor.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                urlConductor="";
            }
        }
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHoler holder = null;
        if (view == null) {
            holder=new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.view_item_lista_servicios_cliente, viewGroup, false);
            holder.setFechaHora((TextView)view.findViewById(R.id.txtHoraFechaServicio));
            holder.setInfo((TextView)view.findViewById(R.id.txtInfoAddres));
            holder.setImageConductor((ImageView)view.findViewById(R.id.imageConductor));
            holder.setImageViewStatus((ImageView)view.findViewById(R.id.imageStatusServicio));
            holder.setLblButtonDetalleService((TextView)view.findViewById(R.id.lblDetalleServicio));
            view.setTag(holder);
        }else {
            holder=(ViewHoler)view.getTag();
        }
        beansHistorialServiciosCreados item = items.get(position);
        holder.getFechaHora().setText(item.getFecha()+"   "+item.getHora());
        int longitudCadena=item.getInfoAddress().length();
        String cadenaFormato="-"+item.getInfoAddress().substring(1,2).toUpperCase()+
                item.getInfoAddress().substring(2,longitudCadena).toLowerCase();
        holder.getInfo().setText(cadenaFormato);
        holder.getInfo().setTypeface(myTypeFace.openRobotoLight());
        holder.getImageViewStatus().setImageDrawable(item.getImageStatusServicio());
     //   holder.getLblButtonDetalleService().setTypeface(myTypeFace.openRobotoLight());
        Picasso.with(mContext)
                .load(urlConductor+item.getNameFotoConductor())
                .placeholder(R.mipmap.ic_imagen_launcher)
                .error(R.mipmap.ic_imagen_launcher)
                .transform(new CircleTransform())
                .into(holder.getImageConductor());

        if(items.get(position).getStatadoServicio().equals("3")){
            Log.d("stado",items.get(position).getStatadoServicio());

        }else if(items.get(position).getStatadoServicio().equals("2")){
            Log.d("stado",items.get(position).getStatadoServicio());
        }else {

        }


        holder.getLblButtonDetalleService().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ON_ITEM_CLICK_LISTENER_DETALLE.onClickDetalle(mContext,items.get(position).getIdServicio(),
                        items.get(position).getStatadoServicio());
            }
        });

        return view;
    }


    public class  ViewHoler{
        private TextView fechaHora;
        private TextView info;
        private ImageView imageConductor;
        private ImageView imageViewStatus;
        private TextView lblButtonDetalleService;

        public TextView getLblButtonDetalleService() {
            return lblButtonDetalleService;
        }

        public ImageView getImageConductor() {
            return imageConductor;
        }

        public void setImageConductor(ImageView imageConductor) {
            this.imageConductor = imageConductor;
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


        public TextView getInfo() {
            return info;
        }

        public void setInfo(TextView info) {
            this.info = info;
        }


    }


}
