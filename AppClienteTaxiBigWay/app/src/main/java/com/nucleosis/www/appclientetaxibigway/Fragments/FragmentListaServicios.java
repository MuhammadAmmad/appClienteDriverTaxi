package com.nucleosis.www.appclientetaxibigway.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.Adpaters.GridAdapterHistoricoServicios;
import com.nucleosis.www.appclientetaxibigway.ListaServicios;
import com.nucleosis.www.appclientetaxibigway.MapsClienteConductorServicio;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;
import com.nucleosis.www.appclientetaxibigway.ws.wsListaServiciosCliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 02/05/2016.
 */
public class FragmentListaServicios extends Fragment{
    private int mYear, mMonth, mDay;
    private ComponentesR compR;
    private String Fecha;

    int sw=0;
    private List<beansHistorialServiciosCreados> ITEMS_HISTORIAL;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compR=new ComponentesR(getActivity());
        Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        ITEMS_HISTORIAL=new ArrayList<beansHistorialServiciosCreados>();

    }
    public FragmentListaServicios() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((ListaServicios) getActivity()).getSupportActionBar()
                .setTitle("Historial Servicios");

        View rootView = inflater.inflate(R.layout.view_container, container, false);
        compR.Controls_fragment_Historico_Sercicios_new(rootView);
        setUpGridView(compR.getGrid());
        return rootView;
    }


    private void setUpGridView(GridViewWithHeaderAndFooter grid) {
        grid.addHeaderView(createHeaderView(grid));
        grid.setAdapter(new GridAdapterHistoricoServicios(getActivity(),
                ITEMS_HISTORIAL));
    }
    private View createHeaderView(final GridViewWithHeaderAndFooter grid) {
        View view;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.view_cabecera_historia_servicios_creados, null, false);
        compR.Controls_fragment_Historia_Servicios_createHeader(view);

        compR.getImageButtonBuscar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha=compR.getEditHistoricoServiciosCreados().getText().toString();
                if(fecha.length()!=0){
                    new wsListaServiciosCliente(getActivity(),grid,fecha).execute();
                }

            }
        });

        formatoEntradaFecha(mYear,mMonth,mDay);

        if(compR.getEditHistoricoServiciosCreados().getText().length()!=0){
            String fecha=compR.getEditHistoricoServiciosCreados().getText().toString();
            new wsListaServiciosCliente(getActivity(),grid,fecha).execute();
        }
        compR.getCheckBoxFecha().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fechaHistorial(grid);
                }else {
                    compR.getEditHistoricoServiciosCreados().setText("");
                }

            }
        });
        compR.getGrid().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(wsListaServiciosCliente.ListServicios!=null){
                    List<beansHistorialServiciosCreados> listServicio=wsListaServiciosCliente.ListServicios;
                //    Toast.makeText(getActivity(),listServicio.get(position).getStatadoServicio(),Toast.LENGTH_LONG).show();
                    if(listServicio.get(position).getStatadoServicio().equals("1")){
                        Intent intent=new Intent(getActivity(), MapsClienteConductorServicio.class);
                        startActivity(intent);
                    }

                }

               // Toast.makeText(getActivity(),listServicio.get(position).getIdServicio(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void formatoEntradaFecha(int mYear, int mMonth, int mDay) {
        if(mDay>=10 && (mMonth+1)>=10){
            compR.getEditHistoricoServiciosCreados().setText(mYear+ "-"
                    + (mMonth + 1) + "-" + mDay);
        }else if(mDay<10 && (mMonth+1)>=10){
            compR.getEditHistoricoServiciosCreados().setText(mYear+ "-"
                    + (mMonth + 1) + "-0" + mDay);
        }else if(mDay<10 && (mMonth+1)<10){
            compR.getEditHistoricoServiciosCreados().setText(mYear+ "-0"
                    + (mMonth + 1) + "-0" + mDay);
        }else if(mDay>=10 && (mMonth+1)<10){
            compR.getEditHistoricoServiciosCreados().setText(mYear+ "-0"
                    + (mMonth + 1) + "-" + mDay);
        }

    }


    private void fechaHistorial(final GridViewWithHeaderAndFooter grid){

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        final int[] z = {0};
            //Fecha="x";
            // Launch Date Picker Dialog
            final DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if(dayOfMonth>=10  && (monthOfYear+1)>=10){
                                compR.getEditHistoricoServiciosCreados().setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }else if(dayOfMonth>=10 && (monthOfYear+1)<10){
                                compR.getEditHistoricoServiciosCreados().setText(year
                                        + (monthOfYear + 1) + "-" + dayOfMonth + "-0");
                            }else if(dayOfMonth<10 && (monthOfYear+1)>=10){
                                compR.getEditHistoricoServiciosCreados().setText( year + "-"
                                        + (monthOfYear + 1) + "-" + "0"+dayOfMonth);
                            }else if (dayOfMonth<10 && (monthOfYear+1)<10){
                                compR.getEditHistoricoServiciosCreados().setText( year+ "-0"
                                        + (monthOfYear + 1) + "-" +  "0"+dayOfMonth);
                            }

                         z[0]++;
                            if(z[0]==2){
                              //  Toast.makeText(getActivity(),String.valueOf(z[0]),Toast.LENGTH_LONG).show();
                                new wsListaServiciosCliente(getActivity(),grid,compR.getEditHistoricoServiciosCreados().getText().toString()).execute();
                                // z[0]=0;
                            }
                           }

                    }, mYear, mMonth, mDay);

            dpd.show();


    }
}
