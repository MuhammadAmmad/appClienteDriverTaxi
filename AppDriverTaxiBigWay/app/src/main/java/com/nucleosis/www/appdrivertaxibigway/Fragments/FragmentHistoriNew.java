package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Adapters.GridAdapterHistorialCarrera;
import com.nucleosis.www.appdrivertaxibigway.Adapters.GriddAdapterServiciosTomadosConductor;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistoriaCarrera;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListenerDetalle;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.MapsConductorClienteServicio;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarStadoServicio;
import com.nucleosis.www.appdrivertaxibigway.ws.wsAsignarServicioConductor;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListaServiciosTomadosConductor;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 05/04/2016.
 */
public class FragmentHistoriNew extends Fragment implements OnItemClickListener,OnItemClickListenerDetalle {
    private int mYear, mMonth, mDay;
    private List<beansHistorialServiciosCreados> ITEMS_HISTORIAL;
    private componentesR compR;
    private String Fecha;
    private  PreferencesDriver preferencesDriver;

    private String idDriver;
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
                .setTitle("Historial Servicios");

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
            if(fecha.length()!=0){
                new wsListaServiciosTomadosConductor(getActivity(),fecha,grid).execute();
            }

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
                        intent.putExtra("idServicio",lista.get(position).getIdServicio());
                        intent.putExtra("stadoService",lista.get(position).getStatadoServicio());
                        startActivity(intent);
                    }else  if(lista.get(position).getStatadoServicio().equals("3")){
                        Intent intent=new Intent(getActivity(), MapsConductorClienteServicio.class);
                        intent.putExtra("idServicio",lista.get(position).getIdServicio());
                        intent.putExtra("stadoService",lista.get(position).getStatadoServicio());
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
                                    +"-0"+(monthOfYear + 1) + "-" + dayOfMonth);

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
                            //z[0]=0;
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

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(final Context context, final String idServicio,final String stadoServicio) {
        Activity activity;
        Log.d("idGrid",stadoServicio);
        int id=Integer.parseInt(stadoServicio);
        switch (id){
            case 1:
                break;
            case 2:
                activity=(Activity)context;
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
                final View view1 = activity.getLayoutInflater().inflate(R.layout.view_cancelar_servicio, null);
                final EditText editText=(EditText)view1.findViewById(R.id.editCancelServicio) ;
                final TextView lblmensaje=(TextView) view1.findViewById(R.id.lblMensajeCancelarServicio);
                alertDialogBuilder1.setView(view1);
                // alertDialogBuilder.setTitle(R.string.addContacto);
                alertDialogBuilder1.setNegativeButton(R.string.CANCEL,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int iii) {

                        preferencesDriver=new PreferencesDriver(context);
                        Log.d("idDriverA",preferencesDriver.OpenIdDriver());
                        idDriver=preferencesDriver.OpenIdDriver();
                        final String msn=editText.getText().toString();
                        if(msn.length()!=0){
                            int idTurno=0;
                            int idAuto=0;
                            new wsActualizarStadoServicio(context,idDriver,idServicio,idTurno,idAuto,"7",msn).execute();
                        }else{
                            Toast.makeText(context,"Escriba su movito !!",Toast.LENGTH_LONG).show();
                        }

                    }
                });

                AlertDialog alertDialog1 = alertDialogBuilder1.create();
                alertDialog1.show();

                break;
            case 3:
                activity=(Activity)context;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                final View view = activity.getLayoutInflater().inflate(R.layout.view_cancelar_servicio, null);

                alertDialogBuilder.setView(view);
                // alertDialogBuilder.setTitle(R.string.addContacto);
                alertDialogBuilder.setNegativeButton(R.string.CANCEL,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int iii) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case 4:
                Toast.makeText(context,"El serivio fue termindo con exito",Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(context,"El servicio no fue terminado",Toast.LENGTH_LONG).show();
                break;
            case 6:
                Toast.makeText(context,"El servicio fue cancelado por el cliente",Toast.LENGTH_LONG).show();
                break;
            case 7:
                Toast.makeText(context,"Usted a cancelado el servicio",Toast.LENGTH_LONG).show();
                break;

        }

    }
    private void cancelarServicio(){

    }

    @Override
    public void onClickDetalle(Context context, String idServicio, String stadoServicio) {
        Log.d("stadoSevcioLog",stadoServicio);
        Toast.makeText(context,"detalle de servicio",Toast.LENGTH_SHORT).show();
    }
}
