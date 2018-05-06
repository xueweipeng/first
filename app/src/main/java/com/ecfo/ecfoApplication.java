package com.ecfo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


public class ecfoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
