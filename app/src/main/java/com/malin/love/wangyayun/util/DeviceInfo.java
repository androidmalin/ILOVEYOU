package com.malin.love.wangyayun.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 类描述:手机设备的信息
 * 创建人:malin.myemail@gmail.com
 * 创建时间:15-11-10.下午3:26
 * 备注:
 * 参考项目:Android-Universal-Image-Loader
 */
public class DeviceInfo {

    public static int mScreenWidthForPortrait;   // 屏幕宽度
    public static int mScreenHeightForPortrait;  // 屏幕高度
    public static float mDensity;


    private volatile static DeviceInfo instance;

    /**
     * Returns singleton class instance
     */
    public static DeviceInfo getInstance() {
        if (instance == null) {
            synchronized (DeviceInfo.class) {
                if (instance == null) {
                    instance = new DeviceInfo();
                }
            }
        }
        return instance;
    }

    protected DeviceInfo() {

    }


    /**
     * init get device information
     *
     * @param activity
     */
    public void initializeScreenInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mDensity = displayMetrics.density;
        if (displayMetrics.heightPixels >= displayMetrics.widthPixels) {
            mScreenWidthForPortrait = displayMetrics.widthPixels;
            mScreenHeightForPortrait = displayMetrics.heightPixels;
        } else {
            mScreenWidthForPortrait = displayMetrics.heightPixels;
            mScreenHeightForPortrait = displayMetrics.widthPixels;
        }
    }
}
