package com.nucleosis.www.appdrivertaxibigway.Beans;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class beansDistritos {
    private String IdDistrito;
    private String NameDistrito;

    public String getIdDistrito() {
        return IdDistrito;
    }

    public void setIdDistrito(String idDistrito) {
        IdDistrito = idDistrito;
    }

    public String getNameDistrito() {
        return NameDistrito;
    }

    public void setNameDistrito(String nameDistrito) {
        NameDistrito = nameDistrito;
    }

    @Override
    public String toString() {
        return NameDistrito;
    }
}
