package com.malin.love.wangyayun.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by malin on 16-1-5.
 */
public class LoveApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
