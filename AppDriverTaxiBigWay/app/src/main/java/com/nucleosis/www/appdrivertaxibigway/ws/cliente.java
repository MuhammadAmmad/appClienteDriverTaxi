package com.nucleosis.www.appdrivertaxibigway.ws;

import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appdrivertaxibigway.Beans.Multiplicacion;
import com.nucleosis.www.appdrivertaxibigway.Beans.MultiplicacionRequest;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by karlos on 20/03/2016.
 */
public class cliente extends AsyncTask<String,String,String> {
    static  final String NAMESPACE="http://springxt.io/soapserver/web-service-prueba";
    static final String PUERTO="PuertoLocal_1/";
    static  final String URL="http://192.168.1.100:8383/www/ws/suma.wsdl";
    static  final String METHODO="multiplicacionRequest";
    static  final String SOAP_ACTION=NAMESPACE+"/"+METHODO;
    @Override
    protected String doInBackground(String... params) {
        SoapObject request = new SoapObject(NAMESPACE,METHODO);
        MultiplicacionRequest m1=new MultiplicacionRequest();
            m1.setNumero1("5");
            PropertyInfo pi1=new PropertyInfo();
            pi1.setName("N1");
            pi1.setValue(m1);
            pi1.setType(m1.getClass());
            request.addProperty(pi1);

        MultiplicacionRequest m2=new MultiplicacionRequest();
            m2.setNumero1("23");
            PropertyInfo pi2=new PropertyInfo();
            pi2.setName("N2");
            pi2.setValue(m2);
            pi2.setType(MultiplicacionRequest.class);
            request.addProperty(pi2);

        /*request.addProperty("N1",5.8);
        request.addProperty("n2",3.5);*/
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;

        envelope.addMapping(NAMESPACE, MultiplicacionRequest.class.getSimpleName(), MultiplicacionRequest.class);
        envelope.setOutputSoapObject(request);
        envelope.implicitTypes = true;
        //  MarshalDouble md=new MarshalDouble();
        // md.register(envelope);
        Marshal floatMarshal = new MarshalFloat();
        floatMarshal.register(envelope);
        HttpTransportSE httpTransportSE=new HttpTransportSE(URL);
        httpTransportSE.debug = true;
        try {
            Log.d("transport_ ", request.toString());
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
            String xlm1=httpTransportSE.requestDump;
            String xlm2=httpTransportSE.responseDump;
            Log.d("response ",response.toString()+"\n"+xlm1.toString()+"\n"+xlm2.toString());
        }catch (Exception e){
            e.printStackTrace();
            Log.d("error !!!",e.toString());
        }
        return null;
    }
}
