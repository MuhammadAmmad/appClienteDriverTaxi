package com.nucleosis.www.appclientetaxibigway.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.lopez on 28/04/2016.
 */


/*<fecServicio xsi:type="xsd:string">?</fecServicio>
        <desHora xsi:type="xsd:string">?</desHora>
        <impServicio xsi:type="xsd:string">?</impServicio>
        <desServicio xsi:type="xsd:string">?</desServicio>
        <impAireAcondicionado xsi:type="xsd:string">?</impAireAcondicionado>
        <usrRegistro xsi:type="xsd:string">?</usrRegistro>
        <idCliente xsi:type="xsd:string">?</idCliente>
        <idTurno xsi:type="xsd:int">?</idTurno>
        <idConductor xsi:type="xsd:int">?</idConductor>
        <idAuto xsi:type="xsd:int">?</idAuto>
        <idDistritoInicio xsi:type="xsd:int">?</idDistritoInicio>
        <idZonaInicio xsi:type="xsd:int">?</idZonaInicio>
        <desDireccionInicio xsi:type="xsd:int">?</desDireccionInicio>
        <idDistritoFinal xsi:type="xsd:int">?</idDistritoFinal>
        <idZonaFinal xsi:type="xsd:int">?</idZonaFinal>
        <desDireccionFinal xsi:type="xsd:int">?</desDireccionFinal>*/

public class wsCrearServicioClient extends AsyncTask<String,String,String> {

    private Context context;

    public wsCrearServicioClient(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        dataClienteSigUp row=null;
        List<dataClienteSigUp> listCliente=null;
        Log.d("eta_aqui", "doInBackGround");
        String codLogin="";
        String msnLogin="";
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo8());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());

       /* request.addProperty("desHora", Email);
        request.addProperty("impServicio", Email);
        request.addProperty("desServicio", Email);
        request.addProperty("impAireAcondicionado", Email);
        request.addProperty("usrRegistro", Email);
        request.addProperty("idCliente", Email);
        request.addProperty("idTurno", Email);
        request.addProperty("idConductor", Email);
        request.addProperty("idAuto", Email);
        request.addProperty("idDistritoInicio", Email);
        request.addProperty("idZonaInicio", Email);
        request.addProperty("desDireccionInicio", Email);
        request.addProperty("idDistritoFinal", Email);
        request.addProperty("idZonaFinal", Email);
        request.addProperty("desDireccionFinal", Email);*/


        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction8(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            listCliente=new ArrayList<dataClienteSigUp>();
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Log.d("respuesSoap", response1.toString());
            SoapObject response2=(SoapObject)response1.getProperty("return");
            row=new dataClienteSigUp();
            row.setIdCliente(response2.getPropertyAsString("ID_CLIENTE").toString());
            row.setNombre(response2.getPropertyAsString("NOM_CLIENTE").toString());
            row.setaPaterno(response2.getPropertyAsString("APE_PATERNO").toString());
            row.setaMaterno(response2.getPropertyAsString("APE_MATERNO").toString());
            row.setDni(response2.getPropertyAsString("NUM_DOCUMENTO_IDENTIDAD").toString());
            row.setEmail(response2.getPropertyAsString("DES_EMAIL").toString());
            row.setCelular(response2.getPropertyAsString("NUM_CELULAR").toString());
            listCliente.add(row);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.getMessage());
        }




        return null;
    }
}
