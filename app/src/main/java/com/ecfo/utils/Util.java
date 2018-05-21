package com.ecfo.utils;


import android.app.Activity;
import android.content.Intent;

import com.ecfo.R;
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
}
