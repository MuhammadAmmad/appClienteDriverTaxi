package com.nucleosis.www.appdrivertaxibigway.Beans;

import android.widget.Switch;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by karlos on 20/03/2016.
 */
public class Category implements KvmSerializable {
    public int numero1;
    public int numero2;
    public Category(){}

    public Category(int numero1, int numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
    }

    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return numero1;

            case 1:
                return numero2;
            default:
                return null;
        }

    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index){
            case 0:
                numero1=Integer.parseInt(value.toString());
                break;
            case 1:
                numero2=Integer.parseInt(value.toString());
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable hashtable, PropertyInfo info) {
        switch (index){
            case 0:
                info.type=PropertyInfo.INTEGER_CLASS;
                info.name="numero1";
                break;
            case 1:
                info.type=PropertyInfo.INTEGER_CLASS;
                info.name="numero2";
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
