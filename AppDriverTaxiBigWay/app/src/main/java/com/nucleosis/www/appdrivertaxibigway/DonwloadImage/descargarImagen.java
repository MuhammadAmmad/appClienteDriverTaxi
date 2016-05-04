package com.nucleosis.www.appdrivertaxibigway.DonwloadImage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by karlos on 17/04/2016.
 */
public class descargarImagen extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap imagen = donwloadaImage(url);
        return imagen;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    private Bitmap donwloadaImage (String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }
}
