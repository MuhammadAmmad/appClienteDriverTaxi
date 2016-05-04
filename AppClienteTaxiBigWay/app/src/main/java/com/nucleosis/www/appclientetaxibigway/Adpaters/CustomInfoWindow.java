package com.nucleosis.www.appclientetaxibigway.Adpaters;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nucleosis.www.appclientetaxibigway.MainActivity;
import com.nucleosis.www.appclientetaxibigway.R;

/**
 * Created by carlos.lopez on 27/04/2016.
 */
public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private MainActivity cx=new MainActivity();
    private Context context;

    public  CustomInfoWindow(Context context){
        myContentsView = cx.MAIN_ACTIVITY.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        this.context=context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
        tvTitle.setText(marker.getTitle());
        TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
        tvSnippet.setText(marker.getSnippet());
        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
     /*   if(marker.getId().equals("m1")){
            Toast.makeText(context, "hellooooo", Toast.LENGTH_SHORT).show();
        }*/
        return null;
    }

}