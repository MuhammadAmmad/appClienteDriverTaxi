package com.ajustepantalla.respons;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout mContainer;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        final float height = metrics.heightPixels;
        final float width= metrics.widthPixels;
        Log.d("width_height",String.valueOf(width)+"-->"+String.valueOf(height));
        //  edittext.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,(int) (height/2)));

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams((int)(width/1.2),(int) (height/12));
        mContainer=(LinearLayout)findViewById(R.id.LinearContainer);

        LinearLayout linear1=new LinearLayout(this);
        linear1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        linear1.setGravity(Gravity.CENTER);
        linear1.setBackgroundColor(Color.RED);
        EditText  editText=new EditText(this);
        editText.setLayoutParams(params);
        editText.setBackgroundColor(Color.BLUE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                editText.setBackground(getResources().getDrawable(R.drawable.selector_edit,this.getTheme()));
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    editText.setBackground(getResources().getDrawable(R.drawable.selector_edit));
                }
            }

        linear1.addView(editText);
        mContainer.addView(linear1);
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        // buscando los pixeles a partir de dips con la densidad
        int dips = 200;
        float pixelBoton = 0;
        float scaleDensity = 0;
        switch(metrics.densityDpi)
        {
            case DisplayMetrics.DENSITY_LOW:  //LDPI
                Log.d("baja_Densidad","0.75");
                scaleDensity = scale * 120;
                pixelBoton = dips * (scaleDensity / 120);
                break;
            case DisplayMetrics.DENSITY_MEDIUM: //MDPI
                Log.d("mediana_Densidad","1");
                //equivale a un pixel
                scaleDensity = scale * 160;
                pixelBoton = dips * (scaleDensity / 160);
                break;
            case DisplayMetrics.DENSITY_HIGH: //HDPI
                Log.d("Alta_densidad","escala 1.5");
                scaleDensity = scale * 240;
                pixelBoton = dips * (scaleDensity / 240);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Log.d("info_metrica","2");
                scaleDensity = scale * 320;
                pixelBoton = dips * (scaleDensity / 320);
                break;
            case DisplayMetrics.DENSITY_400:
                Log.d("info_metrica","2.5");
                scaleDensity = scale * 400;
                pixelBoton = dips * (scaleDensity / 400);
                break;
            case DisplayMetrics.DENSITY_420:
                Log.d("info_metrica","2.625");
                scaleDensity = scale * 420;
                pixelBoton = dips * (scaleDensity / 420);
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                Log.d("info_metrica", "3");
                scaleDensity = scale * 480;
                pixelBoton = dips * (scaleDensity/480);
                break;
            case DisplayMetrics.DENSITY_560:
                Log.d("info_metrica", "3.5");
                scaleDensity = scale * 560;
                pixelBoton = dips * (scaleDensity / 560);
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                Log.d("Alta_densidad","4");
                scaleDensity = scale * 640;
                pixelBoton = dips * (scaleDensity / 640);
                break;
        }
        Log.d(getClass().getSimpleName(), "pixels:"+Float.toString(pixelBoton));
    }
}
