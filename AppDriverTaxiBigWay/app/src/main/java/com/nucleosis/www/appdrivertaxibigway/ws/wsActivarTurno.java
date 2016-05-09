package com.nucleosis.www.appdrivertaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Beans.beansDataDriver;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.locationDriver;
import com.nucleosis.www.appdrivertaxibigway.SharedPreferences.PreferencesDriver;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karlos on 02/05/2016.
 */
public class wsActivarTurno  extends AsyncTask<String,String,String[]>{
    private Context context;
    private  int idVehiculo;
    private PreferencesDriver preferencesDriver;
    private componentesR compR;

    public wsActivarTurno(Context context,int idVehiculo) {
        this.context = context;
        this.idVehiculo=idVehiculo;
        preferencesDriver=new PreferencesDriver(context);
        compR=new componentesR(context);
        Activity activity= (Activity) context;
        compR.Contros_main_activity(activity);
    }

    @Override
    protected String[] doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo4());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        String[] dataSalida=new String[2];
        request.addProperty("idConductor", Integer.parseInt(preferencesDriver.OpenIdDriver()));
        request.addProperty("idAuto",idVehiculo);
        request.addProperty("usrRegistro", "");
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

     /*   <idConductor xsi:type="xsd:int">?</idConductor>
        <idAuto xsi:type="xsd:int">?</idAuto>
        <usrRegistro xsi:type="xsd:int">?</usrRegistro>
*/
        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction4(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2= (SoapObject)response1.getProperty("return");
              Log.d("responseTurno",response2.toString());
            if(response2.hasProperty("IND_OPERACION")){
                if(response2.getPropertyAsString("IND_OPERACION").equals("1")){
                    dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                    dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
                }else if(response2.getPropertyAsString("IND_OPERACION").equals("2")){
                    dataSalida[0]=response2.getPropertyAsString("IND_OPERACION");
                    dataSalida[1]=response2.getPropertyAsString("DES_MENSAJE");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.d("error", e.printStackTrace());
        }
        return dataSalida;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
        Toast.makeText(context,data[1],Toast.LENGTH_SHORT).show();
        if(data[0].equals("1")){
          //  compR.getBtnActivarTurno().setVisibility(View.GONE);
          //  compR.getBtnDesactivarTurno().setVisibility(View.VISIBLE);
         //   compR.getBtnAdicionales().setVisibility(View.VISIBLE);
            Intent intent=new Intent(context,locationDriver.class);
            context.startService(intent);
            preferencesDriver.InsertarIdVehiculo(String.valueOf(idVehiculo));
            Log.d("extraerIdAuto",preferencesDriver.ExtraerIdVehiculo());
        }else if(data[0].equals("2")){
           // compR.getBtnActivarTurno().setVisibility(View.VISIBLE);
          //  compR.getBtnDesactivarTurno().setVisibility(View.GONE);
          //  compR.getBtnAdicionales().setVisibility(View.GONE);
        }
    }
}
