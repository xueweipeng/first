package com.ecfo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import org.litepal.LitePal;


public class ecfoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LitePal.initialize(this);
    }
}
