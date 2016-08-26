package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.R;

import org.json.JSONArray;
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
public class wsExtraerPrecioZonaDistrito extends AsyncTask<String,String,String[]> {
    private Context context;
    private Fichero fichero;
    private JSONObject jsonOrigenClienteIncial;
    private JSONObject jsonOrigen;
    private JSONObject jsonDestino;
    private AlertDialog alertDialog;
    private JSONArray jsonDistritos;
    private String idServicio;
    private Activity activity;
    private String idDistritoIncio;
    private String idZonaIncio;
    private String idZonaFin;
    private int sw=0;
    private String idCliente;
    public wsExtraerPrecioZonaDistrito(Context context,AlertDialog alertDialog,String idServicio,String idCliente) {
        this.context = context;
        this.alertDialog=alertDialog;
        this.idServicio=idServicio;
        this.idCliente=idCliente;
        fichero=new Fichero(context);
        activity=(Activity)context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        jsonOrigen=fichero.ExtraerZonaIdDistrito_Origen();
        jsonDestino=fichero.ExtraerZonaIdDistrito_Destino();

        jsonDistritos=fichero.ExtraerListDistritos();
        if(jsonOrigen!=null && jsonDestino!=null){
            Log.d("oringeAdrres_detnio",jsonOrigen.toString()+"--->"+jsonDestino.toString());
            Log.d("distritosImpresos",jsonDistritos.toString());
        }

        try {
            if(jsonOrigen==null){
                jsonOrigenClienteIncial=fichero.EXTRAER_IdZONA_IDDISTRITO_INCIO();
                Log.d("jsonCli_a",jsonOrigenClienteIncial.toString());
                idZonaIncio=jsonOrigenClienteIncial.getString("idZona");
                idDistritoIncio=jsonOrigenClienteIncial.getString("idDistrito");
                sw=1;
            }else if(jsonOrigen.getString("idZona").length()==0){
                jsonOrigenClienteIncial=fichero.EXTRAER_IdZONA_IDDISTRITO_INCIO();
                Log.d("jsonCli_b",jsonOrigenClienteIncial.toString());
                idZonaIncio=jsonOrigenClienteIncial.getString("idZona");
                idDistritoIncio=jsonOrigenClienteIncial.getString("idDistrito");
                sw=1;

            }else{
                idZonaIncio=jsonOrigen.getString("idZona");
                idDistritoIncio=jsonOrigen.getString("idDistrito");
                sw=0;
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    @Override
    protected String[] doInBackground(String... params) {
        String[] dataTarifa=new String[3];
        String Tarifa="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(), ConstantsWS.getMethodo15());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        try {
            request.addProperty("idZonaInicio",Integer.parseInt(idZonaIncio));
            request.addProperty("idZonaFinal",Integer.parseInt(jsonDestino.getString("idZona").toString()));
            request.addProperty("idCliente",Integer.parseInt(idCliente));
            Log.d("requesDistritos",request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction15(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("responseTarifas",response1.toString());
            if(response1.hasProperty("return")){
                if(response1.getProperty("return")!=null){
                    Tarifa=response1.getProperty("return").toString();
                    dataTarifa[0]=Tarifa;
                } else {
                    Log.d("tarifaResponse", "nulos-->");
                    dataTarifa[0]="";
                }
            }

            int y=0;
            int z=0;
            for(int i=0;i<jsonDistritos.length();i++){
                if(sw==0){
                    if(jsonDistritos.getJSONObject(i).getString("IdDistrito").equals(jsonOrigen.getString("idDistrito"))){
                        Log.d("NombreDistritoInicioA",jsonDistritos.getJSONObject(i).getString("NameDistrito"));
                        dataTarifa[1]=jsonDistritos.getJSONObject(i).getString("NameDistrito");
                        y=1;
                    }
                    if(jsonDistritos.getJSONObject(i).getString("IdDistrito").equals(jsonDestino.getString("idDistrito"))){
                        Log.d("NombreDistritoFinA",jsonDistritos.getJSONObject(i).getString("NameDistrito"));
                        dataTarifa[2]=jsonDistritos.getJSONObject(i).getString("NameDistrito");
                        z=1;
                    }
                }


                if (sw==1){
                    if(jsonDistritos.getJSONObject(i).getString("IdDistrito").equals(jsonOrigenClienteIncial.getString("idDistrito"))){
                        Log.d("NombreDistritoInicioB",jsonDistritos.getJSONObject(i).getString("NameDistrito"));
                        dataTarifa[1]=jsonDistritos.getJSONObject(i).getString("NameDistrito");
                        y=1;
                    }
                    if(jsonDistritos.getJSONObject(i).getString("IdDistrito").equals(jsonDestino.getString("idDistrito"))){
                        Log.d("NombreDistritoFinA",jsonDistritos.getJSONObject(i).getString("NameDistrito"));
                        dataTarifa[2]=jsonDistritos.getJSONObject(i).getString("NameDistrito");
                        z=1;
                    }

                }

            }
            if(y==0){
                dataTarifa[1]="Sin informacion";
            }
            if(z==0){
                dataTarifa[2]="Sin informacion";
            }


        } catch (Exception e) {
            e.printStackTrace();
            dataTarifa[0]="";
            dataTarifa[1]="vacio";
            dataTarifa[2]="vacio";
            Log.d("error", e.getMessage());
        }
        return dataTarifa;
    }

    @Override
    protected void onPostExecute(String[] dataTarifa) {
        super.onPostExecute(dataTarifa);
        if(dataTarifa!=null){
            LevantarAlert(dataTarifa);
        }

    }
    private void LevantarAlert(final String[] dataTarifa){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
      //  final View view = activity.getLayoutInflater().inflate(R.layout.view_alert_adicionales, null);
        dialogo1.setTitle("Nuevas Direcciones !!");
        JSONObject jsonOrigenAddres=fichero.ExtraerDireccionIncio();
        JSONObject jsonFinAddres=fichero.ExtraerDireccionFin();
        String tarifa_="";
    if(dataTarifa[0].length()==0){
        tarifa_="No hay tarifa !";
    }else {
        tarifa_=dataTarifa[0];
    }
        try {
            dialogo1.setMessage("\n"+"• "+jsonOrigenAddres.getString("addresOrigen")+
                                "\n\t\t"+"( "+dataTarifa[1]+" )"+
                                "\n"+
                                "\n"+"• "+jsonFinAddres.getString("addresDestino")+
                                "\n\t\t"+"( "+dataTarifa[2]+" )"+
                                "\n\n"+"S/. "+tarifa_);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                JSONObject jsonOrigenAddres=fichero.ExtraerDireccionIncio();
                JSONObject jsonFinAddres=fichero.ExtraerDireccionFin();
                try {
                    if(dataTarifa[0].length()!=0){
                        alertDialog.dismiss();
                        new wsActualizarServicio(
                                activity,
                                idServicio,
                                "",//indAireacondicionado
                                "",//aireAcondicionado
                                "",//peaje
                                "",//importe tiempo espera
                                "",//minutos tiempoEspera
                                dataTarifa[0],//importe servicio
                                idDistritoIncio,//idDistritoIncio
                                idZonaIncio,//idZonaIncio
                                jsonOrigenAddres.getString("addresOrigen"),//DireccionIncio
                                jsonDestino.getString("idDistrito"),//idDistritoFin
                                jsonDestino.getString("idZona"),//idZonaFin
                                jsonFinAddres.getString("addresDestino"),//DireccionFin
                                "",//Descripcion pago extraordinario
                                "",//IMPORTE GASTO EXTRAORDINARIO
                                ""//TIPO DE PAGO DE SERVCIO 1 CONTADO 2 CREDITO
                        ).execute();
                    }else {
                        Toast.makeText(context,"No tiene tarifa !!",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {


            }
        });
        dialogo1.show();
    }
}
