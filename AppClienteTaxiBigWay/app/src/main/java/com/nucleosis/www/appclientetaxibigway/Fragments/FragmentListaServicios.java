package com.nucleosis.www.appclientetaxibigway.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.Adpaters.GridAdapterHistoricoServicios;
import com.nucleosis.www.appclientetaxibigway.ConexionRed.conexionInternet;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.Interfaces.OnItemClickListenerDetalle;
import com.nucleosis.www.appclientetaxibigway.ListaServicios;
import com.nucleosis.www.appclientetaxibigway.MapsClienteConductorServicio;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;
import com.nucleosis.www.appclientetaxibigway.ws.wsListaServiciosCliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 02/05/2016.
 */
public class FragmentListaServicios extends Fragment implements OnItemClickListenerDetalle {
    private int mYear, mMonth, mDay;
    private ComponentesR compR;
    private String Fecha;
    private Fichero fichero;

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
        fichero=new Fichero(getActivity());
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
                final String fecha=compR.getEditHistoricoServiciosCreados().getText().toString();
                if(fecha.length()!=0){
                    new AsyncTask<String, String, Boolean>() {
                        @Override
                        protected Boolean doInBackground(String... params) {
                            conexionInternet conexion=new conexionInternet();
                            return conexion.isInternet();
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            super.onPostExecute(aBoolean);
                            if(aBoolean){
                                new wsListaServiciosCliente(getActivity(),grid,fecha).execute();
                            }else {
                                Toast.makeText(getActivity(),"No tiene acceso a internet !!!",Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();

                }

            }
        });
        compR.getImageButtonBuscar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fecha=compR.getEditHistoricoServiciosCreados().getText().toString();
                if(fecha.length()!=0){
                    new AsyncTask<String, String, Boolean>() {
                        @Override
                        protected Boolean doInBackground(String... params) {
                            conexionInternet conexion=new conexionInternet();
                            return conexion.isInternet();
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            super.onPostExecute(aBoolean);
                            if(aBoolean){
                                new wsListaServiciosCliente(getActivity(),grid,fecha).execute();
                            }else {
                                Toast.makeText(getActivity(),"No tiene acceso a internet !!!",Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();

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
                JSONArray jsonArrayServiciosDiarios=fichero.ExtraerListaServiciosTomadoCliete();
                if(wsListaServiciosCliente.ListServicios!=null){
                    List<beansHistorialServiciosCreados> listServicio=wsListaServiciosCliente.ListServicios;
                    //    Toast.makeText(getActivity(),listServicio.get(position).getStatadoServicio(),Toast.LENGTH_LONG).show();
                    if(listServicio.get(position).getStatadoServicio().equals("1") ||
                            listServicio.get(position).getStatadoServicio().equals("2")||
                            listServicio.get(position).getStatadoServicio().equals("3")){

                        if(jsonArrayServiciosDiarios!=null){
                         for(int i=0;i<jsonArrayServiciosDiarios.length();i++){
                             try {
                                 if(listServicio.get(position).getIdServicio().
                                         equals(jsonArrayServiciosDiarios.getJSONObject(i).getString("idServicio"))){
                                            Toast.makeText(getActivity(),
                                                    jsonArrayServiciosDiarios.getJSONObject(i).getString("idServicio"),
                                                    Toast.LENGTH_LONG).show();
                                         Intent intent=new Intent(getActivity(), MapsClienteConductorServicio.class);
                                        intent.putExtra("idServicio",jsonArrayServiciosDiarios.getJSONObject(i).getString("idServicio"));
                                        startActivity(intent);
                                        getActivity().finish();
                                     i=jsonArrayServiciosDiarios.length();
                                 }
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }
                        }



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
                                        + "-0"+(monthOfYear + 1) + "-" + dayOfMonth);
                            }else if(dayOfMonth<10 && (monthOfYear+1)>=10){
                                compR.getEditHistoricoServiciosCreados().setText( year + "-"
                                        + (monthOfYear + 1) + "-" + "0"+dayOfMonth);
                            }else if (dayOfMonth<10 && (monthOfYear+1)<10){
                                compR.getEditHistoricoServiciosCreados().setText( year+ "-0"
                                        + (monthOfYear + 1) + "-" +  "0"+dayOfMonth);
                            }
                        //    Log.d("8787878-->","fasdfasdf");
                        /* z[0]++;
                            if(z[0]==2){
                                Log.d("fsdfjl-->","fasdfasdf");
                                z[0]=0;
                              //  Toast.makeText(getActivity(),String.valueOf(z[0]),Toast.LENGTH_LONG).show();
                                new wsListaServiciosCliente(getActivity(),grid,compR.getEditHistoricoServiciosCreados().getText().toString()).execute();
                                // z[0]=0;
                            }else  if(z[0]==1){
                                new wsListaServiciosCliente(getActivity(),grid,compR.getEditHistoricoServiciosCreados().getText().toString()).execute();
                                Log.d("cuento_",String.valueOf(z[0]));
                                z[0]=0;
                            }*/
                           }

                    }, mYear, mMonth, mDay);

            dpd.show();


    }

    @Override
    public void onClickDetalle(final Context context, String idServicio, String stadoServicio) {
        Activity activity=(Activity)context;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_detalle_servicio_custom, null);

        TextView lblDetalleServicio = (TextView) view.findViewById(R.id.txtDetalleServicio);
        Fichero fichero=new Fichero(context);
        JSONArray jsonService=fichero.ExtraerListaServiciosTomadoCliete();
                if(jsonService!=null){
                    for(int i=0; i<jsonService.length();i++){
                        try {
                            if(idServicio.equals(jsonService.getJSONObject(i).getString("idServicio"))){
                                    Log.d("click_",jsonService.getJSONObject(i).getString("statadoServicio"));
                                DetalleServicioReturn(jsonService,i,context);
                                lblDetalleServicio.setText(Html.fromHtml(DetalleServicioReturn(jsonService,i,context)));
                                i=jsonService.length();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

        alertDialogBuilder.setView(view);
        AlertDialog alertDialog;

        alertDialogBuilder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Toast.makeText(context,idServicio+"--"+stadoServicio,Toast.LENGTH_LONG).show();
    }

    private String DetalleServicioReturn( JSONArray ListaServiciosCreados,int posicion_,Context context){
        String  detalle="";
        String importTipoAutoSolicitoCliente="0.00";
        double sumaImportesServicio=0.0;
        Fichero fichero_=new Fichero(context);

        if(ListaServiciosCreados!=null){

            try {
                if(ListaServiciosCreados.getJSONObject(posicion_).getString("idAutoTipoPidioCliente").equals("1")){
                    JSONObject jsonObjectConfiguraciones=fichero_.ExtraerConfiguraciones();
                    if(jsonObjectConfiguraciones!=null){
                        importTipoAutoSolicitoCliente=jsonObjectConfiguraciones.getString("impAutoVip");
                    }

                }
                try {
                    sumaImportesServicio=Double.parseDouble(ListaServiciosCreados.getJSONObject(posicion_).getString("importeServicio"))+
                            Double.parseDouble(ListaServiciosCreados.getJSONObject(posicion_).getString("importeAireAcondicionado"))+
                            Double.parseDouble(ListaServiciosCreados.getJSONObject(posicion_).getString("importeTiempoEspera"))+
                            Double.parseDouble(ListaServiciosCreados.getJSONObject(posicion_).getString("importePeaje"))+
                            Double.parseDouble(importTipoAutoSolicitoCliente);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                detalle =
                        "<font color=\"#11aebf\"><bold>Fecha:&nbsp;</bold></font>"
                                + "\t" + ListaServiciosCreados.getJSONObject(posicion_).getString("Fecha") + "<br>"
                                + "<font color=\"#11aebf\"><bold>Hora:&nbsp;</bold></font>"
                                + ListaServiciosCreados.getJSONObject(posicion_).getString("Hora") + "<br>"
                                + "<font color=\"#11aebf\"><bold>Distri Incio:&nbsp;</bold></font>"
                                + ListaServiciosCreados.getJSONObject(posicion_).getString("nameDistritoInicio") + "<br>"
                                + "<font color=\"#11aebf\"><bold>Direccion Incio:&nbsp;</bold></font>"
                                + ListaServiciosCreados.getJSONObject(posicion_).getString("DireccionIncio") + "<br>"
                                + "<font color=\"#11aebf\"><bold>Distri Fin:&nbsp;</bold></font>"
                                + ListaServiciosCreados.getJSONObject(posicion_).getString("nameDistritoFin") + "<br>"
                                + "<font color=\"#11aebf\"><bold>Direccion Fin:&nbsp;</bold></font>"
                                + ListaServiciosCreados.getJSONObject(posicion_).getString("direccionFinal")  + "<br>"
                                + "<font color=\"#11aebf\"><bold>Num mint espera:&nbsp;</bold></font>"
                                + ListaServiciosCreados.getJSONObject(posicion_).getString("numeroMinutoTiempoEspera")  + "\t" + " min" + "<br>"
                                + "<font color=\"#11aebf\"><bold>Tipo Servicio :&nbsp;</bold></font>"
                                + "( " + ListaServiciosCreados.getJSONObject(posicion_).getString("desAutoTipoPidioCliente")  + " )" + "<br><br>"

                                + "<font color=\"#11aebf\"><bold>Import Serv:&nbsp;</bold></font>"
                                + "S/." + ListaServiciosCreados.getJSONObject(posicion_).getString("importeServicio")  + "<br>"

                                + "<font color=\"#11aebf\"><bold>Import Aire:&nbsp;</bold></font>"
                                + "S/." + ListaServiciosCreados.getJSONObject(posicion_).getString("importeAireAcondicionado")  + "<br>"

                                +"<font color=\"#11aebf\"><bold>Import Tiem espera:&nbsp;</bold></font>"
                                +"S/."+ListaServiciosCreados.getJSONObject(posicion_).getString("importeTiempoEspera") +"<br>"

                                + "<font color=\"#11aebf\"><bold>Import Peaje:&nbsp;</bold></font>"
                                + "S/." + ListaServiciosCreados.getJSONObject(posicion_).getString("importePeaje")  + "<br>"

                                + "<font color=\"#11aebf\"><bold>Import Tipo auto:&nbsp;</bold></font>"
                                + "S/." + importTipoAutoSolicitoCliente + "<br><br>"

                                + "<font color=\"#11aebf\"><bold>Import Total:&nbsp;</bold></font>"
                                + "S/." + String.valueOf(sumaImportesServicio) + "<br><br>";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return detalle;
    }
}
