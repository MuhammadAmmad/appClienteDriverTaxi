package com.nucleosis.www.appdrivertaxibigway.Componentes;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.nucleosis.www.appdrivertaxibigway.R;
import com.nucleosis.www.appdrivertaxibigway.TypeFace.MyTypeFace;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by karlos on 21/03/2016.
 */
public class componentesR  {
    private  Context context;

    private TextView lblNombre_;
    private TextView lblAppell_;
    private TextView lblCelular_;
    private TextView lblTelefono_;
    private TextView lblLicenciExpiration_;
    private TextView lblEmal_;
    private TextView lblDNI_;

    private  TextView txtNombre;
    private TextView txtAppelli;
    private TextView txtCelular;
    private TextView txtTelefono;
    private TextView txtLicenciaExpiration;
    private TextView txtEmail;
    private TextView txtDNI;
    private  TextView lblElijaSuVehiculo;
    private  EditText editUser;
    private  EditText editPass;
    private TextView textNotificaciones;
    private  Button btnSigIn;
    private ImageButton btnDismisNotificaciones;
    private Button btnActivarTurno;
    private Button btnDesactivarTurno;
    private ImageView btnClienteEncontrado;
    private ImageView btnServicioTerminadoOk;
    private ImageView btnIrAServicios;
    private ImageView btnServicioNoTerminado;
    private ImageView btnAdicionales;
    EditText editHistoriaCarrera;
    private  LinearLayout linearLayoutLoading;
    private  LinearLayout linearLayoutLogin;
    private  ProgressBar progressBarLoading;
    private Toolbar toolbar;
    private Toolbar toolbar_2;
    private ActionBar actionBar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private  AppCompatActivity appCompatActivity;
    private MyTypeFace myTypeFace;
    private GridViewWithHeaderAndFooter grid;
    private CheckBox  chxBoxFecha;
    private Spinner spinerVehiculo;
    private ImageView imageDriver;
    private ImageView imageButonListarServicios;
    private LinearLayout linearFragment;
    private  RecyclerView recycler;
    public componentesR(Context context) {
        this.context = context;
        appCompatActivity= (AppCompatActivity) context;
        myTypeFace=new MyTypeFace(context);
    }

