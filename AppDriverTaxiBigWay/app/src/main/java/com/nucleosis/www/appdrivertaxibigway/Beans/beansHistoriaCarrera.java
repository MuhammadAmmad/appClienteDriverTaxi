package com.nucleosis.www.appdrivertaxibigway.Beans;

/**
 * Created by karlos on 03/04/2016.
 */
public class beansHistoriaCarrera {
    String Name;
    String dateTime;
    int status;
    int imageHistoria;
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImageHistoria() {
        return imageHistoria;
    }

    public void setImageHistoria(int imageHistoria) {
        this.imageHistoria = imageHistoria;
    }
}
