package com.malin.love.wangyayun.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * @author malin.myemail@163.com
 * @date 16-5-15.15:28
 */
@SuppressWarnings("ALL")
public class UnBindResourceUtil {

    private static final String TAG = UnBindResourceUtil.class.getSimpleName();

    private UnBindResourceUtil() {
    }

    /**
     */
    public static void unBindDrawables(View view) {
        if (view != null) {
            try {
                Drawable drawable = view.getBackground();
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int viewGroupChildCount = viewGroup.getChildCount();
                    for (int j = 0; j < viewGroupChildCount; j++) {
                        unBindDrawables(viewGroup.getChildAt(j));
                    }
                    viewGroup.removeAllViews();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Remove an onclick listener
     */
    public static void unBingListener(View view) {
        if (view != null) {
            try {
                if (view.hasOnClickListeners()) {
                    view.setOnClickListener(null);
                }
                if (view instanceof TextView){
                   ((TextView) view).addTextChangedListener(null);
                    ((TextView) view).setOnEditorActionListener(null);
                }
                if (view.getOnFocusChangeListener() != null) {
                    view.setOnFocusChangeListener(null);
                }

                if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int viewGroupChildCount = viewGroup.getChildCount();
                    for (int i = 0; i < viewGroupChildCount; i++) {
                        unBingListener(viewGroup.getChildAt(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 回收Bitmap
     *
     * @param bitmap
     */
    public static void unBindBitmap(Bitmap bitmap) {
        try {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