    public void Controls_LoginDriverApp(Activity activity){
        /*EditText*/
         editUser=(EditText)activity.findViewById(R.id.editUser);
         editPass=(EditText)activity.findViewById(R.id.editPass);
         btnSigIn=(Button)activity.findViewById(R.id.btnLoginDriver);
         btnSigIn.setOnClickListener((View.OnClickListener) context);
        /*LinearLayout*/
        /*linearLayoutLoading =(LinearLayout)activity.findViewById(R.id.LinearLayoutLoading);
        linearLayoutLogin=(LinearLayout)activity.findViewById(R.id.LinearLayoutLogin);
        *//*ProggresBar*//*
        progressBarLoading=(ProgressBar)activity.findViewById(R.id.Loading);*/
    }
    public Toolbar cargar_toolbar_2(Activity mainActivity) {
        toolbar_2 = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar_2);
        return toolbar_2;
    }
    public void Contros_main_activity(Activity activity) {
        actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawer =(DrawerLayout)activity.findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)activity.findViewById(R.id.nav_view);
        btnActivarTurno=(Button)activity.findViewById(R.id.btnActivarTurno);
        btnActivarTurno.setOnClickListener((View.OnClickListener) activity);
        btnDesactivarTurno=(Button)activity.findViewById(R.id.btnDesactivarTurno);
        btnDesactivarTurno.setOnClickListener((View.OnClickListener) activity);
        btnAdicionales=(ImageView)activity.findViewById(R.id.btnAdicionales);
        btnAdicionales.setOnClickListener((View.OnClickListener) activity);

        btnClienteEncontrado=(ImageView)activity.findViewById(R.id.btnClienteEncontrado);
        btnClienteEncontrado.setOnClickListener((View.OnClickListener) activity);

        btnServicioTerminadoOk=(ImageView)activity.findViewById(R.id.btnServicioTerminadoOk);
        btnServicioTerminadoOk.setOnClickListener((View.OnClickListener) activity);


        btnIrAServicios=(ImageView)activity.findViewById(R.id.btnIrA_Servicios);
        btnIrAServicios.setOnClickListener((View.OnClickListener) activity);

        linearFragment=(LinearLayout)activity.findViewById(R.id.LinearFragment);
    }
    public Toolbar cargar_toolbar(Activity activity) {
        toolbar = (Toolbar) activity.findViewById(R.id.appbar);
        appCompatActivity.setSupportActionBar(toolbar);
        return toolbar;
    }

    public Button getBtnActivarTurno() {
        return btnActivarTurno;
    }

    public void setBtnActivarTurno(Button btnActivarTurno) {
        this.btnActivarTurno = btnActivarTurno;
    }

    public Button getBtnDesactivarTurno() {
        return btnDesactivarTurno;
    }

    public void setBtnDesactivarTurno(Button btnDesactivarTurno) {
        this.btnDesactivarTurno = btnDesactivarTurno;
    }

    public void Controls_fragmentDataUser(View rootView) {
        lblNombre_=(TextView)rootView.findViewById(R.id.lblNombre);
        lblAppell_=(TextView)rootView.findViewById(R.id.lblApellido);
        lblCelular_=(TextView)rootView.findViewById(R.id.lblCelular);
        lblTelefono_=(TextView)rootView.findViewById(R.id.lblTelefono);
        lblEmal_=(TextView)rootView.findViewById(R.id.lblEmail);
        lblDNI_=(TextView)rootView.findViewById(R.id.lblDni);
        lblLicenciExpiration_=(TextView)rootView.findViewById(R.id.lblLicenciaExpiration);

        lblNombre_.setTypeface(myTypeFace.opencodeBold());
        lblAppell_.setTypeface(myTypeFace.opencodeBold());
        lblCelular_.setTypeface(myTypeFace.opencodeBold());
        lblTelefono_.setTypeface(myTypeFace.opencodeBold());
        lblEmal_.setTypeface(myTypeFace.opencodeBold());
        lblDNI_.setTypeface(myTypeFace.opencodeBold());
        lblLicenciExpiration_.setTypeface(myTypeFace.opencodeBold());

        txtNombre=(TextView)rootView.findViewById(R.id.txtName);
        txtAppelli=(TextView)rootView.findViewById(R.id.txtApellido);
        txtCelular=(TextView)rootView.findViewById(R.id.txtCelular);
        txtTelefono=(TextView)rootView.findViewById(R.id.txtPhone);
        txtEmail=(TextView)rootView.findViewById(R.id.txtEmail);
        txtDNI=(TextView)rootView.findViewById(R.id.txtDNI);
        txtLicenciaExpiration=(TextView)rootView.findViewById(R.id.txtLicenciaExpiration);

        txtNombre.setTypeface(myTypeFace.openRobotoLight());
        txtAppelli.setTypeface(myTypeFace.openRobotoLight());
        txtCelular.setTypeface(myTypeFace.openRobotoLight());
        txtTelefono.setTypeface(myTypeFace.openRobotoLight());
        txtEmail.setTypeface(myTypeFace.openRobotoLight());
        txtDNI.setTypeface(myTypeFace.openRobotoLight());
        txtLicenciaExpiration.setTypeface(myTypeFace.openRobotoLight());

        imageDriver=(ImageView)rootView.findViewById(R.id.imagDriver);
    }

    public void Controls_fragment_Historia_Carrearas_new(View rootView) {
        grid= (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.gridview);

    }
    public void Controls_fragment_Historia_Carreras_createHeader(View rootView) {
        editHistoriaCarrera=(EditText)rootView.findViewById(R.id.editHistoriaCarrera);
        chxBoxFecha=(CheckBox)rootView.findViewById(R.id.chxFecha);
        imageButonListarServicios=(ImageView)rootView.findViewById(R.id.imageButtonSearch);
  //      imageButonListarServicios.setOnClickListener((View.OnClickListener) rootView);
        //chxBoxFecha.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) context);

    }

    public void Controls_alert_elegir_auto_conductor(View view) {
        lblElijaSuVehiculo =(TextView)view.findViewById(R.id.lblVehiculo);
        spinerVehiculo=(Spinner)view.findViewById(R.id.spinnerVehiculoElejir);

    }
    public void Controls_notificaciones(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.reciclador);
        textNotificaciones=(TextView)view.findViewById(R.id.txtNotificaciones);
        textNotificaciones.setTypeface(myTypeFace.openRobotoLight());
        btnDismisNotificaciones=(ImageButton)view.findViewById(R.id.btnDismisNotificaciones);
    }

    public void Controls_Maps_conducotor_cliente(Activity activity) {
        btnActivarTurno=(Button)activity.findViewById(R.id.btnActivarTurno);
        btnActivarTurno.setOnClickListener((View.OnClickListener) activity);
        btnDesactivarTurno=(Button)activity.findViewById(R.id.btnDesactivarTurno);
        btnDesactivarTurno.setOnClickListener((View.OnClickListener) activity);
        btnAdicionales=(ImageView)activity.findViewById(R.id.btnAdicionales);
        btnAdicionales.setOnClickListener((View.OnClickListener) activity);

        btnClienteEncontrado=(ImageView)activity.findViewById(R.id.btnClienteEncontrado);
        btnClienteEncontrado.setOnClickListener((View.OnClickListener) activity);

        btnServicioTerminadoOk=(ImageView)activity.findViewById(R.id.btnServicioTerminadoOk);
        btnServicioTerminadoOk.setOnClickListener((View.OnClickListener) activity);

        btnServicioNoTerminado=(ImageView)activity.findViewById(R.id.btnSercioNoTerminado);
        btnServicioNoTerminado.setOnClickListener((View.OnClickListener) activity);

        btnIrAServicios=(ImageView)activity.findViewById(R.id.btnIrA_Servicios);
        btnIrAServicios.setOnClickListener((View.OnClickListener) activity);
    }



    public GridViewWithHeaderAndFooter getGrid() {

        return grid;
    }

    public ImageView getBtnAdicionales() {
        return btnAdicionales;
    }

    public void setBtnAdicionales(ImageView btnAdicionales) {
        this.btnAdicionales = btnAdicionales;
    }

    public TextView getTextNotificaciones() {
        return textNotificaciones;
    }

    public ImageView getImageButonListarServicios() {
        return imageButonListarServicios;
    }

    public void setImageButonListarServicios(ImageView imageButonListarServicios) {
        this.imageButonListarServicios = imageButonListarServicios;
    }

    public void setTextNotificaciones(TextView textNotificaciones) {
        this.textNotificaciones = textNotificaciones;
    }

    public ImageButton getBtnDismisNotificaciones() {
        return btnDismisNotificaciones;
    }

    public void setBtnDismisNotificaciones(ImageButton btnDismisNotificaciones) {
        this.btnDismisNotificaciones = btnDismisNotificaciones;
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    public void setRecycler(RecyclerView recycler) {
        this.recycler = recycler;
    }

    public LinearLayout getLinearFragment() {
        return linearFragment;
    }

    public void setLinearFragment(LinearLayout linearFragment) {
        this.linearFragment = linearFragment;
    }

    public ImageView getBtnIrAServicios() {
        return btnIrAServicios;
    }

    public void setBtnIrAServicios(ImageView btnIrAServicios) {
        this.btnIrAServicios = btnIrAServicios;
    }

    public TextView getLblElijaSuVehiculo() {
        return lblElijaSuVehiculo;
    }

    public void setLblElijaSuVehiculo(TextView lblElijaSuVehiculo) {
        this.lblElijaSuVehiculo = lblElijaSuVehiculo;
    }

    public ImageView getBtnServicioNoTerminado() {
        return btnServicioNoTerminado;
    }

    public void setBtnServicioNoTerminado(ImageView btnServicioNoTerminado) {
        this.btnServicioNoTerminado = btnServicioNoTerminado;
    }

    public Spinner getSpinerVehiculo() {
        return spinerVehiculo;
    }

    public void setSpinerVehiculo(Spinner spinerVehiculo) {
        this.spinerVehiculo = spinerVehiculo;
    }

    public EditText getEditHistoriaCarrera() {
        return editHistoriaCarrera;
    }

    public void setEditHistoriaCarrera(EditText editHistoriaCarrera) {
        this.editHistoriaCarrera = editHistoriaCarrera;
    }

    public CheckBox getChxBoxFecha() {
        return chxBoxFecha;
    }

    public void setChxBoxFecha(CheckBox chxBoxFecha) {
        this.chxBoxFecha = chxBoxFecha;
    }

    public void setGrid(GridViewWithHeaderAndFooter grid) {
        this.grid = grid;
    }

    public TextView getLblNombre_() {
        return lblNombre_;
    }

    public void setLblNombre_(TextView lblNombre_) {
        this.lblNombre_ = lblNombre_;
    }

    public TextView getLblAppell_() {
        return lblAppell_;
    }

    public void setLblAppell_(TextView lblAppell_) {
        this.lblAppell_ = lblAppell_;
    }

    public TextView getLblCelular_() {
        return lblCelular_;
    }

    public void setLblCelular_(TextView lblCelular_) {
        this.lblCelular_ = lblCelular_;
    }

    public TextView getLblTelefono_() {
        return lblTelefono_;
    }

    public void setLblTelefono_(TextView lblTelefono_) {
        this.lblTelefono_ = lblTelefono_;
    }

    public TextView getLblLicenciExpiration_() {
        return lblLicenciExpiration_;
    }

    public void setLblLicenciExpiration_(TextView lblLicenciExpiration_) {
        this.lblLicenciExpiration_ = lblLicenciExpiration_;
    }

    public TextView getLblEmal_() {
        return lblEmal_;
    }

    public void setLblEmal_(TextView lblEmal_) {
        this.lblEmal_ = lblEmal_;
    }

    public TextView getLblDNI_() {
        return lblDNI_;
    }

    public void setLblDNI_(TextView lblDNI_) {
        this.lblDNI_ = lblDNI_;
    }

    public TextView getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(TextView txtNombre) {
        this.txtNombre = txtNombre;
    }

    public TextView getTxtAppelli() {
        return txtAppelli;
    }

    public void setTxtAppelli(TextView txtAppelli) {
        this.txtAppelli = txtAppelli;
    }

    public TextView getTxtCelular() {
        return txtCelular;
    }

    public void setTxtCelular(TextView txtCelular) {
        this.txtCelular = txtCelular;
    }

    public TextView getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(TextView txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public TextView getTxtLicenciaExpiration() {
        return txtLicenciaExpiration;
    }

    public void setTxtLicenciaExpiration(TextView txtLicenciaExpiration) {
        this.txtLicenciaExpiration = txtLicenciaExpiration;
    }

    public TextView getTxtDNI() {
        return txtDNI;
    }

    public void setTxtDNI(TextView txtDNI) {
        this.txtDNI = txtDNI;
    }

    public TextView getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(TextView txtEmail) {
        this.txtEmail = txtEmail;
    }

    public MyTypeFace getMyTypeFace() {
        return myTypeFace;
    }

    public void setMyTypeFace(MyTypeFace myTypeFace) {
        this.myTypeFace = myTypeFace;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Toolbar getToolbar_2() {
        return toolbar_2;
    }

    public void setToolbar_2(Toolbar toolbar_2) {
        this.toolbar_2 = toolbar_2;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public LinearLayout getLinearLayoutLogin() {
        return linearLayoutLogin;
    }

    public void setLinearLayoutLogin(LinearLayout linearLayoutLogin) {
        this.linearLayoutLogin = linearLayoutLogin;
    }

    public LinearLayout getLinearLayoutLoading() {
        return linearLayoutLoading;
    }

    public void setLinearLayoutLoading(LinearLayout linearLayoutLoading) {
        this.linearLayoutLoading = linearLayoutLoading;
    }

    public Button getBtnSigIn() {
        return btnSigIn;
    }

    public void setBtnSigIn(Button btnSigIn) {
        this.btnSigIn = btnSigIn;
    }

    public EditText getEditPass() {
        return editPass;
    }

    public void setEditPass(EditText editPass) {
        this.editPass = editPass;
    }

    public EditText getEditUser() {
        return editUser;
    }

    public void setEditUser(EditText editUser) {
        this.editUser = editUser;
    }

    public ProgressBar getProgressBarLoading() {
        return progressBarLoading;
    }

    public void setProgressBarLoading(ProgressBar progressBarLoading) {
        this.progressBarLoading = progressBarLoading;
    }

    public ImageView getImageDriver() {
        return imageDriver;
    }

    public void setImageDriver(ImageView imageDriver) {
        this.imageDriver = imageDriver;
    }

    public ImageView getBtnClienteEncontrado() {
        return btnClienteEncontrado;
    }

    public void setBtnClienteEncontrado(ImageView btnClienteEncontrado) {
        this.btnClienteEncontrado = btnClienteEncontrado;
    }

    public ImageView getBtnServicioTerminadoOk() {
        return btnServicioTerminadoOk;
    }

    public void setBtnServicioTerminadoOk(ImageView btnServicioTerminadoOk) {
        this.btnServicioTerminadoOk = btnServicioTerminadoOk;
    }


}
