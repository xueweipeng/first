package com.ecfo.play;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.ecfo.IPlayer;
import com.ecfo.IPlayerListener;
import com.ecfo.modules.lesson.mvp.module.beans.Lesson;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayService extends Service implements
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {

    public static final int MUSIC_ACTION_PLAY = 255;
    public static final int MUSIC_ACTION_PREVIOUS = 256;
    public static final int MUSIC_ACTION_NEXT = 257;
    public static final int MUSIC_ACTION_PAUSE = 259;
    public static final int MUSIC_ACTION_STOP = 260;
    public static final int MUSIC_ACTION_CONTINUE_PLAY = 280;
    public static final int MUSIC_ACTION_SEEK_PLAY = 270;
    public static final int MUSIC_ACTION_MUTE = 258;
    public static final int MUSIC_PLAY_MODE_RANDOM = 2001;//random
    public static final int MUSIC_PLAY_MODE_REPEAT = 2002;//sequence
    public static final int MUSIC_PLAY_MODE_SINGLE = 2003;//single
    public static final int PLAYER_LISTENER_ACTION_NORMAL = 1001;
    public static final int PLAYER_LISTENER_ACTION_DANGER = 1005;
    public static int MUSIC_CURRENT_ACTION = -1;
    public static int MUSIC_CURRENT_MODE = MUSIC_PLAY_MODE_RANDOM;
    /******************************************************************/
    private MediaPlayer mMediaPlayer;
    private Timer mTimer;

    private RemoteCallbackList<IPlayerListener> mListenerList
            = new RemoteCallbackList<>();


    private int currentPosition;
    private ArrayList<Lesson> lessonList = new ArrayList<>();

    Binder mBinder = new IPlayer.Stub() {
        @Override
        public void action(int action, String datum) throws RemoteException {
            switch (action) {

                ///*******************about play***********************************************/
                case MUSIC_ACTION_PAUSE:
                    pauseSong();
                    break;
                case MUSIC_ACTION_STOP:
                    stopSong();
                    break;
                case MUSIC_ACTION_SEEK_PLAY:
                    seekPlaySong(Integer.parseInt(datum));
                    break;
                case MUSIC_ACTION_PLAY:
                    onActionPlay(datum);
                    break;
                case MUSIC_ACTION_CONTINUE_PLAY:
                    continuePlaySong();
                    break;
                case MUSIC_ACTION_PREVIOUS:
                    previousSong();
                    break;
                case MUSIC_ACTION_NEXT:
                    modePlay();
                    break;
                case MUSIC_ACTION_MUTE:
                    mMediaPlayer.setVolume(0f, 0f);
                    break;
                default:
                    ///*******************about play mode***********************************************/
                    matchPlayMode(action);
                    break;
            }
        }

        private void matchPlayMode(int action) {
            switch (action) {
                case MUSIC_PLAY_MODE_RANDOM:
                    MUSIC_CURRENT_MODE = MUSIC_PLAY_MODE_RANDOM;
                    break;
                case MUSIC_PLAY_MODE_REPEAT:
                    MUSIC_CURRENT_MODE = MUSIC_PLAY_MODE_REPEAT;
                    break;
                case MUSIC_PLAY_MODE_SINGLE:
                    MUSIC_CURRENT_MODE = MUSIC_PLAY_MODE_SINGLE;
                    break;
            }
        }

        @Override
        public void registerListener(IPlayerListener listener) throws RemoteException {
            if (listener != null) {
                mListenerList.register(listener);
            }

        }

        @Override
        public void unregisterListener(IPlayerListener listener) throws RemoteException {
            if (listener != null) {
                mListenerList.unregister(listener);
            }
        }

        @Override
        public Message getCurrentSongInfo() throws RemoteException {
            Message msg = Message.obtain();
            if (lessonList != null && lessonList.size() > 0) {
                msg.obj = lessonList.get(currentPosition);
            }
            return msg;
        }
    };

    public PlayService() {
    }

    public void playSong(String path) {
        try {
            stopSong();
            mMediaPlayer.reset();//idle
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_PLAY;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void pauseSong() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_PAUSE;
            onPausePlay();
        }
    }

    private void previousSong() {
        if (currentPosition > 0) {
            currentPosition--;
        } else {
            currentPosition = lessonList.size() - 1;
        }
        play();
    }

    private void onPausePlay() {
    }

    private void onActionPlay(String datum) {
//        if (TextUtils.isEmpty(datum)) {
//            play();
//            return;
//        }
//        MusicServiceBean musicServiceBean = GsonHelper.getGson().fromJson(datum, MusicServiceBean.class);
//        currentPosition = musicServiceBean.position;
//        mSong_list.clear();
//        mSong_list.addAll(musicServiceBean.song_list);
//        play();
    }

    public void continuePlaySong() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_PLAY;
            onStartPlay();
        }
    }

    private void onStartPlay() {
        Message msg = Message.obtain();
        msg.what = MUSIC_ACTION_PLAY;
        msg.arg1 = 1;
        sendBroadcast(MUSIC_ACTION_PLAY, msg);
    }

    public void stopSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            MUSIC_CURRENT_ACTION = MUSIC_ACTION_STOP;
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        }

    }

    public void seekPlaySong(int progress) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(progress);
        }
    }

    private void play() {
        Lesson lesson = lessonList.get(currentPosition);
        onStartPlay();
        playSong(lesson.getLesson_url());

    }

    private void modePlay() {
        switch (MUSIC_CURRENT_MODE) {
            case MUSIC_PLAY_MODE_RANDOM:
                if (lessonList != null && lessonList.size() > 0) {
                    Random random = new Random();
                    currentPosition = random.nextInt(lessonList.size());
                    play();
                }
                break;
            case MUSIC_PLAY_MODE_REPEAT:
                nextSong();
                break;
            case MUSIC_PLAY_MODE_SINGLE:
                play();
                break;
        }
    }

    private void nextSong() {
        if (++currentPosition >= lessonList.size()) {
            currentPosition = 0;
        }
        play();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).
                requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void onPlaying() {
        int currentPosition = mMediaPlayer.getCurrentPosition();
        int totalDuration = mMediaPlayer.getDuration();
        Message msg = Message.obtain();
        msg.what = MUSIC_ACTION_SEEK_PLAY;
        msg.arg1 = currentPosition;
        msg.arg2 = totalDuration;
        sendBroadcast(PLAYER_LISTENER_ACTION_NORMAL, msg);
    }

    @Override
    public void onAudioFocusChange(int i) {
        switch (i) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stopSong playback and release media player
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stopSong
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        modePlay();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onPlaying();
            }
        }, 0, 1000);
    }

    private void sendBroadcast(int action, Message msg) {
        try {
            final int N = mListenerList.beginBroadcast();
            for (int i = 0; i < N; i++) {
                IPlayerListener broadcastItem = mListenerList.getBroadcastItem(i);
                if (broadcastItem != null) {
                    broadcastItem.action(action, msg);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtils.e("RemoteException" + e.getMessage());
        } finally {
            try {
                mListenerList.finishBroadcast();
            } catch (IllegalArgumentException illegalArgumentException) {
                LogUtils.e("Error while diffusing message to listener  finishBroadcast ", illegalArgumentException);
            }
        }

    }
}
