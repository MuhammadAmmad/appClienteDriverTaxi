package com.nucleosis.www.appdrivertaxibigway.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.nucleosis.www.appdrivertaxibigway.Adapters.GridAdapterHistorialCarrera;
import com.nucleosis.www.appdrivertaxibigway.Beans.beansHistoriaCarrera;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;
import com.nucleosis.www.appdrivertaxibigway.MainActivity;
import com.nucleosis.www.appdrivertaxibigway.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by carlos.lopez on 05/04/2016.
 */
public class FragmentHistoriNew extends Fragment {
    private int mYear, mMonth, mDay;
    private List<beansHistoriaCarrera> ITEMS_HISTORIAL;
    private componentesR compR;
    private String Fecha;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEMS_HISTORIAL=new ArrayList<beansHistoriaCarrera>();
        compR=new componentesR(getActivity());
        Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

    }
    public FragmentHistoriNew() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Historial Carreras");

        View rootView = inflater.inflate(R.layout.view_container, container, false);
        compR.Controls_fragment_Historia_Carrearas_new(rootView);
        setUpGridView(compR.getGrid());
        return rootView;
    }

    private void setUpGridView(GridViewWithHeaderAndFooter grid) {
                        grid.addHeaderView(createHeaderView());
                        grid.setAdapter(new GridAdapterHistorialCarrera(getActivity(),
                                ITEMS_HISTORIAL));
    }

    private View createHeaderView() {
        View view;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.view_cabecera_historial_carreras, null, false);
            compR.Controls_fragment_Historia_Carreras_createHeader(view);
            compR.getEditHistoriaCarrera().setText(mDay + "-"
                + (mMonth + 1) + "-" + mYear);
            compR.getChxBoxFecha().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        fechaHistorial(isChecked);

                }
            });
        return view;
    }





    private void fechaHistorial(boolean isChecked){
        if (isChecked) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            compR.getEditHistoriaCarrera().setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                            int suma = monthOfYear + 1;
                            if (suma < 10) {
                                Log.d("dia_", String.valueOf(dayOfMonth));
                                if(dayOfMonth<10){
                                    Fecha = String.valueOf(year) + "0" + String.valueOf(monthOfYear + 1)+"0"+String.valueOf(dayOfMonth);
                                }else{
                                    Fecha = String.valueOf(year) + "0" + String.valueOf(monthOfYear + 1)+String.valueOf(dayOfMonth);
                                }

                                Log.d("fecha_",Fecha);
                            } else {
                                if(dayOfMonth<10){
                                    Fecha = String.valueOf(year) + String.valueOf(monthOfYear + 1) +"0"+String.valueOf(dayOfMonth);
                                }else {
                                    Fecha = String.valueOf(year) + String.valueOf(monthOfYear + 1) +String.valueOf(dayOfMonth);

                                }

                                Log.d("fecha_",Fecha);
                            }
                            //  Log.d("date:",String.valueOf(mYear)+"*"+String.valueOf(mMonth+1)+"*"+String.valueOf(dayOfMonth));
                        }

                    }, mYear, mMonth, mDay);
            dpd.show();

        } else {
            compR.getEditHistoriaCarrera().setText("");
        }
    }
}
