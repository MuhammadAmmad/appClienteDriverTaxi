package com.nucleosis.www.appclientetaxibigway.TypeFace;
import android.content.Context;
import android.graphics.Typeface;
import com.nucleosis.www.appclientetaxibigway.Constantes.ConstantesTypeFace;

/**
 * Created by carlos.lopez on 14/03/2016.
 */
public class MyTypeFace {
    private Context context;
    private Typeface mTf;
    public MyTypeFace(Context context) {
        this.context = context;
    }

    public Typeface OpenSansRegular(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getOpenSansRegular());
        return mTf;
    }
    public Typeface openRobotoLight(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getRobotoLight());
        return mTf;
    }

    public Typeface opencodeLight(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getCodeLight());
        return mTf;
    }
    public Typeface opencodeBold(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getCodeBold());
        return mTf;
    }

    public Typeface opengalderglynn(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getGalderglynn());
        return mTf;
    }

    public Typeface openSlabTallX_Normal(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getSlabTallX_Normal());
        return mTf;
    }
    public Typeface openSlabTallX_Medium(){
        mTf = Typeface.createFromAsset(context.getAssets(), ConstantesTypeFace.getSlabTallX_Medium());
        return mTf;
    }

}
