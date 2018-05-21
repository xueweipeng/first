package com.ecfo;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.Utils;
import com.ecfo.play.PlayNotification;
import com.ecfo.play.PlayService;

import org.litepal.LitePal;


public class ecfoApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static ecfoApplication app;
    private IPlayer mPlayerService;
    private PlayNotification mPlayNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LitePal.initialize(this);
        app = this;
        bindService();
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
    }

    public IPlayer getMusicPlayerService() {
        return mPlayerService;
    }

    private void bindService() {
        Intent intent = new Intent(ecfoApplication.app, PlayService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    static boolean linkSuccess;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerService = IPlayer.Stub.asInterface(service);
            linkSuccess = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindService();
        }
    };

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    int isCurrentRunningForeground;


    @Override
    public void onActivityStarted(Activity activity) {
        if (isCurrentRunningForeground == 0) {// front
            if (mPlayNotification != null) {
                mPlayNotification.unregisterListener();
            }
        }
        isCurrentRunningForeground++;
    }

    private void showNotification() throws RemoteException {
        if (mPlayNotification == null) {
            mPlayNotification = new PlayNotification(app, mPlayerService);
        }
        mPlayNotification.registerListener();
        mPlayNotification.notifyMusic();
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        isCurrentRunningForeground--;
        if (isCurrentRunningForeground == 0) { // back
            try {
                showNotification();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
