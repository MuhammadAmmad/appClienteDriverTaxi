package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by karlos on 14/05/2016.
 */
public class wsExtraerIdZonaIdDistrito extends AsyncTask<String,String, String[]> {
    private Context context;
    private String NameZona;
    private int CASO=0;
    Fichero fichero;
    private Activity activity;
    private ProgressDialog progressDialog;
    public wsExtraerIdZonaIdDistrito(Activity activity, String nameZona, int CASO) {
        this.activity = activity;
        this.NameZona = nameZona;
        this.CASO=CASO;
        context=(Context)activity;
        fichero =new Fichero(context);
        Log.d("nameCaso",nameZona+"///"+String.valueOf(CASO));

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(CASO==1 || CASO==2){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Cargando espere....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    @Override
    protected  String[] doInBackground(String... params) {
        Log.d("eta_aqui11", "doInBackGround");
        String codLogin="";
        String msnLogin="";
        String[] dataPuntos=dataPuntos=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo14());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());

        // BARRANCO
        // CARMEN DE LA LEGUA I

        request.addProperty("nomZona", NameZona);
        Log.d("nameZonaXXX--->",NameZona.toString());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction14(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2=(SoapObject)response1.getProperty("return");
            Log.d("Response_ZONAA", response2.toString());

            if (response2.hasProperty("ID_DISTRITO")){
                dataPuntos[0]=response2.getProperty("ID_DISTRITO").toString();
                dataPuntos[1]=response2.getProperty("ID_ZONA").toString();
            }

            if(response2.hasProperty("DES_MENSAJE")){
                dataPuntos[0]="-1";
                dataPuntos[1]="-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataPuntos[0]="-1";
            dataPuntos[1]="-1";
        }
        return dataPuntos;
    }

    @Override
    protected void onPostExecute(String[] dataOrigenDestino) {
        super.onPostExecute(dataOrigenDestino);
        if(CASO==1 || CASO==2){
            progressDialog.dismiss();
        }

        JSONObject jsonObject=null;
        Log.d("caso",String.valueOf(CASO));
        switch (CASO){
            case 0:

                break;
            case 1:
                fichero.InsertIdZonaIdDistrito_Origen(dataOrigenDestino);
                jsonObject=fichero.ExtraerZonaIdDistrito_Origen();
                Log.d("dataRetornoX-->",jsonObject.toString());
                break;
            case 2:

                fichero.InsertIdZonaIdDistrito_Destino(dataOrigenDestino);
                jsonObject=fichero.ExtraerZonaIdDistrito_Destino();
                Log.d("dataRetornoY-->", jsonObject.toString());
                break;

            case 3:
                JSONObject jsonIdDistritoIdZonaInicio=new JSONObject();
                try {
                    jsonIdDistritoIdZonaInicio.put("idDistrito",dataOrigenDestino[0]);
                    jsonIdDistritoIdZonaInicio.put("idZona",dataOrigenDestino[1]);
                    fichero.INSERTAR_IdZONA_IDDISTRITO_INCIO(jsonIdDistritoIdZonaInicio.toString());
                    Log.d("originalIdDistrito1", fichero.EXTRAER_IdZONA_IDDISTRITO_INCIO().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                JSONObject jsonIdDistritoIdZonaFin=new JSONObject();
                try {
                    jsonIdDistritoIdZonaFin.put("idDistrito",dataOrigenDestino[0]);
                    jsonIdDistritoIdZonaFin.put("idZona",dataOrigenDestino[1]);
                    fichero.INSERTAR_IdZONA_IDDISTRITO_FIN(jsonIdDistritoIdZonaFin.toString());
                    Log.d("originalIdDistrito2", fichero.EXTRAER_IdZONA_IDDISTRITO_FIN().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        //  Log.d("dataRetorno-->",jsonObject.toString());
    }
}
