package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

        Log.d("dataJon",jsonOrigen.toString()+"-*-*-*"+jsonDestino.toString());
    }

    @Override
    protected String doInBackground(String... params) {
        String Tarifa="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        try {
            request.addProperty("idZonaInicio",Integer.parseInt(jsonOrigen.getString("idZona").toString()));
            request.addProperty("idZonaFinal",Integer.parseInt(jsonDestino.getString("idZona").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        if(tarifa_.length()!=0){
            AlertPedirServicio(tarifa_);
        }else {
            Toast.makeText(context,"No hay tarifas para esta zona",Toast.LENGTH_SHORT).show();
        }
       }

    private void AlertPedirServicio(final String tarifa) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final View view = CX.MAIN_ACTIVITY.getLayoutInflater().inflate(R.layout.activity_solicitar__servicio__cliente, null);
        String addresIni=compR.getEditAddresss().getText().toString().trim();
        String addressFin=compR.getEditAddresssFinal().getText().toString().trim();
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

                    new wsValidarHoraServicio(context, fechaIngreso, horaIngreso,jsonOrigen,jsonDestino,tarifa,alertDialog).execute();
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
