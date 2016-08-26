package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.nucleosis.www.appdrivertaxibigway.ConexionRed.ConnectionUtils;
import com.nucleosis.www.appdrivertaxibigway.ConexionRed.conexionInternet;
import com.nucleosis.www.appdrivertaxibigway.Constans.Constans;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Constans.Utils;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListener;
import com.nucleosis.www.appdrivertaxibigway.Interfaces.OnItemClickListenerDetalle;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.MapsConductorClienteServicio;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarStadoServicio;
import com.nucleosis.www.appdrivertaxibigway.ws.wsAsignarServicioConductor;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerConfiguracionAdicionales;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListaServiciosTomadosConductor;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListarServiciosTomadoConductorDiaActual;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

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
    private View rootView;
    public static GridViewWithHeaderAndFooter GRID_CANCEL;
    private String idDriver;
    private Fichero fichero;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEMS_HISTORIAL=new ArrayList<beansHistorialServiciosCreados>();
        compR=new componentesR(getActivity());
        Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        fichero=new Fichero(getActivity());
        new wsExtraerConfiguracionAdicionales(getActivity()).execute();

    }
    public FragmentHistoriNew() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Historial Servicios");

        rootView = inflater.inflate(R.layout.view_container, container, false);
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
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.view_cabecera_historial_carreras, null, false);
        GRID_CANCEL=grid;
            compR.Controls_fragment_Historia_Carreras_createHeader(view);
            compR.getEditHistoriaCarrera().setText(mDay + "-"
                + (mMonth + 1) + "-" + mYear);

        formatoEntradaFecha(mYear,mMonth,mDay);

        compR.getImageButonListarServicios().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(getActivity(),"XXXX",Toast.LENGTH_LONG).show();
                final String fecha=compR.getEditHistoriaCarrera().getText().toString();
                Log.d("fechax_",fecha);
                if(fecha.length()!=0){
                new AsyncTask<String, String, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... params) {
                        conexionInternet conecicoin=new conexionInternet();
                        return conecicoin.isInternet();
                    }
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);

                        if (aBoolean){
                            preferencesDriver=new PreferencesDriver(getActivity());
                            JSONObject jsonFecha=preferencesDriver.ExtraerHoraSistema();
                            Log.d("xx_","154645616"+jsonFecha.toString());
                            if(jsonFecha!=null){
                                try {
                                    jsonFecha.getString("fechaServidor");
                                    Log.d("fecha_",jsonFecha.getString("fechaServidor"));
                                    String fecha_=compR.getEditHistoriaCarrera().getText().toString();
                                    if(jsonFecha.getString("fechaServidor").equals(fecha_)){
                                        new wsListarServiciosTomadoConductorDiaActual(getActivity(),grid).execute();
                                    }else{
                                        Log.d("poraqi_",fecha_);
                                        new wsListaServiciosTomadosConductor(getActivity(),fecha_,grid).execute();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else{
                                Log.d("fecha_","null");
                            }
                        }else{
                            //String msnInternet=getResources().getString(R.string.InternetAccessRevision);
                            Toast.makeText(getActivity(),"No tienes Acceso a internet!!",Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
                }
            }
        });



        if(compR.getEditHistoriaCarrera().getText().length()!=0){
            final String fecha=compR.getEditHistoriaCarrera().getText().toString();
            if(fecha.length()!=0){

                new wsListarServiciosTomadoConductorDiaActual(getActivity(),grid).execute();
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
               // Toast.makeText(getActivity(), String.valueOf(id),Toast.LENGTH_SHORT);
                String Fecha_=compR.getEditHistoriaCarrera().getText().toString();
                preferencesDriver=new PreferencesDriver(getActivity());
                JSONObject jsonObject =preferencesDriver.ExtraerHoraSistema();
                JSONObject jsonDataTurno= preferencesDriver.ExtraerDataTurno();

                final LocationManager manager = (LocationManager)getActivity().getSystemService( Context.LOCATION_SERVICE );
              boolean gps=false;
               if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                   gps=false;
                }else{
                    gps=true;
                }
                if(jsonObject!=null && jsonDataTurno!=null && gps==true){
                    try {
                        //FECHA DIFERENTE DE LA FECHA ACTUAL
                        int stado=Integer.parseInt(jsonDataTurno.getString("stadoTurnoJson").toString());
                        Log.d("StasdoTurno_",String.valueOf(stado));
                        if(stado==1){
                            if(wsListaServiciosTomadosConductor.ListServicios!=null &&
                                    !Fecha_.equals(jsonObject.getString("fechaServidor")) ){
                                List<beansHistorialServiciosCreados> lista1=wsListaServiciosTomadosConductor.ListServicios;
                                Log.d("aquiListaServicio","fueraDeFechaActual");
                                if(lista1.size()!=0){
                                  //  Toast.makeText(getActivity(),String.valueOf(lista1.get(position).getIdServicio()),Toast.LENGTH_SHORT).show();
                                    if(lista1.get(position).getStatadoServicio().equals("2")){
                                        Intent intent=new Intent(getActivity(), MapsConductorClienteServicio.class);
                                        intent.putExtra("idServicio",lista1.get(position).getIdServicio());
                                        intent.putExtra("stadoService",lista1.get(position).getStatadoServicio());
                                        intent.putExtra("idCliente",lista1.get(position).getIdCliente());
                                        intent.putExtra("ZonaIncio",lista1.get(position).getNameZonaIncio());
                                        intent.putExtra("addresIncio",lista1.get(position).getDireccionIncio());
                                        intent.putExtra("ZonaFin",lista1.get(position).getNameZonaFin());
                                        intent.putExtra("idCliente",lista1.get(position).getIdCliente());
                                        startActivity(intent);
                                        getActivity().finish();

                                    }else  if(lista1.get(position).getStatadoServicio().equals("3")){
                                        Intent intent=new Intent(getActivity(), MapsConductorClienteServicio.class);
                                        intent.putExtra("idServicio",lista1.get(position).getIdServicio());
                                        intent.putExtra("stadoService",lista1.get(position).getStatadoServicio());
                                        intent.putExtra("idCliente",lista1.get(position).getIdCliente());
                                        intent.putExtra("ZonaIncio",lista1.get(position).getNameZonaIncio());
                                        intent.putExtra("addresIncio",lista1.get(position).getDireccionIncio());
                                        intent.putExtra("ZonaFin",lista1.get(position).getNameZonaFin());
                                        intent.putExtra("idCliente",lista1.get(position).getIdCliente());
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }

                            }

                            if(wsListarServiciosTomadoConductorDiaActual.listServiciosFechaActualConducor!=null &&
                                    Fecha_.equals(jsonObject.getString("fechaServidor"))){
                                Log.d("aquiListaServicio","En la fecha");
                                List<beansHistorialServiciosCreados> lista2=wsListarServiciosTomadoConductorDiaActual.listServiciosFechaActualConducor;
                                if(lista2.size()!=0){
                                //    Toast.makeText(getActivity(),String.valueOf(lista2.get(position).getIdServicio()),Toast.LENGTH_SHORT).show();
                                    if(lista2.get(position).getStatadoServicio().equals("2")){
                                       // new wsObtenerDireccionIncioCliente(getActivity(),lista2.get(position).getIdCliente()).execute();
                                        Intent intent=new Intent(getActivity(), MapsConductorClienteServicio.class);
                                        intent.putExtra("idServicio",lista2.get(position).getIdServicio());
                                        intent.putExtra("stadoService",lista2.get(position).getStatadoServicio());
                                        intent.putExtra("idCliente",lista2.get(position).getIdCliente());
                                        intent.putExtra("ZonaIncio",lista2.get(position).getNameZonaIncio());
                                        intent.putExtra("addresIncio",lista2.get(position).getDireccionIncio());
                                        intent.putExtra("ZonaFin",lista2.get(position).getNameZonaFin());

                                        intent.putExtra("indMostrarCelularCliente",lista2.get(position).getIndMostrarCelularCliente());
                                        intent.putExtra("celularCliente",lista2.get(position).getNumCelular());
                                        intent.putExtra("latitudService",lista2.get(position).getLatitudService());
                                        intent.putExtra("longitudService",lista2.get(position).getLongitudService());
                                        intent.putExtra("idCliente",lista2.get(position).getIdCliente());

                                        intent.putExtra("idDistritoIncio", lista2.get(position).getIdDistritoIncio());
                                        intent.putExtra("idDistritoFin", lista2.get(position).getIdDistritoFin());
                                        intent.putExtra("idZonaIncio", lista2.get(position).getIdZonaInicio());
                                        intent.putExtra("idZonaFin", lista2.get(position).getIdZonaFin());

                                        startActivity(intent);
                                        getActivity().finish();
                                    }else  if(lista2.get(position).getStatadoServicio().equals("3")){
                                     //   new wsObtenerDireccionIncioCliente(getActivity(),lista2.get(position).getIdCliente()).execute();
                                        Intent intent=new Intent(getActivity(), MapsConductorClienteServicio.class);
                                        intent.putExtra("idServicio",lista2.get(position).getIdServicio());
                                        intent.putExtra("stadoService",lista2.get(position).getStatadoServicio());
                                        intent.putExtra("idCliente",lista2.get(position).getIdCliente());
                                        intent.putExtra("ZonaIncio",lista2.get(position).getNameZonaIncio());
                                        intent.putExtra("addresIncio",lista2.get(position).getDireccionIncio());
                                        intent.putExtra("ZonaFin",lista2.get(position).getNameZonaFin());

                                        intent.putExtra("indMostrarCelularCliente",lista2.get(position).getIndMostrarCelularCliente());
                                        intent.putExtra("celularCliente",lista2.get(position).getNumCelular());
                                        intent.putExtra("latitudService",lista2.get(position).getLatitudService());
                                        intent.putExtra("longitudService",lista2.get(position).getLongitudService());
                                        intent.putExtra("idCliente",lista2.get(position).getIdCliente());

                                        intent.putExtra("idDistritoIncio", lista2.get(position).getIdDistritoIncio());
                                        intent.putExtra("idDistritoFin", lista2.get(position).getIdDistritoFin());
                                        intent.putExtra("idZonaIncio", lista2.get(position).getIdZonaInicio());
                                        intent.putExtra("idZonaFin", lista2.get(position).getIdZonaFin());
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }


                            }
                        }else {
                            Toast.makeText(getActivity(),"Debe acativar un turno para ir al servicio !!!",Toast.LENGTH_LONG).show();
                        }

                        //FECHA IGUAL A LA FECHA ACTUAL

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    if(gps==false){
                        AlertNoGps();
                    }
                   // Toast.makeText(getActivity(),"campos nulos",Toast.LENGTH_SHORT).show();
                }



            }
        });


        return view;
    }
    private void AlertNoGps() {
        AlertDialog alert = null;
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String msnGps=getResources().getString(R.string.gpsMensaje);
        builder.setMessage(msnGps)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused")
                                        final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
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
    public void onClickDetalle(final Context context, final String idServicio, String stadoServicio, final String fecha_) {
        Log.d("stadoSevcioLog",stadoServicio);
      //  Toast.makeText(context,"detalle de servicio",Toast.LENGTH_SHORT).show();
        final Fichero fichero=new Fichero(context);
        preferencesDriver=new PreferencesDriver(context);
        final JSONArray jsonServiciosConductor=fichero.ExtraerListaServiciosTomadoConductor();

        Log.d("fechilla_",fecha_);
      if(jsonServiciosConductor!=null){

        Log.d("jsonSerxx",jsonServiciosConductor.toString()+"--->"+idServicio);
          Log.d("idServicios_statico",idServicio);

        }


        new AsyncTask<String, String, JSONObject>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Listando...");
                progressDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                JSONObject jsonServicio_ =new JSONObject();
                SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
                SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;

                request.addProperty("idCliente", 0);
                request.addProperty("fecServicio", fecha_);
                request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
                request.addProperty("idEstadoServicio", "");
                request.addProperty("idAutoTipo", 0);
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(), Utils.TIME_OUT);
                try {
                    ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                    headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                    httpTransport.call(ConstantsWS.getSoapAction7(), envelope, headerPropertyArrayList);
                    //  httpTransport.call("http://taxibigway.com/soap/WS_SERVICIO_LISTAR", envelope);
                    SoapObject response1= (SoapObject) envelope.bodyIn;
                    Vector<?> responseVector = (Vector<?>) response1.getProperty("return");

                    Log.d("vectorRE",responseVector.toString());
                    Log.d("requestServicios_",request.toString());
                    int countVector=responseVector.size();
                    Log.d("cantidad", String.valueOf(responseVector.size()));
                    for(int i=0;i<countVector;i++){
                        SoapObject dataVector=(SoapObject)responseVector.get(i);
                        if(dataVector.getProperty("ID_SERVICIO").toString().equals(idServicio)){
                            Log.d("idIgual",dataVector.getPropertyAsString("ID_SERVICIO"));

                            jsonServicio_.put("importeServicio",dataVector.getProperty("IMP_SERVICIO").toString());
                            jsonServicio_.put("DescripcionServicion",dataVector.getProperty("DES_SERVICIO").toString());
                            jsonServicio_.put("Fecha",dataVector.getProperty("FEC_SERVICIO").toString());
                            jsonServicio_.put("Hora",dataVector.getProperty("DES_HORA").toString());
                            jsonServicio_.put("nombreConductor",dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                            jsonServicio_.put("nucCelularCliente",dataVector.getProperty("NUM_CELULAR").toString());
                            jsonServicio_.put("importeAireAcondicionado",dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                            jsonServicio_.put("importeGastosAdicionales",dataVector.getProperty("IMP_GASTOS_ADICIONALES").toString());
                            jsonServicio_.put("numeroMinutoTiempoEspera",dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());
                            jsonServicio_.put("importeTiempoEspera",dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                            jsonServicio_.put("importePeaje",dataVector.getProperty("IMP_PEAJE").toString());

                            jsonServicio_.put("nameDistritoInicio",dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                            jsonServicio_.put("DireccionIncio",dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                            jsonServicio_.put("nameDistritoFin",dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                            jsonServicio_.put("direccionFinal",dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                            jsonServicio_.put("numeroMovilTaxi",dataVector.getProperty("NUM_MOVIL").toString());
                            jsonServicio_.put("nombreStadoServicio",dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                            jsonServicio_.put("idAutoTipoPidioCliente",dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString());
                            jsonServicio_.put("desAutoTipoPidioCliente",dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString());

                            jsonServicio_.put("nameCliente",dataVector.getProperty("NOM_APE_CLIENTE").toString());
                            jsonServicio_.put("tipoPago",dataVector.getProperty("NOM_TIPO_PAGO_SERVICIO").toString());

                            String importeGastoAdicional_=dataVector.getPropertyAsString("IMP_GASTOS_ADICIONALES");
                            double importeGastoAdicional=0.0;
                            String importeTipoAuto="0.00";

                            if(dataVector.getPropertyAsString("ID_AUTO_TIPO_PIDIO_CLIENTE").equals("1")){
                                JSONObject configuracionJson=fichero.ExtraerConfiguraciones();
                                if(configuracionJson!=null){
                                    jsonServicio_.put("importeTipoAuto",configuracionJson.getString("impAutoVip"));
                                    importeTipoAuto=configuracionJson.getString("impAutoVip");
                                }else {
                                    jsonServicio_.put("importeTipoAuto","0.00");
                                    importeTipoAuto="0.00";
                                }
                            }else{
                                jsonServicio_.put("importeTipoAuto","0.00");
                                importeTipoAuto="0.00";
                            }
                            if(importeGastoAdicional_!=null){
                                if(Constans.isNumeric(importeGastoAdicional_)){
                                    importeGastoAdicional=Double.parseDouble(importeGastoAdicional_);
                                }else{
                                    importeGastoAdicional=0.0;
                                }

                            }
                            double sumaImportes=Double.parseDouble(dataVector.getPropertyAsString("IMP_SERVICIO"))+
                                    Double.parseDouble(dataVector.getPropertyAsString("IMP_TIEMPO_ESPERA"))+
                                    Double.parseDouble(dataVector.getPropertyAsString("IMP_AIRE_ACONDICIONADO"))+
                                    Double.parseDouble(dataVector.getPropertyAsString("IMP_PEAJE"))+
                                    Double.parseDouble(importeTipoAuto)+importeGastoAdicional;

                            jsonServicio_.put("importeTotalServicio",String.valueOf(sumaImportes));

                            i=countVector;
                        }



                    }
                }catch( InterruptedIOException i){
                    i.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
//            Log.d("error", e.getMessage());
                }

                Log.d("xxJosn",jsonServicio_.toString());
                return jsonServicio_;
            }

            @Override
            protected void onPostExecute(JSONObject jsonDetalle) {
                super.onPostExecute(jsonDetalle);
                progressDialog.dismiss();
                if(jsonDetalle!=null){

                    AlertDialog.Builder alerDialogoBilder = new AlertDialog.Builder(context);
                    Activity activity=(Activity)context;
                    final View view = activity.getLayoutInflater().inflate(R.layout.view_detalle_servicio_custom, null);
                    //  dialogo1.setTitle("Detalle del Servicio");
                    TextView txtDetalle=(TextView)view.findViewById(R.id.txtDetalleServicio);
                    Button btnOK=(Button)view.findViewById(R.id.btnOk);

                    alerDialogoBilder.setView(view);
                    String detalle = "";
                    final AlertDialog alertDialog;
                    Log.d("jsonDetall_x",jsonDetalle.toString());
                        detalle=Constans.DetalleServicioJson(jsonDetalle);
                    Log.d("detaxxx",detalle);
                        txtDetalle.setText(Html.fromHtml(detalle));
                   /* alerDialogoBilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });*/
                    alertDialog = alerDialogoBilder.create();
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }else{
                   String msn= getResources().getString(R.string.noDetalle);
                    Toast.makeText(context,msn,Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

    }


}
