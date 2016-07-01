package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by carlos.lopez on 27/04/2016.
 */
public class wsExtraerPrecioZonaDistrito extends AsyncTask<String,String,String> {
    private Context context;
    private JSONObject jsonOrigen;
    private JSONObject jsonDestino;
    private ComponentesR compR;
    private MainActivity CX=new MainActivity();
    private int hora,minuto,mYear, mMonth, mDay;
    private long mLastClickTime = 0;
    private String addresIncio;
    private String addresFin;
    private String idZonaIncio="-1";
    private  String idZonaFin="-1";
    private Fichero fichero;
    private JSONObject configuracionServicio;
    private ProgressDialog progressDialog;
    public wsExtraerPrecioZonaDistrito(Context context ,
                                       JSONObject jsonOrigen,
                                       JSONObject jsonDestino) {
        this.context = context;
        this.jsonDestino = jsonDestino;
        this.jsonOrigen = jsonOrigen;
        this.addresFin=addresFin;
        this.addresIncio=addresIncio;
        compR=new ComponentesR(context);
        compR.Contros_MainActivity(CX.MAIN_ACTIVITY);

           if(jsonOrigen!=null && jsonDestino!=null){
               Log.d("dataJon",jsonOrigen.toString()+"-*-*-*"+jsonDestino.toString());

               try {
                   idZonaIncio=jsonOrigen.getString("idZona").toString();
                   idZonaFin=jsonDestino.getString("idZona").toString();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }else {
               idZonaIncio="-1";
               idZonaFin="-1";
           }
        fichero=new Fichero(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        configuracionServicio=fichero.ExtraerConfiguraciones();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String Tarifa="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
            request.addProperty("idZonaInicio",Integer.parseInt(idZonaIncio));
            request.addProperty("idZonaFinal",Integer.parseInt(idZonaFin));
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction7(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("responseTarifas",response1.toString());
            if(response1.hasProperty("return")){
               if(response1.getProperty("return")!=null){
                   Tarifa=response1.getProperty("return").toString();
               } else {
                   Log.d("tarifaResponse", "nulos-->");
                   Tarifa="";
               }
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.getMessage());
        }
        return Tarifa;
    }

    @Override
    protected void onPostExecute(String tarifa_) {
        super.onPostExecute(tarifa_);
        progressDialog.dismiss();
        if(tarifa_.length()!=0){
            AlertPedirServicio(tarifa_);
        }else {
            Toast.makeText(context,"No hay tarifas para esta zona",Toast.LENGTH_SHORT).show();
        }
       }

    private void AlertPedirServicio(final String tarifa) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final View view = CX.MAIN_ACTIVITY.getLayoutInflater().inflate(R.layout.activity_solicitar__servicio__cliente, null);
        String addresIni=compR.getAutoCompletText1().getText().toString().trim();
        String addressFin=compR.getAutoCompletText2().getText().toString().trim();
        compR.Contros_Alert_Pedir_Servicio(view);
        alertDialogBuilder.setView(view);
        compR.getTxtTarifa().setText("S/. "+tarifa);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        final Calendar calendar = Calendar.getInstance();
        int   yyyy = calendar.get(Calendar.YEAR);
        int   mm =  calendar.get(Calendar.MONTH);
        int   dd = calendar.get(Calendar.DAY_OF_MONTH);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int  ss = calendar.get(Calendar.MINUTE);

        mostrarFechaEdit(dd,mm,yyyy,hh,ss);
        compR.getCheckBoxAire_No().setChecked(true);
        compR.getCheckBoxAutoEconomico().setChecked(true);


        compR.getCheckBoxFecha().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ExtracDate();
                } else {
                }
            }
        });
        compR.getCheckBoxHora().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ExtraerHora();
                }
            }
        });

        compR.getCheckBoxAire_Si().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String costoAire="0.00";
                    if (configuracionServicio!=null){
                        try {
                            costoAire=configuracionServicio.getString("impAireAcondicionado");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            costoAire="0.00";
                        }
                    }else {
                        costoAire="0.00";
                    }
                    compR.getCheckBoxAire_No().setChecked(false);
                    compR.getTxtCostoAire().setVisibility(View.VISIBLE);
                    compR.getTxtCostoAire().setText("S/. "+costoAire);
                }else {
                    compR.getTxtCostoAire().setText("S/.0.00");
                    compR.getTxtCostoAire().setVisibility(View.GONE);
                }


            }
        });
        compR.getCheckBoxAire_No().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    compR.getCheckBoxAire_Si().setChecked(false);
                }
            }
        });
        compR.getCheckBoxAutoVip().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String costoAutoVip="0.00";
                    if (configuracionServicio!=null){
                        try {
                            costoAutoVip=configuracionServicio.getString("impAutoVip");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            costoAutoVip="0.00";
                        }
                    }else {
                        costoAutoVip="0.00";
                    }

                    compR.getCheckBoxAutoEconomico().setChecked(false);
                    compR.getTxtCostoAutoTipoSoliciot().setVisibility(View.VISIBLE);
                    compR.getTxtCostoAutoTipoSoliciot().setText("S/. "+costoAutoVip);

                }else {
                    compR.getTxtCostoAutoTipoSoliciot().setVisibility(View.GONE);
                    compR.getTxtCostoAutoTipoSoliciot().setText("S/ 0.00");
                }
            }
        });

        compR.getCheckBoxAutoEconomico().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    compR.getCheckBoxAutoVip().setChecked(false);
                }

            }
        });


        compR.getBtnConfirmarServicio().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String fechaIngreso = compR.getEditFechaServicio().getText().toString();
                String horaIngreso = compR.getEditHoraServicio().getText().toString();
                if (compR.getCheckBoxFecha().isChecked() && compR.getCheckBoxHora().isChecked()) {
                    String idAire="0";
                    String precioAire="";
                    String idTipoAuto="2";
                    String importeTipoAuto="0";
                    if(jsonOrigen!=null && jsonDestino!=null){
                        if(compR.getCheckBoxAire_Si().isChecked()){
                            idAire="1";//DESEA AIRE ACONDICIONADO

                            if (configuracionServicio!=null){
                                try {
                                    precioAire=configuracionServicio.getString("impAireAcondicionado");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    precioAire="";
                                }
                            }else {
                                precioAire="";
                            }
                        }else {
                            idAire="0";//NO DESEA AIRE ACONDICIONADO
                        }
                        if (compR.getCheckBoxAutoVip().isChecked()){
                            idTipoAuto="1";//DESA AUTO VIP
                            try {
                                importeTipoAuto=configuracionServicio.getString("impAutoVip");
                            } catch (JSONException e) {
                                importeTipoAuto="0";
                                e.printStackTrace();
                            }
                        }else {
                            idTipoAuto="2";//DESA AUTO ECONOMICO
                            importeTipoAuto="0";
                        }
                        new wsValidarHoraServicio(context, fechaIngreso, horaIngreso,
                                                        jsonOrigen,
                                                        jsonDestino,
                                                        tarifa,
                                                        idAire,
                                                        precioAire,
                                                        idTipoAuto,
                                                        importeTipoAuto,
                                                        alertDialog).execute();
                    }else {
                        Log.d("jsonOringenDestion","nulll chekar");
                    }

                } else {
                    Toast.makeText(context, "Seleccione Hora o Fecha", Toast.LENGTH_SHORT).show();
                }
            }
        });
        compR.getImageButtonCerrarServicio().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        compR.getmAutocompleteView_AddressIncio().setText(addresIni);
        compR.getmAutocompleteView_AddressFinal().setText(addressFin);
      //  alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

    }


    private  void ExtraerHora(){
        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        TimePickerDialog timerHora=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              /*  compR.getEditHoraServicio().setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                Log.d("hora",String.valueOf(hourOfDay)+":"+String.valueOf(minute));*/
                if(hourOfDay>=10 && minute>=10){
                    compR.getEditHoraServicio().setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                }else if(hourOfDay>=10 && minute<10){
                    compR.getEditHoraServicio().setText(String.valueOf(hourOfDay)+":0"+String.valueOf(minute));
                }else if(hourOfDay<10 && minute>=10){
                    compR.getEditHoraServicio().setText("0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                }else if(hourOfDay<10 && minute<10){
                    compR.getEditHoraServicio().setText("0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute));
                }
            }
        },hora,minuto,true);
        timerHora.show();
    }

    private void mostrarFechaEdit(int dd,int mm,int yyyy, int hh, int ss){
        if(dd>=10  && (mm+1)>=10){
            compR.getEditFechaServicio().setText(dd + "-"+ (mm + 1) + "-" + yyyy);

        } else if (dd >= 10 && (mm + 1) < 10) {
            compR.getEditFechaServicio().setText(dd + "-0"+ (mm + 1) + "-" + yyyy);
        } else if (dd < 10 && (mm + 1) >= 10) {
            compR.getEditFechaServicio().setText("0" + dd + "-"+ (mm + 1) + "-" + yyyy);
        }else if (dd<10 && (mm+1)<10){
            compR.getEditFechaServicio().setText("0"+dd + "-0"+ (mm + 1) + "-" + yyyy);
        }
        if(hh>=10 && ss>=10){
            compR.getEditHoraServicio().setText(String.valueOf(hh)+":"+String.valueOf(ss));
        }else if(hh>=10 && ss<10){
            compR.getEditHoraServicio().setText(String.valueOf(hh)+":0"+String.valueOf(ss));
        }else if(hh<10 && ss>=10){
            compR.getEditHoraServicio().setText("0"+String.valueOf(hh)+":"+String.valueOf(ss));
        }else if(hh<10 && ss<10){
            compR.getEditHoraServicio().setText("0"+String.valueOf(hh)+":0"+String.valueOf(ss));
        }


    }

    private void ExtracDate(){
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        if(dayOfMonth>=10  && (monthOfYear+1)>=10){
                            compR.getEditFechaServicio().setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }else if(dayOfMonth>=10 && (monthOfYear+1)<10){
                            compR.getEditFechaServicio().setText(dayOfMonth + "-0"
                                    + (monthOfYear + 1) + "-" + year);
                        }else if(dayOfMonth<10 && (monthOfYear+1)>=10){
                            compR.getEditFechaServicio().setText("0"+dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                        }else if (dayOfMonth<10 && (monthOfYear+1)<10){
                            compR.getEditFechaServicio().setText("0"+dayOfMonth + "-0"
                                    + (monthOfYear + 1) + "-" + year);
                        }

                    }


                }, mYear, mMonth, mDay);
        dpd.show();
    }



}
