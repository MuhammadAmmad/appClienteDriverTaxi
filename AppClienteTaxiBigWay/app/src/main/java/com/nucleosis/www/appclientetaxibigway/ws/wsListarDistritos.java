package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nucleosis.www.appclientetaxibigway.Adpaters.AdapterDistritos;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Fragments.FragmentSolicitarServicioCliente;
import com.nucleosis.www.appclientetaxibigway.beans.beansDistritos;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class wsListarDistritos extends AsyncTask<String,String, List<beansDistritos>> {

    private Context context;
    private beansDistritos beasDistrito_;
    public static List<beansDistritos> LISTA_DISTRITOS;
    private Spinner spinerDistritos;
    private ComponentesR compR;
    private AdapterDistritos adapterDistritos;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public wsListarDistritos( FragmentManager fragmentManager) {
        this.fragmentManager=fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LISTA_DISTRITOS=new ArrayList<beansDistritos>();
    }

    @Override
    protected  List<beansDistritos> doInBackground(String... params) {
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo4());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction4(), envelope, headerPropertyArrayList);
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
                beasDistrito_.setIdDistrito(Integer.parseInt(test.getProperty("ID_DISTRITO").toString()));
                beasDistrito_.setNameDistrito(test.getProperty("NOM_DISTRITO").toString());
                LISTA_DISTRITOS.add(beasDistrito_);
            }
           // Log.d("vector-->",response2.toString());
           /* codLogin=response2.getPropertyAsString("CHECK_CONTRASENA").toString();
            msnLogin=response2.getPropertyAsString("DES_MENSAJE").toString();*/

            //  Log.d("response",response2.toString());
        } catch (Exception e) {
            e.printStackTrace();
          //  Log.d("error", e.getMessage());
        }
        return LISTA_DISTRITOS;
    }

    @Override
    protected void onPostExecute(List<beansDistritos> beansDistritoses) {
        super.onPostExecute(beansDistritoses);
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSolicitarServicioCliente fragmentSolicitarServicioCliente=new
                        FragmentSolicitarServicioCliente();
                //inboxFragment.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, fragmentSolicitarServicioCliente);
                fragmentTransaction.commit();

       /* if(beansDistritoses!=null){
            adapterDistritos=new AdapterDistritos(context,beansDistritoses);
            compR.getSpiner().setAdapter(adapterDistritos);
        }*/

    }
}
