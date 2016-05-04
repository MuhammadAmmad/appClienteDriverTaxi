package com.nucleosis.www.appclientetaxibigway.Adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nucleosis.www.appclientetaxibigway.beans.beansDistritos;
import  com.nucleosis.www.appclientetaxibigway.R;
import java.util.List;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class AdapterDistritos extends ArrayAdapter<beansDistritos> {
    private Context context;

    List<beansDistritos> datos = null;

    public AdapterDistritos(Context context, List<beansDistritos> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.view_spiner_distritos, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_spiner_distritos,null);
        }
        ((TextView) convertView.findViewById(R.id.txtNameDistrito)).setText(datos.get(position).getNameDistrito());

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
            row = layoutInflater.inflate(R.layout.view_spiner_distritos, parent, false);
        }

        if (row.getTag() == null)
        {
            Holder_contactos redSocialHolder = new Holder_contactos();
            redSocialHolder.setTextViewTitulo((TextView) row.findViewById(R.id.txtNameDistrito));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        beansDistritos redSocial = datos.get(position);
        ((Holder_contactos) row.getTag()).getTextViewTitulo().setText(redSocial.getNameDistrito());

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