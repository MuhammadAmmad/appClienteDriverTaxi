package com.nucleosis.www.appclientetaxibigway.ws;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.nucleosis.www.appclientetaxibigway.Adpaters.GridAdapterHistoricoServicios;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.beansHistorialServiciosCreados;
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
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 03/05/2016.
 */
public class wsListaServiciosCliente extends AsyncTask<String,String,List<beansHistorialServiciosCreados>> {
    private Context context;
    private GridViewWithHeaderAndFooter grid;
    private PreferencesCliente preferencesCliente;
    private String fecha;
    Drawable drawable;
    public static List<beansHistorialServiciosCreados> ListServicios;
    @SuppressWarnings("deprecation")
    public wsListaServiciosCliente(Context context,GridViewWithHeaderAndFooter grid,String fecha) {
        this.grid = grid;
        this.context = context;
        this.fecha=fecha;
         drawable=context.getResources().getDrawable(R.drawable.ic_room_black_24dp);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preferencesCliente=new PreferencesCliente(context);
        ListServicios=new ArrayList<beansHistorialServiciosCreados>();
        ListServicios.clear();
    }

    @Override
    protected List<beansHistorialServiciosCreados> doInBackground(String... params) {
        beansHistorialServiciosCreados row=null;
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        request.addProperty("idCliente", Integer.parseInt(preferencesCliente.OpenIdCliente()));
        request.addProperty("fecServicio", fecha);
        request.addProperty("idConductor", "");
        request.addProperty("idEstadoServicio", "");
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL());

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);
            // httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
            int countVector=responseVector.size();
            for(int i=0;i<countVector;i++){
                SoapObject dataVector=(SoapObject)responseVector.get(i);
                row=new beansHistorialServiciosCreados();
                row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                row.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                row.setHora(dataVector.getProperty("DES_HORA").toString());
                row.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                row.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());
                row.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
               // row.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
               // row.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());
               // row.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                row.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                row.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
              //  row.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                row.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                row.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());
                row.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                        +"\n"+dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                row.setImageHistorico(drawable);
                ListServicios.add(row);
            }
          // SoapObject response2=(SoapObject)response1.getProperty("return");
            Log.d("listaxxx", responseVector.toString());
            Log.d("sizeVector", String.valueOf(responseVector.size()));
        } catch (Exception e) {
            e.printStackTrace();
//            Log.d("error", e.getMessage());
        }
        return ListServicios;
    }

    @Override
    protected void onPostExecute(List<beansHistorialServiciosCreados> listServis) {
        super.onPostExecute(listServis);
        grid.setAdapter(new GridAdapterHistoricoServicios(context,listServis));
    }
}
