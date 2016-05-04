package com.nucleosis.www.appdrivertaxibigway.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansVehiculoConductor;
import com.nucleosis.www.appdrivertaxibigway.R;

import java.util.List;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class AdapterListVehiculos extends ArrayAdapter<beansVehiculoConductor> {
    private Context context;

    List<beansVehiculoConductor> datos = null;

    public AdapterListVehiculos(Context context, List<beansVehiculoConductor> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.view_spinner_list_vehiculos, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_spinner_list_vehiculos,null);
        }
        ((TextView) convertView.findViewById(R.id.txtPlacaVehiculo)).setText(datos.get(position).getPlacaVehiculo());

        return convertView;
    }

    //gestiona la lista usando el View Holder Pattern. Equivale a la típica implementación del getView
    //de un Adapter de un ListView ordinario
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.view_spinner_list_vehiculos, parent, false);
        }

        if (row.getTag() == null)
        {
            Holder_contactos redSocialHolder = new Holder_contactos();
            redSocialHolder.setTextViewTitulo((TextView) row.findViewById(R.id.txtPlacaVehiculo));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        beansVehiculoConductor redSocial = datos.get(position);
        ((Holder_contactos) row.getTag()).getTextViewTitulo().setText(redSocial.getPlacaVehiculo());

        return row;
    }


    class Holder_contactos {
        TextView textViewTitulo;

        public TextView getTextViewTitulo() {
            return textViewTitulo;
        }

        public void setTextViewTitulo(TextView textViewTitulo) {
            this.textViewTitulo = textViewTitulo;
        }

    }
}