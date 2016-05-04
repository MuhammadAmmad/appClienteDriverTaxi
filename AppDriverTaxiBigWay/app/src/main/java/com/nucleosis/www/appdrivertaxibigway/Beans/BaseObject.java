package com.nucleosis.www.appdrivertaxibigway.Beans;

import org.ksoap2.serialization.KvmSerializable;

/**
 * Created by karlos on 20/03/2016.
 */
public abstract class BaseObject implements KvmSerializable {
    public static final String NAMESPACE="http://programacionj2ee.com/ws/schemas";
    public BaseObject(){
        super();
    }
}
