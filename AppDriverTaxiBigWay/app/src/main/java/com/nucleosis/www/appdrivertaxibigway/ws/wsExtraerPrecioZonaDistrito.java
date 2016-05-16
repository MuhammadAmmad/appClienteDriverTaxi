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
    private JSONObject jsonOrigen;
    private JSONObject jsonDestino;
    private AlertDialog alertDialog;
    private JSONArray jsonDistritos;
    private String idServicio;
    private Activity activity;
    public wsExtraerPrecioZonaDistrito(Context context,AlertDialog alertDialog,String idServicio) {
        this.context = context;
        this.alertDialog=alertDialog;
        this.idServicio=idServicio;
        fichero=new Fichero(context);
        activity=(Activity)context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        jsonOrigen=fichero.ExtraerZonaIdDistrito_Origen();
        jsonDestino=fichero.ExtraerZonaIdDistrito_Destino();
        jsonDistritos=fichero.ExtraerListDistritos();
        Log.d("oringeAdrres_detnio",jsonOrigen.toString()+"--->"+jsonDestino.toString());
        Log.d("distritosImpresos",jsonDistritos.toString());
    }

    @Override
    protected String[] doInBackground(String... params) {
        String[] dataTarifa=new String[3];
        String Tarifa="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(), ConstantsWS.getMethodo15());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        try {
            request.addProperty("idZonaInicio",Integer.parseInt(jsonOrigen.getString("idZona").toString()));
            request.addProperty("idZonaFinal",Integer.parseInt(jsonDestino.getString("idZona").toString()));
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
                    dataTarifa[0]="No hay tarifas";
                }
            }

            int y=0;
            int z=0;
            for(int i=0;i<jsonDistritos.length();i++){

                if(jsonDistritos.getJSONObject(i).getString("IdDistrito").equals(jsonOrigen.getString("idDistrito"))){
                    Log.d("NombreDistritoInicio",jsonDistritos.getJSONObject(i).getString("NameDistrito"));
                    dataTarifa[1]=jsonDistritos.getJSONObject(i).getString("NameDistrito");
                    y=1;
                }
                if(jsonDistritos.getJSONObject(i).getString("IdDistrito").equals(jsonDestino.getString("idDistrito"))){
                    Log.d("NombreDistritoFin",jsonDistritos.getJSONObject(i).getString("NameDistrito"));
                    dataTarifa[2]=jsonDistritos.getJSONObject(i).getString("NameDistrito");
                    z=1;
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
            dataTarifa[0]="No hay tarifas";
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
           // Toast.makeText(context,dataTarifa[0],Toast.LENGTH_SHORT).show();
            LevantarAlert(dataTarifa);
        }

    }



    private void LevantarAlert(final String[] dataTarifa){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
      //  final View view = activity.getLayoutInflater().inflate(R.layout.view_alert_adicionales, null);
        dialogo1.setTitle("Nuevas Direcciones !!");
        JSONObject jsonOrigenAddres=fichero.ExtraerDireccionIncio();
        JSONObject jsonFinAddres=fichero.ExtraerDireccionFin();

        try {
            dialogo1.setMessage("\n"+"• "+jsonOrigenAddres.getString("addresOrigen")+
                                "\n\t\t"+"( "+dataTarifa[1]+" )"+
                                "\n"+
                                "\n"+"• "+jsonFinAddres.getString("addresDestino")+
                                "\n\t\t"+"( "+dataTarifa[2]+" )"+
                                "\n\n"+"S/. "+dataTarifa[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                alertDialog.dismiss();
                JSONObject jsonOrigenAddres=fichero.ExtraerDireccionIncio();
                JSONObject jsonFinAddres=fichero.ExtraerDireccionFin();
                try {
                    new wsActualizarServicio(
                            activity,
                            idServicio,
                            "",//indAireacondicionado
                            "",//aireAcondicionado
                            "",//peaje
                            "",//importe tiempo espera
                            "",//minutos tiempoEspera
                            dataTarifa[0],//importe servicio
                            jsonOrigen.getString("idDistrito"),//idDistritoIncio
                            jsonOrigen.getString("idZona"),//idZonaIncio
                            jsonOrigenAddres.getString("addresOrigen"),//DireccionIncio
                            jsonDestino.getString("idDistrito"),//idDistritoFin
                            jsonDestino.getString("idZona"),//idZonaFin
                            jsonFinAddres.getString("addresDestino")//DireccionFin
                    ).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                /*JSONObject borrarDataAddresIncio=new JSONObject();
                JSONObject borrarDataAddresFin=new JSONObject();
                try {
                    borrarDataAddresIncio.put("addresOrigen","Vuelva a intentarlo");
                    borrarDataAddresFin.put("addresDestino","Vuelva a intentarlo");
                    fichero.InsertaDireccionIncio(borrarDataAddresIncio.toString());
                    fichero.InsertaDireccionFin(borrarDataAddresFin.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
        dialogo1.show();
    }
}
