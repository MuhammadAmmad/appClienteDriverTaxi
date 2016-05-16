package com.nucleosis.www.appdrivertaxibigway.GooglePlaceRest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos.lopez on 20/04/2016.
 */
public class AddresRestmap extends AsyncTask<String,String,String> {
    private Context context;
    private String lat;
    private String lng;


    String Url=null;
    public AddresRestmap(Context context, String lat, String lng, int casoMarker) {
        this.context = context;
        this.lat=lat;
        this.lng=lng;
        Log.d("adress_inicio_fin",lat+"****"+lng);
        Url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=";
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonObject = "";
        List<String> response = new ArrayList<String>();
        try {

            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line + "\n");
            }
            br.close();
            jsonObject = json.toString();
            Log.d("json_", jsonObject.toString());
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        if (data != null) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.has("results")) {
                    String results = jsonObject.getString("results");
                    JSONArray jsonArray = new JSONArray(results.toString());
                    ///   Log.d("varlores>>>", jsonArray.getJSONObject(0).getString("formatted_address"));
                    String address = jsonArray.getJSONObject(0).getString("formatted_address");

                    String addressOptional=jsonArray.getJSONObject(0).getString("address_components");
                    JSONArray jsonArray1=new JSONArray(addressOptional.toString());
                   ///     Log.d("adres-->", address.toString());
                    String shortNumer=jsonArray1.getJSONObject(0).getString("short_name");
                    String shortName=jsonArray1.getJSONObject(1).getString("short_name");
                    String shortDistrito=jsonArray1.getJSONObject(2).getString("short_name");
                     String shortDpto=jsonArray1.getJSONObject(3).getString("short_name");
                 //   Log.d("Numero->", shortNumer + "\n" + shortName + "\n" + shortDistrito + "\n" + shortDpto);
                  //  Log.d("casoMarker",String.valueOf(casoMarker));


                    //  Toast.makeText(MainActivity.this,address,Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
