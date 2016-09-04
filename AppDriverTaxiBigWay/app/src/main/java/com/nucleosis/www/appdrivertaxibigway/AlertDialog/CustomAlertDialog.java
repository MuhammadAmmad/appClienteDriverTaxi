package com.nucleosis.www.appdrivertaxibigway.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.nucleosis.www.appdrivertaxibigway.Adapters.AdapterNotificaciones;
import com.nucleosis.www.appdrivertaxibigway.Adapters.PlaceAutocompleteAdapter;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistorialServiciosCreados;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansListaPolygono;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansVehiculoConductor;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.Constans.Constans;
import com.nucleosis.www.appdrivertaxibigway.Ficheros.Fichero;
import com.nucleosis.www.appdrivertaxibigway.LoingDriverApp;
import com.nucleosis.www.appdrivertaxibigway.PointPolygono.PointDentroPolygono;
import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.ServiceDriver.ServiceTurno;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActivarTurno;
import com.nucleosis.www.appdrivertaxibigway.ws.wsActualizarServicio;
import com.nucleosis.www.appdrivertaxibigway.ws.wsAsignarServicioConductor;
import com.nucleosis.www.appdrivertaxibigway.ws.wsDesactivarTurno;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerIdZonaIdDistrito;
import com.nucleosis.www.appdrivertaxibigway.ws.wsExtraerPrecioZonaDistrito;
import com.nucleosis.www.appdrivertaxibigway.ws.wsListVehiculos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by karlos on 03/09/2016.
 */
