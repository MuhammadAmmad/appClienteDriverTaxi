package com.nucleosis.www.appclientetaxibigway;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.nucleosis.www.appclientetaxibigway.Fragments.FragmentListaServicios;

public class ListaServicios extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicios);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setFragment(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
//                FragmentHistoriaCarreras HistorialCarreras = new FragmentHistoriaCarreras();
                FragmentListaServicios fragmentListaServicios=new FragmentListaServicios();
                //inboxFragment.newInstance(1);
                fragmentTransaction.replace(R.id.fragment, fragmentListaServicios);
                fragmentTransaction.commit();
                break;
        }
    }


}
