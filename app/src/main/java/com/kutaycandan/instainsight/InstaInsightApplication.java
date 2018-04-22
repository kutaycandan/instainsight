package com.kutaycandan.instainsight;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class InstaInsightApplication extends Application {
    static InstaInsightApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static InstaInsightApplication newInstance() {
        return instance;
    }


}