package com.nucleosis.www.appclientetaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
/**
 * Created by carlos.lopez on 27/04/2016.
 */
public class wsExtraerIdZonaIdDistrito extends AsyncTask<String,String, String[]> {
    private Context context;
    private String NameZona;
    private int CASO=0;
    Fichero fichero;
    public wsExtraerIdZonaIdDistrito(Context context, String nameZona,int CASO) {
        this.context = context;
        this.NameZona = nameZona;
        this.CASO=CASO;
        fichero =new Fichero(context);
    }

    @Override
    protected  String[] doInBackground(String... params) {
        Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String msnLogin="";
        String[] dataPuntos=dataPuntos=new String[2];
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo6());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());

        // BARRANCO
        // CARMEN DE LA LEGUA I

        request.addProperty("nomZona", NameZona);
        Log.d("nameZona--->",NameZona.toString());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction6(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            SoapObject response2=(SoapObject)response1.getProperty("return");
            Log.d("Response_nameZona", response2.toString());

            if (response2.hasProperty("ID_DISTRITO")){
                dataPuntos[0]=response2.getProperty("ID_DISTRITO").toString();
            }else {
                dataPuntos[0]="-1";
            }
            if (response2.hasProperty("ID_ZONA")){
                dataPuntos[1]=response2.getProperty("ID_ZONA").toString();
            }else {
                dataPuntos[1]="-1";
            }
            if(response2.hasProperty("DES_MENSAJE")){
                dataPuntos[0]="-1";
                dataPuntos[1]="-1";
            }else {

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
        JSONObject jsonObject=null;
        Log.d("caso",String.valueOf(CASO));
       switch (CASO){
           case 0:
               break;
           case 1:
               fichero.InsertIdZonaIdDistrito_Origen(dataOrigenDestino);
               jsonObject = fichero.ExtraerZonaIdDistrito_Origen();
               if(jsonObject!=null) {
                   Log.d("dataRetornoX-->", jsonObject.toString());
               }
               break;
           case 2:
               fichero.InsertIdZonaIdDistrito_Destino(dataOrigenDestino);
               jsonObject=fichero.ExtraerZonaIdDistrito_Destino();
               if(jsonObject!=null){
                   Log.d("dataRetornoY-->", jsonObject.toString());
               }

               break;
       }
      //  Log.d("dataRetorno-->",jsonObject.toString());
    }
}
