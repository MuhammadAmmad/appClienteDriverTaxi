package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * Created by karlos on 06/05/2016.
 */
public class wsVerificarServicioActivo extends AsyncTask<String,String,List<beansVerificadorDeServicioActivio>> {
    private Context context;
    private JSONObject jsonObject;
    private PreferencesDriver preferencesDriver;
    private List<beansVerificadorDeServicioActivio> listaVerificacionServicios;
    public wsVerificarServicioActivo( Context context,JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.context = context;
        preferencesDriver=new PreferencesDriver(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listaVerificacionServicios=new ArrayList<beansVerificadorDeServicioActivio>();
    }

    @Override
    protected List<beansVerificadorDeServicioActivio> doInBackground(String... params) {

        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo7());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());

        try {
            request.addProperty("idCliente", "");
            request.addProperty("fecServicio", jsonObject.getString("Fecha").toString());
            request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
            request.addProperty("idEstadoServicio", 2);
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
            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
            int countVector=responseVector.size();
            beansVerificadorDeServicioActivio row=null;
            for(int i=0; i<countVector;i++){
                SoapObject dataVector=(SoapObject)responseVector.get(i);
                row=new beansVerificadorDeServicioActivio();
                row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                row.setFechaServicio(dataVector.getProperty("FEC_SERVICIO").toString());
                row.setHoraServicio(dataVector.getProperty("DES_HORA").toString());
                listaVerificacionServicios.add(row);
            }
        }catch (Exception e){

        }
        return listaVerificacionServicios;
    }

    @Override
    protected void onPostExecute(List<beansVerificadorDeServicioActivio> listResult) {
        super.onPostExecute(listResult);
        if(listResult!=null){
            String jsonArray=new Gson().toJson(listResult);
            Log.d("jsonT",jsonArray.toString());
        }else {

        }
    }



}
class beansVerificadorDeServicioActivio{
    private String idServicio;
    private String fechaServicio;
    private String horaServicio;

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(String fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }
}