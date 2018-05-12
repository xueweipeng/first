package com.ecfo.play;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.ecfo.IPlayer;
import com.ecfo.IPlayerListener;
import com.ecfo.R;
import com.ecfo.activities.MainActivity;
import com.ecfo.modules.lesson.mvp.module.beans.Lesson;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.ecfo.play.PlayService.MUSIC_ACTION_PAUSE;
import static com.ecfo.play.PlayService.MUSIC_ACTION_PLAY;

/**
 * author：agxxxx on 2017/3/6 17:31
 * email：agxxxx@126.com
 * blog: http://www.jianshu.com/u/c1a3c4c943e5
 * github: https://github.com/agxxxx
 * Created by Administrator on 2017/3/6.
 */

public class PlayNotification {


    private final IPlayer mPlayerService;
    private final Context mContext;
    private NotificationManager mNotificationManager;
    private Notification mNotification;


    public PlayNotification(Context context, IPlayer playerService) {
        this.mPlayerService = playerService;
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

    }


    IPlayerListener mPlayerListener = new IPlayerListener.Stub() {
        @Override
        public void action(int action, Message msg) throws RemoteException {
            switch (action) {
                case MUSIC_ACTION_PLAY:
                case MUSIC_ACTION_PAUSE:
                    notifyMusic();
                    break;

            }
        }
    };

    public void registerListener() {
        try {
            if (mPlayerService != null) {
                mPlayerService.registerListener(mPlayerListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterListener() {
        if (mPlayerService != null) {
            try {
                mPlayerService.unregisterListener(mPlayerListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 1.play
     * 2.pause
     * 3.next
     * 4.open
     * 5.close
     *
     * @param context
     * @param playerService
     * @return
     */
    private Notification createNotification(Context context, IPlayer playerService) {
        try {
            Lesson lesson = (Lesson) playerService.getCurrentSongInfo().obj;
            if (lesson == null) {
                return null;
            }
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent openPendingIntent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteView = createRemoteView(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContent(remoteView);
            builder.setContentTitle(lesson.title);
            builder.setTicker("课程已移到后台");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(openPendingIntent);

            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                builder.setShowWhen(false);
            }
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            NotificationTarget notificationTarget = new NotificationTarget(context, R.id.iv_icon, remoteView, notification, 0);
            Glide.with(context.getApplicationContext())
                    .asBitmap()
                    .load(lesson.big_pic_url)
                    .into(notificationTarget);
            return notification;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void notifyMusic() {
        mNotification = createNotification(mContext, mPlayerService);
        mNotificationManager.notify(0, mNotification);
    }


    RemoteViews createRemoteView(Context context) throws RemoteException {
        final Lesson lesson = (Lesson) mPlayerService.getCurrentSongInfo().obj;
        if (lesson == null) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_player);
        remoteViews.setTextViewText(R.id.tv_title, lesson.title);
        remoteViews.setTextViewText(R.id.tv_content, lesson.getTitle() + "-" + lesson.title);


//        1. 2. play and pause
        if (PlayService.MUSIC_CURRENT_ACTION == MUSIC_ACTION_PLAY) {
            remoteViews.setImageViewResource(R.id.iv_pause, R.mipmap.note_btn_pause);
        } else if (PlayService.MUSIC_CURRENT_ACTION == MUSIC_ACTION_PAUSE) {
            remoteViews.setImageViewResource(R.id.iv_pause, R.mipmap.note_btn_play);
        }


        Intent intent = new Intent(PlayNotificationReceiver.ACTION_MUSIC_PLAY);
        intent.putExtra("broadcast_action", MUSIC_ACTION_PLAY);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_pause, pendingIntent);

//        3.next
        Intent nextIntent = new Intent(PlayNotificationReceiver.ACTION_MUSIC_NEXT);
        nextIntent.putExtra("broadcast_action", PlayService.MUSIC_ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 3, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_next, nextPendingIntent);

//        5.close
//        ....

        return remoteViews;
    }

//
}
