package com.nucleosis.www.appclientetaxibigway.beans;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class beansDistritos {
    private int IdDistrito;
    private String NameDistrito;

    public int getIdDistrito() {
        return IdDistrito;
    }

    public void setIdDistrito(int idDistrito) {
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
