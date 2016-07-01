package com.nucleosis.www.appclientetaxibigway;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.nucleosis.www.appclientetaxibigway.beans.dataClienteSigUp;
import com.nucleosis.www.appclientetaxibigway.componentes.ComponentesR;

import static com.nucleosis.www.appclientetaxibigway.Constantes.UtilsInterfaces.LOGGIN_USUARIO;

public class FrmSigUp extends AppCompatActivity implements View.OnClickListener{

    private ComponentesR compR;
    public static Activity FRM_SIGUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_up);
        FRM_SIGUP=this;
        compR=new ComponentesR(FrmSigUp.this);
        compR.Controls_FrmSigUp(FRM_SIGUP);
    /*    compR.getEditName().setText("Cliente");
        compR.getEditApaterno().setText("taxiBig");
        compR.getEditAmaterno().setText("way");
        compR.getEditDni().setText("145854125");
        compR.getEditEmail().setText("taxi@gmail.com");
        compR.getEditCelular().setText("987654321");
        compR.getEditPassword().setText("123456");
        compR.getEditConfirPassword().setText("123456");*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar:
                dataClienteSigUp row=new dataClienteSigUp();

                String nombre=compR.getEditName().getText().toString().trim();
                String apadre=compR.getEditApaterno().getText().toString().trim();
                String amadre=compR.getEditAmaterno().getText().toString().trim();
                String dni=compR.getEditDni().getText().toString().trim();
                String email=compR.getEditEmail().getText().toString().trim();
                String password=compR.getEditPassword().getText().toString().trim();
                String confirPass=compR.getEditConfirPassword().getText().toString().trim();
                String celular=compR.getEditCelular().getText().toString().trim();

                if(nombre.length()!=0 &&
                        email.length()!=0 &&
                        password.length()!=0 &&
                        confirPass.length()!=0 &&
                        celular.length()!=0){
                    row.setNombre(nombre.toUpperCase());
                    row.setaPaterno("");
                    row.setaMaterno("");
                    row.setDni("");
                    row.setEmail(email);
                    row.setPassword(password);
                    row.setCelular(celular);
                    try {
                        LOGGIN_USUARIO.RegisterCliente(row,FrmSigUp.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(FrmSigUp.this, "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
