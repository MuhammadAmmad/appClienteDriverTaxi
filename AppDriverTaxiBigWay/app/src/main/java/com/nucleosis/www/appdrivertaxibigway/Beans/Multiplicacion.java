package com.nucleosis.www.appdrivertaxibigway.Beans;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class Multiplicacion extends BaseObject {
    public static Class EVENT_CLASS=new Multiplicacion().getClass();
    private double numero1;
    private double numero2;

    public double getNumero2() {
        return numero2;
    }

    public void setNumero2(double numero2) {
        this.numero2 = numero2;
    }

    public double getNumero1() {
        return numero1;
    }

    public void setNumero1(double numero1) {
        this.numero1 = numero1;
    }

    @Override
    public Object getProperty(int index) {
        switch (index){
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
                numero1=Double.parseDouble(value.toString());
                break;
            case 1:
                numero2=Double.parseDouble(value.toString());
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index){
            case 0:
                info.type=MarshalDouble.class;
                info.name="numero1";
            case 1:
                info.type=MarshalDouble.class;
                info.name="numero2";
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
