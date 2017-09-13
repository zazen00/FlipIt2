package com.globalappdev.flipit;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by Owner on 8/30/2017.
 */

public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    private int id;
    private int ids[];
    private int border = 0;
    private float speed = 0.2f;

    public ParallaxPageTransformer(int bg, int... ids) {
        this.id = bg;
        this.ids = ids;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {
        boolean bOther = (ids.length != 0);
        View parallaxView = view.findViewById(id);

        if (view == null ) {
            Log.w("ParallaxPage", "There is no view");
        }

        if (parallaxView != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ) {
            if (position > -1 && position < 1) {
                float width = parallaxView.getWidth();
                parallaxView.setTranslationX(-(position * width * speed));
                if(bOther){
                    for (int oid:ids) {
                        View v = view.findViewById(oid);
                        v.setTranslationX((position * width * speed));
                        if (position == 0) {
                            view.setAlpha(1);
                        } else {
                            v.setAlpha(1-(Math.abs(position)));

                        }
                    }
                }
                float sc = (((float)view.getWidth() - border)/ view.getWidth());
                if (position == 0) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else {
                    view.setScaleX(sc);
                    view.setScaleY(sc);
                }
            }
        }
    }

    public void setBorder(int px) {
        border = px;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
