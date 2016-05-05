package com.nucleosis.www.appdrivertaxibigway.Constans;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.nucleosis.www.appdrivertaxibigway.Circle.BadgeDrawable;
import com.nucleosis.www.appdrivertaxibigway.R;
/**
 * Created by carlos.lopez on 23/03/2016.
 */
public class Alerta {
    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {
        BadgeDrawable badge;

        // Reusar drawable
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
}
