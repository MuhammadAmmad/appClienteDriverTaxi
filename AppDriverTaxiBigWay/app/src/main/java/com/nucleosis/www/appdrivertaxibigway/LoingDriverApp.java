package com.nucleosis.www.appdrivertaxibigway;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.nucleosis.www.appdrivertaxibigway.Beans.User;
import com.nucleosis.www.appdrivertaxibigway.Componentes.componentesR;

import static com.nucleosis.www.appdrivertaxibigway.Constans.Utils.LOGGIN_USUARIO;
public class LoingDriverApp extends AppCompatActivity     implements View.OnClickListener{

private componentesR compR;

     public static Activity LoingDriverApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loggin_driver);
        LoingDriverApp=this;
        compR = new componentesR(LoingDriverApp.this);
        compR.cargar_toolbar(LoingDriverApp);
        compR.Controls_LoginDriverApp(LoingDriverApp);
       // compR.getEditUser().setText("45845785");
        compR.getEditUser().setText("33333333");
        compR.getEditPass().setText("123456");
       // compR.getBtnSigIn().setOnClickListener(this);
         }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
   *//*     if (id == R.id.Historial_ubi) {
            return true;
        }*//*
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginDriver:
               /* String USERX=compR.getEditUser().getText().toString().trim();
                String PASSX=compR.getEditPass().getText().toString().trim();*/
                User user=null;
                try {
                    user=new User();
                    user.setUser(compR.getEditUser().getText().toString().trim());
                    user.setPassword(compR.getEditPass().getText().toString().trim());
                    LOGGIN_USUARIO.Login(user,LoingDriverApp);
                    //Toast.makeText(getApplicationContext(),saludo,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_LONG).show();
                /*new cliente().execute();*/
                break;
        }
    }


}
