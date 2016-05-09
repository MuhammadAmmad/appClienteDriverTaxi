package com.nucleosis.www.appclientetaxibigway.componentes;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.nucleosis.www.appclientetaxibigway.R;
import com.nucleosis.www.appclientetaxibigway.TypeFace.MyTypeFace;

import org.w3c.dom.Text;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by karlos on 16/04/2016.
 */
public class ComponentesR {
    private Context context;
    private  AppCompatActivity appCompatActivity;
    private MyTypeFace myTypeFace;
    private Button btnSigIn;
    private Button btnSigUp;
    private Button btnPedirServicio;
    private Button btnConfirmarServicio;
    private ImageButton imageButtonCerrarServicio;
    private TextView lblIncioAddress;
    private TextView lblFinAaddress;
    private TextView lblTarifaServicio;
    private TextView txtTarifa;
    private TextView txtCabecera;
    private Button   btnRegistro;
    private EditText editName;
    private EditText editApaterno;
    private EditText editAmaterno;
    private EditText editDni;
    private EditText editCelular;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editConfirPassword;
    private EditText editAddresss;
    private EditText editAddresssFinal;
    private EditText editFechaServicio;
    private EditText editHoraServicio;
    private EditText editHistoricoServiciosCreados;
    private ActionBar actionBar;
    private DrawerLayout drawer;
    private Spinner spiner;
    private Spinner spinerZona;
    private CheckBox checkBoxFecha;
    private CheckBox checkBoxHora;
    private NavigationView navigationView;
    private AutoCompleteTextView mAutocompleteView_AddressIncio;
    private  AutoCompleteTextView mAutocompleteView_AddressFinal;
    private GridViewWithHeaderAndFooter grid;
    private ImageButton imageButtonBuscar;
    Toolbar toolbar;
    public ComponentesR(Context context) {
        this.context = context;
        appCompatActivity= (AppCompatActivity) context;
        myTypeFace=new MyTypeFace(context);
    }

    public void Controls_LoginActivity(Activity loginActivity) {

        editEmail=(EditText)loginActivity.findViewById(R.id.editEmail);
        editPassword=(EditText)loginActivity.findViewById(R.id.editPass);
        btnSigIn=(Button)loginActivity.findViewById(R.id.btnSigIn);
        btnSigIn.setOnClickListener((View.OnClickListener) context);
        btnSigUp=(Button)loginActivity.findViewById(R.id.btnSigUp);
        btnSigUp.setOnClickListener((View.OnClickListener) context);
         toolbar=(Toolbar)loginActivity.findViewById(R.id.appbar);
        appCompatActivity.setSupportActionBar(toolbar);
    }

    public void Controls_FrmSigUp(Activity frmSigup) {
        txtCabecera=(TextView)frmSigup.findViewById(R.id.txtCabecera);
        txtCabecera.setTypeface(myTypeFace.openRobotoLight());
        toolbar=(Toolbar)frmSigup.findViewById(R.id.appbar);
        appCompatActivity.setSupportActionBar(toolbar);

        editName=(EditText)frmSigup.findViewById(R.id.editName);
        editApaterno=(EditText)frmSigup.findViewById(R.id.editApaterno);
        editAmaterno=(EditText)frmSigup.findViewById(R.id.editAmaterno);
        editDni=(EditText)frmSigup.findViewById(R.id.editDNI);
        editEmail=(EditText)frmSigup.findViewById(R.id.editEmail);
        editCelular=(EditText)frmSigup.findViewById(R.id.editCelular);
        editPassword=(EditText)frmSigup.findViewById(R.id.editPass);
        editConfirPassword=(EditText)frmSigup.findViewById(R.id.editConfirPass);

        btnRegistro=(Button)frmSigup.findViewById(R.id.btnRegistrar);
        btnRegistro.setOnClickListener((View.OnClickListener) context);
    }

    public void Contros_MainActivity(Activity mainActivity) {
        toolbar=(Toolbar)mainActivity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        editAddresss=(EditText)mainActivity.findViewById(R.id.editAddress);
        editAddresssFinal=(EditText)mainActivity.findViewById(R.id.editAddresFinal);
        drawer =(DrawerLayout)mainActivity.findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)mainActivity.findViewById(R.id.nav_view);