public class CustomAlertDialog implements AdapterNotificaciones.OnItemClickListener{
    private Context context;
    private componentesR compR;
    private Fichero fichero;
    private MyTypeFace myTypeFace;
    private Activity activity;
    private PlaceAutocompleteAdapter mAdapter;
    protected GoogleApiClient mGoogleApiClient;
    private List<beansListaPolygono> listPolyGo;
    private static final LatLngBounds BOUNDS_LIMA = new LatLngBounds(
            new LatLng(-12.34202, -77.04231), new LatLng(-12.00103, -77.03269));
    private List<beansHistorialServiciosCreados> listServisCreados;
    public CustomAlertDialog(Context context) {
        this.context = context;
        activity=(Activity)context;
        myTypeFace=new MyTypeFace(context);
        fichero=new Fichero(context);
        compR = new componentesR(context);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                // .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mAdapter = new PlaceAutocompleteAdapter(context, mGoogleApiClient, BOUNDS_LIMA,
                null);
    }
    public void Alert_Elegir_taxi_conductor() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_elegir_taxi_conductor, null);
        final Button btnCancel=(Button)view.findViewById(R.id.btnCancel);
        final Button btnOk=(Button)view.findViewById(R.id.btnOk);
        alertDialogBuilder.setView(view);
        final int[] idVehiculo = {0};
        compR.Controls_alert_elegir_auto_conductor(view);
        //TextView lblElijaSuVehiculo=(TextView)view.findViewById(R.id.lblVehiculo);
        new wsListVehiculos(context,view).execute();

        compR.getSpinerVehiculo().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (wsListVehiculos.listVehiculos != null) {
                    List<beansVehiculoConductor> lista = wsListVehiculos.listVehiculos;
                    idVehiculo[0] = lista.get(position).getIdVehiculo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idVehiculo[0] != 0) {
                    new wsActivarTurno(context, idVehiculo[0]).execute();
                    alertDialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    public void DescativarTurno(){
             AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle(R.string.importante);
                dialogo1.setMessage(R.string.desactivatTurno);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        new wsDesactivarTurno(context).execute();
                    }
                });
                dialogo1.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
    }

    public void LlamarACentral(){
        JSONObject jsonConfiguraciones=fichero.ExtraerConfiguraciones();
        String direccion1;
        String telefono1="00-0000";
        if(jsonConfiguraciones!=null){
            try {
                telefono1=jsonConfiguraciones.getString("numTelefonoEmpesa");
                direccion1=jsonConfiguraciones.getString("direccionEmpresa");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AlertDialog.Builder alertBilder = new AlertDialog.Builder(context);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_llamada_central, null);
        final TextView txtTelefono1=(TextView)view.findViewById(R.id.txtTefono1);
        final TextView txtTelefono2=(TextView)view.findViewById(R.id.txtTefono2);
        final TextView txtTelefono3=(TextView)view.findViewById(R.id.txtTefono3);
        final TextView txtTelefono4=(TextView)view.findViewById(R.id.txtTefono4);
        Button btnCancelarTelefonos=(Button)view.findViewById(R.id.btnCerrarTelefonos);
        txtTelefono1.setTypeface(myTypeFace.OpenSansRegular());
        txtTelefono2.setTypeface(myTypeFace.OpenSansRegular());
        txtTelefono3.setTypeface(myTypeFace.OpenSansRegular());
        txtTelefono4.setTypeface(myTypeFace.OpenSansRegular());
        btnCancelarTelefonos.setTypeface(myTypeFace.openRobotoLight());
        alertBilder.setView(view);
        final AlertDialog alertDialog = alertBilder.create();
        JSONObject configuracionesJson=fichero.ExtraerConfiguraciones();
        Log.d("configuracionTeel",fichero.ExtraerConfiguraciones().toString());
        if(configuracionesJson!=null){
            try {
                txtTelefono1.setText("Tel 1:\t "+configuracionesJson.getString("numTelefonoEmpesa"));
                txtTelefono2.setText("Tel 2:\t "+configuracionesJson.getString("numTelefonoEmpesa_2"));
                txtTelefono3.setText("Tel 3:\t "+configuracionesJson.getString("numTelefonoEmpesa_3"));
                txtTelefono4.setText("Tel 4:\t "+configuracionesJson.getString("numTelefonoEmpesa_4"));

                //tel:998319046

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        txtTelefono1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject configuracionesJson=fichero.ExtraerConfiguraciones();
                String    telef_1= null;
                try {
                    telef_1 = configuracionesJson.getString("numTelefonoEmpesa");
                    Intent i = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:"+telef_1)); //
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        txtTelefono2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject configuracionesJson=fichero.ExtraerConfiguraciones();
                String    telef_2= null;
                try {
                    telef_2 = configuracionesJson.getString("numTelefonoEmpesa_2");
                    Intent i = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:"+telef_2)); //
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        txtTelefono3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject configuracionesJson=fichero.ExtraerConfiguraciones();
                String    telef_3= null;
                try {
                    telef_3 = configuracionesJson.getString("numTelefonoEmpesa_3");
                    Intent i = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:"+telef_3)); //
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        txtTelefono4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject configuracionesJson=fichero.ExtraerConfiguraciones();
                String    telef_4= null;
                try {
                    telef_4 = configuracionesJson.getString("numTelefonoEmpesa_4");
                    Intent i = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:"+telef_4)); //
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancelarTelefonos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        alertDialog.show();
    }
    public void CargarNotificaciones(final List<beansHistorialServiciosCreados> ListaServiciosCreados){
        listServisCreados=ListaServiciosCreados;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_notificaicon_recycler, null);
        //   RecyclerView.Adapter adapter;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager lManager;
        //onLayoutChildren

        compR.Controls_notificaciones(view);
        //requestWindowFeature
        compR.getRecycler().setClipToPadding(true);
        compR.getRecycler().setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(context);
        compR.getRecycler().setLayoutManager(lManager);
        // Crear un nuevo adaptador

        adapter = new AdapterNotificaciones(ListaServiciosCreados, context, this);
        compR.getRecycler().setAdapter(adapter);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialogPanelNotificacion = alertDialogBuilder.create();
        compR.getBtnDismisNotificaciones().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogPanelNotificacion.dismiss();
            }
        });
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialogPanelNotificacion.show();
    }

    @Override
    public void onClick(AdapterNotificaciones.ViewHolder holder, String posicion) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_detalle_servicio_custom, null);
        final int posicion_ = Integer.parseInt(posicion);
        TextView lblDetalleServicio = (TextView) view.findViewById(R.id.txtDetalleServicio);
        Button btnOk_=(Button)view.findViewById(R.id.btnOk);
        Button btnCancel_=(Button)view.findViewById(R.id.btnCancel);
        //   Toast.makeText(this, ListaServiciosCreados.get(posicion_).getIdServicio().toString(), Toast.LENGTH_SHORT).show();
        String detalle = Constans.DetalleServicioLista(context,listServisCreados,posicion_);
        lblDetalleServicio.setText(Html.fromHtml(detalle));
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog;
        alertDialog = alertDialogBuilder.create();
        btnOk_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);

                dialogo1.setTitle(R.string.importante);
                dialogo1.setMessage(R.string.mensajeAlert);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(R.string.Confirmar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        alertDialog.dismiss();
                        new wsAsignarServicioConductor(context,
                                listServisCreados.get(posicion_).getIdServicio()).execute();
                    }
                });
                dialogo1.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });
        btnCancel_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void CerrarSesion(){
        AlertDialog.Builder alertBilder = new AlertDialog.Builder(context);
        alertBilder.setTitle(R.string.cerrar_sesion);
        alertBilder.setMessage(R.string.msnCerrar_sesion);
        alertBilder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JSONObject jsonSesion = new JSONObject();
                try {
                    jsonSesion.put("idSesion", "0");
                    fichero.InsertarSesion(jsonSesion.toString());
                    Log.d("StracFichero", fichero.ExtraerSesion().toString());
                    Intent intentLongin = new Intent(context, LoingDriverApp.class);
                    context.startActivity(intentLongin);
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //  Log.d("acatividad", "Destrudia en sesion");
                    Intent intent=new Intent(context,ServiceTurno.class);
                    context.stopService(intent);
                    activity.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        alertBilder.show();
    }

    public void alertAdicionales(final String idServicio,final JSONObject jsonConfiguraciones,
                                  List<beansListaPolygono> listPoligonos,
                                  final String AddresIncioCliente,
                                  final String idCliente){
        listPolyGo=listPoligonos;
        final AlertDialog.Builder alertBilder = new AlertDialog.Builder(activity);
        final View view = activity.getLayoutInflater().inflate(R.layout.view_alert_adicionales, null);

        final ImageView imagenCanceleAlertAdicionales=(ImageView)view.findViewById(R.id.ImgButtonCancelAlert);
        final Button btnAireAcondicionado1=(Button)view.findViewById(R.id.btnAireAcondicionado1);
        final Button btnAireAcondicionado2=(Button)view.findViewById(R.id.btnAireAcondicionado2);
        final Button btnEnviarAire=(Button)view.findViewById(R.id.btnEnviarAire);
        btnAireAcondicionado1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAireAcondicionado1.setVisibility(View.GONE);
                btnAireAcondicionado2.setVisibility(View.VISIBLE);
                btnEnviarAire.setVisibility(View.VISIBLE);
            }
        });

        btnAireAcondicionado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAireAcondicionado1.setVisibility(View.VISIBLE);
                btnAireAcondicionado2.setVisibility(View.GONE);
                btnEnviarAire.setVisibility(View.GONE);
            }
        });


        btnEnviarAire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                dialogo1.setTitle("Añadir Aire acondicionado");
                try {
                    dialogo1.setMessage("¿ Esta seguro de  realizar esta operacion ?"+"\n\n"+
                            "Costo:"+"\t\t"+"S/."+jsonConfiguraciones.getString("impAireAcondicionado"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnAireAcondicionado1.setVisibility(View.VISIBLE);
                        btnAireAcondicionado2.setVisibility(View.GONE);
                        btnEnviarAire.setVisibility(View.GONE);

                        try {
                            new wsActualizarServicio(
                                    activity,
                                    idServicio,
                                    "1",
                                    jsonConfiguraciones.getString("impAireAcondicionado"),
                                    "",//peaje
                                    "",//tiempo de espera
                                    "",//minutos tiempoEspera
                                    "",//importe servicio
                                    "",//idDistritoIncio
                                    "",//idZonaIncio
                                    "",//DireccionIncio
                                    "",//idDistritoFin
                                    "",//idZonaFin
                                    "",//DireccionFin
                                    "",//descricion pago extra
                                    "",  //IMPORTE EXTRAORDINARIO
                                    ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                            ).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnAireAcondicionado1.setVisibility(View.VISIBLE);
                        btnAireAcondicionado2.setVisibility(View.GONE);
                        btnEnviarAire.setVisibility(View.GONE);
                    }
                });
                dialogo1.show();
            }
        });

        final Button btnPeaje1=(Button)view.findViewById(R.id.btnPeaje1);
        final Button btnPeaje2=(Button)view.findViewById(R.id.btnPeaje2);
        final Button btnEnviarPeaje=(Button)view.findViewById(R.id.btnEnviarPeaje);

        btnPeaje1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPeaje1.setVisibility(View.GONE);
                btnPeaje2.setVisibility(View.VISIBLE);
                btnEnviarPeaje.setVisibility(View.VISIBLE);
            }
        });

        btnPeaje2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPeaje2.setVisibility(View.GONE);
                btnPeaje1.setVisibility(View.VISIBLE);
                btnEnviarPeaje.setVisibility(View.GONE);
            }
        });

        btnEnviarPeaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                dialogo1.setTitle(R.string.agregarPeaje);
                try {
                    dialogo1.setMessage(R.string.operacionPeaje+"\n\n"+
                            "Costo:"+"\t\t"+"S/."+jsonConfiguraciones.getString("impServicioPeaje"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(R.string.Confirmar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnPeaje2.setVisibility(View.GONE);
                        btnPeaje1.setVisibility(View.VISIBLE);
                        btnEnviarPeaje.setVisibility(View.GONE);
                        try {
                            new wsActualizarServicio(
                                    activity,
                                    idServicio,
                                    "",
                                    "",
                                    jsonConfiguraciones.getString("impServicioPeaje"),//peaje
                                    "",//tiempo de espera
                                    "",//minutos tiempoEspera
                                    "",//importe servicio
                                    "",//idDistritoIncio
                                    "",//idZonaIncio
                                    "",//DireccionIncio
                                    "",//idDistritoFin
                                    "",//idZonaFin
                                    "",//DireccionFin
                                    "",
                                    "",  //IMPORTE EXTRAORDINARIO
                                    ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                            ).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        btnPeaje2.setVisibility(View.GONE);
                        btnPeaje1.setVisibility(View.VISIBLE);
                        btnEnviarPeaje.setVisibility(View.GONE);
                    }
                });
                dialogo1.show();
            }
        });

        final Button btnTiempoEspera1=(Button)view.findViewById(R.id.btnTiempoEspera1);
        final Button btnTiempoEspera2=(Button)view.findViewById(R.id.btnTiempoEspera2);
        final Button btnEnviarTiempoEspera=(Button)view.findViewById(R.id.btnEnviarTiempoEspera);
        final EditText editTiempoEspera=(EditText)view.findViewById(R.id.editTiempoEspera);

        final double[] totalMinutosXtiempo = {0};

        final LinearLayout lienarTiempoEspera=(LinearLayout)view.findViewById(R.id.LinearContenedorTiempoEspera);
        btnTiempoEspera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTiempoEspera1.setVisibility(View.GONE);
                btnTiempoEspera2.setVisibility(View.VISIBLE);
                lienarTiempoEspera.setVisibility(View.VISIBLE);

            }
        });
        btnTiempoEspera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTiempoEspera1.setVisibility(View.VISIBLE);
                btnTiempoEspera2.setVisibility(View.GONE);
                lienarTiempoEspera.setVisibility(View.GONE);
            }
        });

        btnEnviarTiempoEspera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lblMinutosTiempo=editTiempoEspera.getText().toString().trim();
                Log.d("editTexttiempoA",lblMinutosTiempo);
                if(lblMinutosTiempo.length()!=0){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                    dialogo1.setTitle("Añadir Minutos de espera");
                    try {
                        double costoMinuto=Double.parseDouble(jsonConfiguraciones.getString("impMinutoEspera"));
                        Log.d("editTexttiempoB",lblMinutosTiempo);
                        totalMinutosXtiempo[0] =Double.parseDouble(lblMinutosTiempo)*costoMinuto;
                        dialogo1.setMessage("¿ Esta seguro de  realizar esta operacion ?"+"\n\n"+
                                "Costo:"+"\t\t"+"S/."+String.valueOf(totalMinutosXtiempo[0]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                            btnTiempoEspera1.setVisibility(View.VISIBLE);
                            btnTiempoEspera2.setVisibility(View.GONE);
                            lienarTiempoEspera.setVisibility(View.GONE);
                            new wsActualizarServicio(
                                    activity,
                                    idServicio,
                                    "",//indAireacondicionado
                                    "",//aireAcondicionado
                                    "",//peaje
                                    String.valueOf(totalMinutosXtiempo[0]),//importe tiempo espera
                                    lblMinutosTiempo,//minutos tiempoEspera
                                    "",//importe servicio
                                    "",//idDistritoIncio
                                    "",//idZonaIncio
                                    "",//DireccionIncio
                                    "",//idDistritoFin
                                    "",//idZonaFin
                                    "",//DireccionFin
                                    "",//descripcion pago extra
                                    "",  //IMPORTE EXTRAORDINARIO
                                    ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                            ).execute();

                        }
                    });
                    dialogo1.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            btnTiempoEspera1.setVisibility(View.VISIBLE);
                            btnTiempoEspera2.setVisibility(View.GONE);
                            lienarTiempoEspera.setVisibility(View.GONE);
                        }
                    });
                    dialogo1.show();
                }else {
                    Toast.makeText(activity,"Ingrese minutos",Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button btnGatosExtraordinario1=(Button)view.findViewById(R.id.btnPagoExtraOrdinadrio1);
        final Button btnGatosExtraordinario2=(Button)view.findViewById(R.id.btnPagoExtraOrdinadrio2);
        final LinearLayout linearPagosExtras=(LinearLayout)view.findViewById(R.id.linearPagosExtras);
        final EditText editPagosExras=(EditText)view.findViewById(R.id.editPagoExtraOrdinario);
        final Button btnEnviarPagosExras=(Button)view.findViewById(R.id.btnEnviarPagosExtraOrdinarios);

        final EditText editDescriopcionPagoExtra=(EditText)view.findViewById(R.id.editDescripcionPagoExtra);

        btnGatosExtraordinario1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGatosExtraordinario1.setVisibility(View.GONE);
                btnGatosExtraordinario2.setVisibility(View.VISIBLE);
                linearPagosExtras.setVisibility(View.VISIBLE);
            }
        });

        btnGatosExtraordinario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGatosExtraordinario1.setVisibility(View.VISIBLE);
                btnGatosExtraordinario2.setVisibility(View.GONE);
                linearPagosExtras.setVisibility(View.GONE);
                editPagosExras.setText("");
                editDescriopcionPagoExtra.setText("");
            }
        });

        btnEnviarPagosExras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPagosExras.getText().toString().trim().length()!=0){
                    String importePagoExtra=editPagosExras.getText().toString().trim();
                    String descripcion=editDescriopcionPagoExtra.getText().toString().trim();
                    if(!importePagoExtra.equals("0")){
                        new wsActualizarServicio(
                                activity,
                                idServicio,
                                "",//indAireacondicionado
                                "",//aireAcondicionado
                                "",//peaje
                                "",//importe tiempo espera
                                "",//minutos tiempoEspera
                                "",//importe servicio
                                "",//idDistritoIncio
                                "",//idZonaIncio
                                "",//DireccionIncio
                                "",//idDistritoFin
                                "",//idZonaFin
                                "",//DireccionFin
                                descripcion,
                                importePagoExtra,  //IMPORTE EXTRAORDINARIO
                                ""  //ID TIPO PAGO SERVCIO CREDITO O CONTADO  1 CONTADO 2 CREDITO
                        ).execute();
                    }

                }else {
                    Toast.makeText(context,"Ingrese una cantidad",Toast.LENGTH_LONG).show();
                }
            }
        });

        final Button btnModificarRuta1=(Button)view.findViewById(R.id.btnModificarRuta1);
        final Button btnModificarRuta2=(Button)view.findViewById(R.id.btnModificarRuta2);
        final LinearLayout linearContenedor=(LinearLayout)view.findViewById(R.id.LinearContenedorRutaNueva);
        final Button btnEnviarRutaNueva=(Button)view.findViewById(R.id.btnEnviarNuevaRuta);
        final AutoCompleteTextView editDestino1=(AutoCompleteTextView)view.findViewById(R.id.autocompleteDireccion1);
        final AutoCompleteTextView editDestino2=(AutoCompleteTextView)view.findViewById(R.id.autocompleteDireccion2);
        editDestino1.setOnItemClickListener(mAutocompleteClickListener_1);
        editDestino1.setAdapter(mAdapter);

        editDestino2.setOnItemClickListener(mAutocompleteClickListener_2);
        editDestino2.setAdapter(mAdapter);
        btnModificarRuta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGoogleApiClient.connect();

                btnModificarRuta1.setVisibility(View.GONE);
                btnModificarRuta2.setVisibility(View.VISIBLE);
                linearContenedor.setVisibility(View.VISIBLE);
                //SETENADO LA DIRECCION INCIAL DEL CLIENTE
                editDestino1.setText(AddresIncioCliente);
            }
        });

        btnModificarRuta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnModificarRuta1.setVisibility(View.VISIBLE);
                btnModificarRuta2.setVisibility(View.GONE);
                linearContenedor.setVisibility(View.GONE);
            }
        });

        alertBilder.setView(view);
        final AlertDialog alertAdicionales = alertBilder.create();

        btnEnviarRutaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addres1=editDestino1.getText().toString().trim();
                String addres2=editDestino2.getText().toString().trim();

                if(addres1.length()!=0 && addres2.length()!=0){

                    Log.d("dataComplex",editDestino1.getText().toString());
                    JSONObject jsonAdress1=new JSONObject();
                    try {
                        if(addres1.length()!=0){
                            jsonAdress1.put("addresOrigen",addres1);
                            fichero.InsertaDireccionIncio(jsonAdress1.toString());
                            Log.d("addreORIGIN",fichero.ExtraerDireccionIncio().toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //ENTRE EL PRECIO DE IR DE UNA ZONA A OTRA
                    JSONObject jsonAdress2=new JSONObject();
                    try {
                        if(addres2.length()!=0){
                            jsonAdress2.put("addresDestino",addres2);
                            fichero.InsertaDireccionFin(jsonAdress2.toString());
                            Log.d("addreORIGIN",fichero.ExtraerDireccionFin().toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Extrae el precio de zona por zona
                    new wsExtraerPrecioZonaDistrito(activity,alertAdicionales,idServicio,idCliente).execute();
                }
            }
        });
        imagenCanceleAlertAdicionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAdicionales.dismiss();
                mGoogleApiClient.disconnect();
            }
        });
        alertAdicionales.setCancelable(false);
        alertAdicionales.show();
    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener_1
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback_1);
        }
    };
    private AdapterView.OnItemClickListener mAutocompleteClickListener_2
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            Log.d("escuchar", String.valueOf(position));
            final String placeId = item.getPlaceId();

            final CharSequence primaryText = item.getPrimaryText(null);
            final CharSequence fullText=item.getFullText(null);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback_2);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_1
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
          //  Activity activity=MapsConductorClienteServicio.this;

            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            //  Log.d("location_",places.get(0).getLatLng().toString());
            final CharSequence thirdPartyAttribution = places.getAttributions();
            //  Log.d("latLong_adres", String.valueOf(place.getLatLng()));
            //  Log.d("distancia", String.valueOf(place.getLocale()));
            LatLng puntoEnZona=place.getLatLng();

            //OBTIENE EL ID_DE DE ZONA Y DE DISTRITO
            String zona =DeterminaZona(listPolyGo.size(),puntoEnZona);
            int casoEntrada=1;

            new wsExtraerIdZonaIdDistrito(activity,zona,casoEntrada).execute();
            // Log.d("zonaAdres1",zona);
            places.release();
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_2
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
           // Activity activity=MapsConductorClienteServicio.this;
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e("Error", "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            //   Log.d("location_",places.get(0).getLatLng().toString());
            final CharSequence thirdPartyAttribution = places.getAttributions();
            //  Log.d("latLong_adres", String.valueOf(place.getLatLng()));
            //   Log.i(TAG, "Place details received: " + place.getName());
            //  Log.d("distancia", String.valueOf(place.getLocale()));
            LatLng puntoEnZona=place.getLatLng();
            String zona=DeterminaZona(listPolyGo.size(),puntoEnZona);
            //  Log.d("zonaAdres2",zona);
            //OBTIENE EL ID_DE DE ZONA Y DE DISTRITO
            int casoEntrada=2;
//            Log.d("observandoFichero",fichero.ExtraerConfiguraciones().toString());
            new wsExtraerIdZonaIdDistrito(activity,zona,casoEntrada).execute();
            places.release();
        }
    };
    private String DeterminaZona(int totalPoligono,LatLng positoinDireccion){
        String zona="No hay datos";
        for (int x = 0; x < totalPoligono; x++) {
            //  Log.d("nameXXX", String.valueOf(x) + " " + listPolyGo.get(x).getNameZona() + "\t\t" + listPolyGo.get(x).getListLatLngPolygono().toString());
            boolean punto = PointDentroPolygono.containsLocation(positoinDireccion, listPolyGo.get(x).getListLatLngPolygono(), true);
            if (punto) {
                // Toast.makeText(MainActivity.this, "DENTRO", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, listPolyGo.get(x).getNameZona().toString(), Toast.LENGTH_LONG).show();
                Log.d("nombreZna", listPolyGo.get(x).getNameZona().toString());
                zona=listPolyGo.get(x).getNameZona().toString();
                x = totalPoligono;
            }
        }
        return zona;
    }
}
