package com.ecfo.utils;


import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.ecfo.R;
import com.ecfo.ecfoApplication;
import com.ecfo.play.PlayActivity;

public class Util {
    public static void startPlayerActivity(Activity activity) {
        Intent intent = new Intent(activity, PlayActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_down, 0);
    }

    public static void finishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, R.anim.out_to_down);
    }

    public static void performPlayAction(int action, String param) {
        try {
            ecfoApplication.app.getMusicPlayerService().action(action, param);
        } catch (RemoteException e) {
            LogUtils.file("play service not start:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