        btnPedirServicio=(Button)mainActivity.findViewById(R.id.btnPedirServicio);
        btnPedirServicio.setOnClickListener((View.OnClickListener) context);
    }

    public void Controls_fragmentDataClient(View rootView) {
        editName=(EditText)rootView.findViewById(R.id.editName);
        editApaterno=(EditText)rootView.findViewById(R.id.editApaterno);
        editAmaterno=(EditText)rootView.findViewById(R.id.editAmaterno);
        editDni=(EditText)rootView.findViewById(R.id.editDNI);
        editEmail=(EditText)rootView.findViewById(R.id.editEmail);
        editCelular=(EditText)rootView.findViewById(R.id.editCelular);

    }
    public void Fragment_Solicitar_Servicio(View rootView) {
        spiner=(Spinner)rootView.findViewById(R.id.spinerDistrito);
        spinerZona=(Spinner)rootView.findViewById(R.id.spinerZona);
        mAutocompleteView_AddressIncio=(AutoCompleteTextView)rootView.findViewById(R.id.autocomplete_AddresIncio);

    }
    public void Contros_Alert_Pedir_Servicio(View view) {
        mAutocompleteView_AddressIncio=(AutoCompleteTextView)view.findViewById(R.id.autocomplete_AddresIncio);
        mAutocompleteView_AddressFinal=(AutoCompleteTextView)view.findViewById(R.id.autocomplete_placesFinal);
        lblFinAaddress=(TextView)view.findViewById(R.id.lblAddresFin);
        lblIncioAddress=(TextView)view.findViewById(R.id.lblAddresIncio);
        lblTarifaServicio=(TextView)view.findViewById(R.id.lblTarifaServicio);
        txtTarifa=(TextView)view.findViewById(R.id.txtTarifa);
        btnConfirmarServicio=(Button)view.findViewById(R.id.btnConfirmarServicio);
        editFechaServicio=(EditText)view.findViewById(R.id.editFechaServicio);
        editHoraServicio=(EditText)view.findViewById(R.id.editHoraServicio);
        checkBoxFecha=(CheckBox)view.findViewById(R.id.chxFecha);
        checkBoxHora=(CheckBox)view.findViewById(R.id.chxHora);

        imageButtonCerrarServicio=(ImageButton)view.findViewById(R.id.imageButtonCerrar);

        lblTarifaServicio.setTypeface(myTypeFace.openRobotoLight());
      //  lblIncioAddress.setTypeface(myTypeFace.openRobotoLight());
       // lblFinAaddress.setTypeface(myTypeFace.openRobotoLight());
        lblTarifaServicio.setTypeface(myTypeFace.openRobotoLight());
    }

    public void Controls_fragment_Historico_Sercicios_new(View rootView) {
        grid= (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.gridview);
    }
    public void Controls_fragment_Historia_Servicios_createHeader(View view) {
        editHistoricoServiciosCreados=(EditText)view.findViewById(R.id.editHistoriaServiciosCreados);
        checkBoxFecha=(CheckBox) view.findViewById(R.id.chxFecha);
        imageButtonBuscar=(ImageButton)view.findViewById(R.id.imageButtonSearch);

    }

    public GridViewWithHeaderAndFooter getGrid() {
        return grid;
    }

    public void setGrid(GridViewWithHeaderAndFooter grid) {
        this.grid = grid;
    }

    public EditText getEditHistoricoServiciosCreados() {
        return editHistoricoServiciosCreados;
    }

    public void setEditHistoricoServiciosCreados(EditText editHistoricoServiciosCreados) {
        this.editHistoricoServiciosCreados = editHistoricoServiciosCreados;
    }

    public EditText getEditFechaServicio() {
        return editFechaServicio;
    }

    public void setEditFechaServicio(EditText editFechaServicio) {
        this.editFechaServicio = editFechaServicio;
    }

    public EditText getEditHoraServicio() {
        return editHoraServicio;
    }

    public void setEditHoraServicio(EditText editHoraServicio) {
        this.editHoraServicio = editHoraServicio;
    }

    public ImageButton getImageButtonBuscar() {
        return imageButtonBuscar;
    }

    public void setImageButtonBuscar(ImageButton imageButtonBuscar) {
        this.imageButtonBuscar = imageButtonBuscar;
    }

    public ImageButton getImageButtonCerrarServicio() {
        return imageButtonCerrarServicio;
    }

    public void setImageButtonCerrarServicio(ImageButton imageButtonCerrarServicio) {
        this.imageButtonCerrarServicio = imageButtonCerrarServicio;
    }

    public CheckBox getCheckBoxHora() {
        return checkBoxHora;
    }

    public void setCheckBoxHora(CheckBox checkBoxHora) {
        this.checkBoxHora = checkBoxHora;
    }

    public CheckBox getCheckBoxFecha() {
        return checkBoxFecha;
    }

    public void setCheckBoxFecha(CheckBox checkBoxFecha) {
        this.checkBoxFecha = checkBoxFecha;
    }

    public TextView getTxtTarifa() {
        return txtTarifa;
    }

    public void setTxtTarifa(TextView txtTarifa) {
        this.txtTarifa = txtTarifa;
    }

    public TextView getLblTarifaServicio() {
        return lblTarifaServicio;
    }

    public void setLblTarifaServicio(TextView lblTarifaServicio) {
        this.lblTarifaServicio = lblTarifaServicio;
    }

    public TextView getLblFinAaddress() {
        return lblFinAaddress;
    }

    public void setLblFinAaddress(TextView lblFinAaddress) {
        this.lblFinAaddress = lblFinAaddress;
    }

    public TextView getLblIncioAddress() {
        return lblIncioAddress;
    }

    public void setLblIncioAddress(TextView lblIncioAddress) {
        this.lblIncioAddress = lblIncioAddress;
    }

    public Button getBtnConfirmarServicio() {
        return btnConfirmarServicio;
    }

    public void setBtnConfirmarServicio(Button btnConfirmarServicio) {
        this.btnConfirmarServicio = btnConfirmarServicio;
    }

    public Spinner getSpinerZona() {
        return spinerZona;
    }

    public void setSpinerZona(Spinner spinerZona) {
        this.spinerZona = spinerZona;
    }

    public Spinner getSpiner() {
        return spiner;
    }

    public void setSpiner(Spinner spiner) {
        this.spiner = spiner;
    }

    public Button getBtnPedirServicio() {
        return btnPedirServicio;
    }

    public void setBtnPedirServicio(Button btnPedirServicio) {
        this.btnPedirServicio = btnPedirServicio;
    }

    public EditText getEditAddresssFinal() {
        return editAddresssFinal;
    }

    public void setEditAddresssFinal(EditText editAddresssFinal) {
        this.editAddresssFinal = editAddresssFinal;
    }

    public AutoCompleteTextView getmAutocompleteView_AddressIncio() {
        return mAutocompleteView_AddressIncio;
    }

    public void setmAutocompleteView_AddressIncio(AutoCompleteTextView mAutocompleteView_AddressIncio) {
        this.mAutocompleteView_AddressIncio = mAutocompleteView_AddressIncio;
    }

    public AutoCompleteTextView getmAutocompleteView_AddressFinal() {
        return mAutocompleteView_AddressFinal;
    }

    public void setmAutocompleteView_AddressFinal(AutoCompleteTextView mAutocompleteView_AddressFinal) {
        this.mAutocompleteView_AddressFinal = mAutocompleteView_AddressFinal;
    }

    public EditText getEditAddresss() {
        return editAddresss;
    }

    public void setEditAddresss(EditText editAddresss) {
        this.editAddresss = editAddresss;
    }

    public Button getBtnSigUp() {
        return btnSigUp;
    }

    public void setBtnSigUp(Button btnSigUp) {
        this.btnSigUp = btnSigUp;
    }

    public Button getBtnSigIn() {
        return btnSigIn;
    }

    public void setBtnSigIn(Button btnSigIn) {
        this.btnSigIn = btnSigIn;
    }

    public EditText getEditName() {
        return editName;
    }

    public void setEditName(EditText editName) {
        this.editName = editName;
    }

    public EditText getEditApaterno() {
        return editApaterno;
    }

    public void setEditApaterno(EditText editApaterno) {
        this.editApaterno = editApaterno;
    }

    public EditText getEditAmaterno() {
        return editAmaterno;
    }

    public void setEditAmaterno(EditText editAmaterno) {
        this.editAmaterno = editAmaterno;
    }

    public EditText getEditDni() {
        return editDni;
    }

    public void setEditDni(EditText editDni) {
        this.editDni = editDni;
    }

    public EditText getEditCelular() {
        return editCelular;
    }

    public void setEditCelular(EditText editCelular) {
        this.editCelular = editCelular;
    }

    public EditText getEditPassword() {
        return editPassword;
    }

    public void setEditPassword(EditText editPassword) {
        this.editPassword = editPassword;
    }

    public EditText getEditEmail() {
        return editEmail;
    }

    public void setEditEmail(EditText editEmail) {
        this.editEmail = editEmail;
    }

    public Button getBtnRegistro() {
        return btnRegistro;
    }

    public void setBtnRegistro(Button btnRegistro) {
        this.btnRegistro = btnRegistro;
    }

    public TextView getTxtCabecera() {
        return txtCabecera;
    }

    public void setTxtCabecera(TextView txtCabecera) {
        this.txtCabecera = txtCabecera;
    }

    public EditText getEditConfirPassword() {
        return editConfirPassword;
    }

    public void setEditConfirPassword(EditText editConfirPassword) {
        this.editConfirPassword = editConfirPassword;
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

    public MyTypeFace getMyTypeFace() {
        return myTypeFace;
    }

    public void setMyTypeFace(MyTypeFace myTypeFace) {
        this.myTypeFace = myTypeFace;
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }


}
