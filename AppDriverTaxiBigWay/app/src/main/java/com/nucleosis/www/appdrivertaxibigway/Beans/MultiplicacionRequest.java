package com.nucleosis.www.appdrivertaxibigway.Beans;

import android.util.Log;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by karlos on 20/03/2016.
 */
public class MultiplicacionRequest implements KvmSerializable {
    private String Numero1;
    private String Numero2;

    public String getNumero1() {
        return Numero1;
    }
    public MultiplicacionRequest(){}

    public MultiplicacionRequest(String numero1, String numero2) {
        Numero1 = numero1;
        Numero2 = numero2;
    }

    public void setNumero1(String numero1) {
        Numero1 = numero1;
    }

    public String getNumero2() {
        return Numero2;
    }

    public void setNumero2(String numero2) {
        Numero2 = numero2;
    }

    @Override
    public Object getProperty(int index) {
        switch (index){
            case 0:
                Log.d("numericosss",Numero1);
                return Numero1;
            case 1:
                return Numero2;
            default:
                return null;
        }

    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int index, Object obj) {
        switch (index){
            case 0:
                this.Numero1=obj.toString();
                break;
            case 1:
                this.Numero2=obj.toString();
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo info) {
        switch(index){
            case 0:
                info.name = "Numero1";
                info.type = PropertyInfo.STRING_CLASS;
                break;
            case 1:
                info.name = "Numero2";
                info.type = PropertyInfo.STRING_CLASS;
                break;
            default:
                break;
        }
    }

    @Override
    public String getInnerText() {
        return null;
    }

    @Override
    public void setInnerText(String s) {

    }
}
