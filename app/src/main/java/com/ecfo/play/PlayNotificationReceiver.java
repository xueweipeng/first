package com.ecfo.play;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.ecfo.ecfoApplication;

import static com.ecfo.play.PlayService.MUSIC_ACTION_PLAY;

public class PlayNotificationReceiver extends BroadcastReceiver {

    public static final String ACTION_MUSIC_PLAY = "action.music.play";
    public static final String ACTION_MUSIC_NEXT = "action.music.next";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (ecfoApplication.app.getMusicPlayerService() == null) {
            return;
        }
        String action = intent.getAction();
        try {
            switch (action) {
                case ACTION_MUSIC_PLAY:
                    if (PlayService.MUSIC_CURRENT_ACTION == MUSIC_ACTION_PLAY) {
                        ecfoApplication.app.getMusicPlayerService().action(PlayService.MUSIC_ACTION_PAUSE, "");
                    } else {
                        ecfoApplication.app.getMusicPlayerService().action(PlayService.MUSIC_ACTION_CONTINUE_PLAY, "");
                    }
                    break;
                case ACTION_MUSIC_NEXT:
                    ecfoApplication.app.getMusicPlayerService().action(PlayService.MUSIC_ACTION_NEXT, "");
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
