package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Adapters.GridAdapterHistorialCarrera;
import com.nucleosis.www.appdrivertaxibigway.Adapters.GriddAdapterServiciosTomadosConductor;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistoriaCarrera;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.MapsConductorClienteServicio;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.ws.wsAsignarServicioConductor;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListaServiciosTomadosConductor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 05/04/2016.
 */
public class FragmentHistoriNew extends Fragment implements OnItemClickListener {
    private int mYear, mMonth, mDay;
    private List<beansHistorialServiciosCreados> ITEMS_HISTORIAL;
    private componentesR compR;
    private String Fecha;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEMS_HISTORIAL=new ArrayList<beansHistorialServiciosCreados>();
        compR=new componentesR(getActivity());
        Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }
    public FragmentHistoriNew() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Historial Carreras");

        View rootView = inflater.inflate(R.layout.view_container, container, false);
        compR.Controls_fragment_Historia_Carrearas_new(rootView);
        setUpGridView(compR.getGrid());
        return rootView;
    }

    private void setUpGridView(GridViewWithHeaderAndFooter grid) {
                        grid.addHeaderView(createHeaderView(grid));
                        grid.setAdapter(new GriddAdapterServiciosTomadosConductor(getActivity(),
                                ITEMS_HISTORIAL));
    }

    private View createHeaderView(final GridViewWithHeaderAndFooter grid) {
        View view;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.view_cabecera_historial_carreras, null, false);
            compR.Controls_fragment_Historia_Carreras_createHeader(view);
            compR.getEditHistoriaCarrera().setText(mDay + "-"
                + (mMonth + 1) + "-" + mYear);

        formatoEntradaFecha(mYear,mMonth,mDay);
        if(compR.getEditHistoriaCarrera().getText().length()!=0){
            String fecha=compR.getEditHistoriaCarrera().getText().toString();
            new wsListaServiciosTomadosConductor(getActivity(),fecha,grid).execute();
        }
            compR.getChxBoxFecha().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            fechaHistorial(grid);
                        }else{
                            compR.getEditHistoriaCarrera().setText("");
                        }

                }
            });

        compR.getGrid().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(wsListaServiciosTomadosConductor.ListServicios!=null){
                    List<beansHistorialServiciosCreados> lista=wsListaServiciosTomadosConductor.ListServicios;

                    if(lista.get(position).getStatadoServicio().equals("2")){
                        Intent intent=new Intent(getActivity(), MapsConductorClienteServicio.class);
                        startActivity(intent);
                    }
                }
            }
        });


        return view;
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
                            compR.getEditHistoriaCarrera().setText(year + "-"
                                    + (monthOfYear + 1) + "-" + dayOfMonth);

                        }else if(dayOfMonth>=10 && (monthOfYear+1)<10){
                            compR.getEditHistoriaCarrera().setText(year
                                    + (monthOfYear + 1) + "-" + dayOfMonth + "-0");
                        }else if(dayOfMonth<10 && (monthOfYear+1)>=10){
                            compR.getEditHistoriaCarrera().setText( year + "-"
                                    + (monthOfYear + 1) + "-" + "0"+dayOfMonth);
                        }else if (dayOfMonth<10 && (monthOfYear+1)<10){
                            compR.getEditHistoriaCarrera().setText( year+ "-0"
                                    + (monthOfYear + 1) + "-" +  "0"+dayOfMonth);
                        }

                        z[0]++;
                        if(z[0]==2){
                            //  Toast.makeText(getActivity(),String.valueOf(z[0]),Toast.LENGTH_LONG).show();
                           new wsListaServiciosTomadosConductor(getActivity(),compR.getEditHistoriaCarrera().getText().toString(),grid).execute();
                            // z[0]=0;
                        }
                    }

                }, mYear, mMonth, mDay);

        dpd.show();


    }

    private void formatoEntradaFecha(int mYear, int mMonth, int mDay) {
        if(mDay>=10 && (mMonth+1)>=10){
            compR.getEditHistoriaCarrera().setText(mYear+ "-"
                    + (mMonth + 1) + "-" + mDay);
        }else if(mDay<10 && (mMonth+1)>=10){
            compR.getEditHistoriaCarrera().setText(mYear+ "-"
                    + (mMonth + 1) + "-0" + mDay);
        }else if(mDay<10 && (mMonth+1)<10){
            compR.getEditHistoriaCarrera().setText(mYear+ "-0"
                    + (mMonth + 1) + "-0" + mDay);
        }else if(mDay>=10 && (mMonth+1)<10){
            compR.getEditHistoriaCarrera().setText(mYear+ "-0"
                    + (mMonth + 1) + "-" + mDay);
        }

    }


    @Override
    public void onClick(Context context, String idServicio) {
        Log.d("idGrid",idServicio);
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Esta seguro de cancelar este servicio ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();

    }
}
