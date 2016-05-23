package com.nucleosis.www.appclientetaxibigway.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nucleosis.www.appclientetaxibigway.Adpaters.GridAdapterHistoricoServicios;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantsWS;
import com.nucleosis.www.appclientetaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appclientetaxibigway.SharedPreferences.PreferencesCliente;
import com.nucleosis.www.appclientetaxibigway.beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appclientetaxibigway.beans.beansServiciosFechaDetalle;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InterruptedIOException;
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
   private  Drawable drawable;
    private Fichero fichero;
    private JSONObject jsonConfiguraciones;
    private String urlConductor;
    public static List<beansHistorialServiciosCreados> ListServicios;
    private ArrayList<beansServiciosFechaDetalle> listDetalleServicio;
    private ProgressDialog progesDialog;
    private int TIME_OUT=15000;
    private boolean tiempoEsperaConexion=false;
    @SuppressWarnings("deprecation")
    public wsListaServiciosCliente(Context context,GridViewWithHeaderAndFooter grid,String fecha) {
        this.grid = grid;
        this.context = context;
        this.fecha=fecha;
        fichero=new Fichero(context);

       // drawable=context.getResources().getDrawable(R.drawable.ic_room_black_24dp);
        jsonConfiguraciones=fichero.ExtraerConfiguraciones();
        if(jsonConfiguraciones!=null){
            try {
                urlConductor=jsonConfiguraciones.getString("urlFotoConductor");
                Log.d("urlCon",urlConductor.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                urlConductor="";
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preferencesCliente=new PreferencesCliente(context);
        ListServicios=new ArrayList<beansHistorialServiciosCreados>();
        listDetalleServicio=new ArrayList<beansServiciosFechaDetalle>();
        listDetalleServicio.clear();
        ListServicios.clear();
        progesDialog=new ProgressDialog(context);
        progesDialog.setMessage("Cargando...");
        progesDialog.show();
    }
    @SuppressWarnings("deprecation")
    @Override
    protected List<beansHistorialServiciosCreados> doInBackground(String... params) {
        beansHistorialServiciosCreados row=null;

        beansServiciosFechaDetalle row2=null;
        SoapObject request = new SoapObject(ConstantsWS.getNameSpace(),ConstantsWS.getMethodo11());
        SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        //    Log.d("datosUser_",user.getUser()+"\n"+user.getPassword());
        request.addProperty("idCliente", Integer.parseInt(preferencesCliente.OpenIdCliente()));
        request.addProperty("fecServicio", fecha);
        request.addProperty("idConductor", "");
        request.addProperty("idEstadoServicio", "");
        envelope.setOutputSoapObject(request);

        try {
            HttpTransportSE httpTransport = new HttpTransportSE(ConstantsWS.getURL(),TIME_OUT);
          /*  ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(ConstantsWS.getSoapAction11(), envelope, headerPropertyArrayList);*/
             httpTransport.call(ConstantsWS.getSoapAction1(), envelope);
            SoapObject response1= (SoapObject) envelope.bodyIn;
            Vector<?> responseVector = (Vector<?>) response1.getProperty("return");
            int countVector=responseVector.size();
            for(int i=0;i<countVector;i++){
                SoapObject dataVector=(SoapObject)responseVector.get(i);
                row=new beansHistorialServiciosCreados();
                row2=new beansServiciosFechaDetalle();
                row.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                row.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                row.setFechaFormat(dataVector.getProperty("FEC_SERVICIO_YMD").toString());

                row.setHora(dataVector.getProperty("DES_HORA").toString());
                row.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                row.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());
                //IND_AIRE_ACONDICIONADO
                row.setIndAireAcondicionado(dataVector.getProperty("IND_AIRE_ACONDICIONADO").toString());
                row.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                row.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
                row.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());

                if(dataVector.getProperty("DES_FOTO").toString()!=null){
                    row.setNameFotoConductor(dataVector.getProperty("DES_FOTO").toString());
                }else {
                    row.setNameFotoConductor("");
                }

                row.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                row.setNameDistritoIncio(dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                row.setNameZonaIncio(dataVector.getProperty("NOM_ZONA_INICIO").toString());
                row.setNameDistritoFIn(dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                row.setNameZonaFin(dataVector.getProperty("NOM_ZONA_FIN").toString());
                row.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                row.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                if(dataVector.getProperty("NOM_APE_CONDUCTOR").toString()!=null){
                    row.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                }else {
                    row.setNombreConductor("");
                }
                row.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());

                row.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());

                row.setInfoAddress("-"+dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                        + "\n-" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());

                row.setIdCliente(dataVector.getProperty("ID_CLIENTE").toString());
                if(dataVector.getProperty("ID_CONDUCTOR").toString()!=null){
                    row.setIdConductor(dataVector.getProperty("ID_CONDUCTOR").toString());
                }else {
                    row.setIdConductor("");
                }
                if(dataVector.getProperty("ID_AUTO_TIPO").toString()!=null){
                    row.setIdTipoAuto(dataVector.getProperty("ID_AUTO_TIPO").toString());
                }else {
                    row.setIdTipoAuto("");
                }
                if(dataVector.getProperty("NUM_CELULAR").toString()!=null){
                    row.setNumberCelularCliente(dataVector.getProperty("NUM_CELULAR").toString());
                }else {
                    row.setNumberCelularCliente("");
                }
                if(dataVector.getProperty("DES_AUTO_TIPO").toString()!=null){
                    row.setDesAutoTipo(dataVector.getProperty("NUM_CELULAR").toString());
                }else {
                    row.setDesAutoTipo("");
                }
                if(dataVector.getProperty("NUM_MOVIL").toString()!=null){
                    row.setNumeroMovilTaxi(dataVector.getProperty("NUM_MOVIL").toString());
                }else {
                    row.setNumeroMovilTaxi("");
                }
                if(dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString()!=null){
                    row.setDesAutoTipoCliente(dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString());
                }else {
                    row.setDesAutoTipoCliente("");
                }
                if(dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString()!=null){
                    row.setIdTipoAutoPidioCliente(dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString());
                }else {
                    row.setIdTipoAutoPidioCliente("");
                }
            //dataVector.getProperty("ID_ESTADO_SERVICIO").toString()

                if(dataVector.hasProperty("ID_ESTADO_SERVICIO")){
                    String idStadoServicio=dataVector.getProperty("ID_ESTADO_SERVICIO").toString();
                    if(idStadoServicio.equals("1")){
                        //PENDIENTE
                        drawable=context.getResources().getDrawable(R.drawable.shape_stado_servicio_creado);
                        row.setImageStatusServicio(drawable);
                    }else if(idStadoServicio.equals("2")){
                        //STATDO ACEPTADO
                        drawable=context.getResources().getDrawable(R.drawable.shape_stado_aceptado);
                        row.setImageStatusServicio(drawable);
                    }else if(idStadoServicio.equals("3")){
                        //STADO EN RUTA CON CLIENTE
                        drawable=context.getResources().getDrawable(R.drawable.shape_green);
                        row.setImageStatusServicio(drawable);
                    }else if(idStadoServicio.equals("4")){
                        //TERMINADO CORRECTARMENTE EL SERVCIO
                        drawable=context.getResources().getDrawable(R.drawable.shape_blue);
                        row.setImageStatusServicio(drawable);
                    }else if(idStadoServicio.equals("5")){
                        // NO TERMINADO
                        drawable=context.getResources().getDrawable(R.drawable.shape_yellow);
                        row.setImageStatusServicio(drawable);
                    }else if(idStadoServicio.equals("6")){
                        //CANCELADO POR EL CLIENTE
                        drawable=context.getResources().getDrawable(R.drawable.shape_red_cliente);
                        row.setImageStatusServicio(drawable);
                    }



                }

                ////////////////////////////////////////////////////////
                row2.setIdServicio(dataVector.getProperty("ID_SERVICIO").toString());
                row2.setFecha(dataVector.getProperty("FEC_SERVICIO").toString());
                row2.setFechaFormat(dataVector.getProperty("FEC_SERVICIO_YMD").toString());

                row2.setHora(dataVector.getProperty("DES_HORA").toString());
                row2.setImporteServicio(dataVector.getProperty("IMP_SERVICIO").toString());
                row2.setDescripcionServicion(dataVector.getProperty("DES_SERVICIO").toString());
                //IND_AIRE_ACONDICIONADO
                row2.setIndAireAcondicionado(dataVector.getProperty("IND_AIRE_ACONDICIONADO").toString());
                row2.setImporteAireAcondicionado(dataVector.getProperty("IMP_AIRE_ACONDICIONADO").toString());
                row2.setImportePeaje(dataVector.getProperty("IMP_PEAJE").toString());
                row2.setNumeroMinutoTiempoEspera(dataVector.getProperty("NUM_MINUTO_TIEMPO_ESPERA").toString());

               /* if(dataVector.getProperty("DES_FOTO").toString()!=null){
                    row.setNameFotoConductor(dataVector.getProperty("DES_FOTO").toString());
                }else {
                    row.setNameFotoConductor("");
                }*/

                row2.setImporteTiempoEspera(dataVector.getProperty("IMP_TIEMPO_ESPERA").toString());
                row2.setNameDistritoInicio(dataVector.getProperty("NOM_DISTRITO_INICIO").toString());
                row2.setNameZonaIncio(dataVector.getProperty("NOM_ZONA_INICIO").toString());
                row2.setNameDistritoFin(dataVector.getProperty("NOM_DISTRITO_FIN").toString());
                row2.setNameZonaFin(dataVector.getProperty("NOM_ZONA_FIN").toString());
                row2.setDireccionIncio(dataVector.getProperty("DES_DIRECCION_INICIO").toString());
                row2.setDireccionFinal(dataVector.getProperty("DES_DIRECCION_FINAL").toString());
                if(dataVector.getProperty("NOM_APE_CONDUCTOR").toString()!=null){
                    row2.setNombreConductor(dataVector.getProperty("NOM_APE_CONDUCTOR").toString());
                }else {
                    row2.setNombreConductor("");
                }
                row2.setStatadoServicio(dataVector.getProperty("ID_ESTADO_SERVICIO").toString());
                row2.setNombreStadoServicio(dataVector.getProperty("NOM_ESTADO_SERVICIO").toString());

                row2.setInfoAddress(dataVector.getProperty("DES_DIRECCION_INICIO").toString()
                        + "\n" + dataVector.getProperty("DES_DIRECCION_FINAL").toString());

                row2.setIdCliente(dataVector.getProperty("ID_CLIENTE").toString());
                if(dataVector.getProperty("ID_CONDUCTOR").toString()!=null){
                    row2.setIdConductor(dataVector.getProperty("ID_CONDUCTOR").toString());
                }else {
                    row2.setIdConductor("");
                }
                if(dataVector.getProperty("ID_AUTO_TIPO").toString()!=null){
                    row2.setIdTipoAuto(dataVector.getProperty("ID_AUTO_TIPO").toString());
                }else {
                    row2.setIdTipoAuto("");
                }
                if(dataVector.getProperty("NUM_CELULAR").toString()!=null){
                    row2.setNucCelularCliente(dataVector.getProperty("NUM_CELULAR").toString());
                }else {
                    row2.setNucCelularCliente("");
                }
                if(dataVector.getProperty("DES_AUTO_TIPO").toString()!=null){
                    row2.setDesAutoTipo(dataVector.getProperty("DES_AUTO_TIPO").toString());
                }else {
                    row2.setDesAutoTipo("");
                }

                if(dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString()!=null){
                    row2.setDesAutoTipoPidioCliente(dataVector.getProperty("DES_AUTO_TIPO_PIDIO_CLIENTE").toString());
                }else {
                    row2.setDesAutoTipoPidioCliente("");
                }
                if(dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString()!=null){
                    row2.setIdAutoTipoPidioCliente(dataVector.getProperty("ID_AUTO_TIPO_PIDIO_CLIENTE").toString());
                }else {
                    row2.setIdAutoTipoPidioCliente("");
                }

                listDetalleServicio.add(row2);
                ListServicios.add(row);
            }

            Gson json=new Gson();
            String listServiciosCliente=json.toJson(listDetalleServicio);
            // Log.d("lista car-->",listServiciosCliente.toString());
            if(listServiciosCliente!=null){
                fichero.InsertarListaServiciosTomados(listServiciosCliente.toString());
            }else {
                fichero.InsertarListaServiciosTomados("");
            }
          // SoapObject response2=(SoapObject)response1.getProperty("return");
            Log.d("listaxxx", responseVector.toString());
            Log.d("sizeVector", String.valueOf(responseVector.size()));
        } catch (InterruptedIOException e){
            tiempoEsperaConexion=true;
        }

        catch (Exception e) {
            e.printStackTrace();
//            Log.d("error", e.getMessage());
        }
        return ListServicios;
    }

    @Override
    protected void onPostExecute(List<beansHistorialServiciosCreados> listServis) {
        super.onPostExecute(listServis);
        if(tiempoEsperaConexion){
            Toast.makeText(context,"No se puedo Conectar",Toast.LENGTH_LONG).show();
        }
        progesDialog.dismiss();
        grid.setAdapter(new GridAdapterHistoricoServicios(context,listServis));
    }
}
