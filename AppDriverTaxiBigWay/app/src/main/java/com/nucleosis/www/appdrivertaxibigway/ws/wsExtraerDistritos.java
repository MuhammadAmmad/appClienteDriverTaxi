package com.nucleosis.www.appdrivertaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansDistritos;
import com.nucleosis.www.appdrivertaxibigway.Constans.ConstantsWS;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by karlos on 14/05/2016.
 */
public class wsExtraerDistritos  extends AsyncTask<String,String, List<beansDistritos>> {

    private Context context;
    private beansDistritos beasDistrito_;
    public static List<beansDistritos> LISTA_DISTRITOS;
    private Fichero fichero;
    public wsExtraerDistritos( Context context ) {
        this.context=context;
        fichero=new Fichero(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LISTA_DISTRITOS=new ArrayList<beansDistritos>();
    }

    @Override
    protected  List<beansDistritos> doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo16());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction16(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);

            SoapObject response1= (SoapObject) envelope.bodyIn;
            // Log.d("respuest-->", response1.toString());
            Vector<?> responseVector = (Vector<?>) response1.getProperty(0);
            //  Log.d("vector-->", String.valueOf(responseVector.size()));
            int  count=responseVector.size();
            for (int i = 0; i <count; ++i) {
                SoapObject test=(SoapObject)responseVector.get(i);
                beasDistrito_=new beansDistritos();
                //  Log.d("nomDistrito_ ",test.getProperty("ID_DISTRITO").toString()+"-->"+test.getProperty("NOM_DISTRITO").toString());
                beasDistrito_.setIdDistrito(test.getProperty("ID_DISTRITO").toString());
                beasDistrito_.setNameDistrito(test.getProperty("NOM_DISTRITO").toString());
                LISTA_DISTRITOS.add(beasDistrito_);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //  Log.d("error", e.getMessage());
        }
        return LISTA_DISTRITOS;
    }

    @Override
    protected void onPostExecute(List<beansDistritos> listaDistritos) {
        super.onPostExecute(listaDistritos);

        Gson json=new Gson();
        if(listaDistritos!=null){
            String listDistritoJson=json.toJson(listaDistritos);
            fichero.InsertarListaDistritos(listDistritoJson);
            Log.d("listDistritos",fichero.ExtraerListDistritos().toString());
        }




    }
}
