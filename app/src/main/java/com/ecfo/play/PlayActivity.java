package com.ecfo.play;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.ecfo.IPlayer;
import com.ecfo.IPlayerListener;
import com.ecfo.R;
import com.ecfo.ecfoApplication;
import com.ecfo.modules.lesson.mvp.module.beans.Lesson;
import com.ecfo.utils.Util;
import com.xw.repo.BubbleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ecfo.play.PlayService.MUSIC_ACTION_PAUSE;
import static com.ecfo.play.PlayService.MUSIC_ACTION_PLAY;
import static com.ecfo.play.PlayService.MUSIC_ACTION_SEEK_PLAY;
import static com.ecfo.play.PlayService.PLAYER_LISTENER_ACTION_NORMAL;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "PlayActivity";
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvPlayTitle)
    TextView tvPlayTitle;

    @BindView(R.id.seekBar)
    BubbleSeekBar seekBar;
    @BindView(R.id.ivPlayPrevious)
    ImageView ivPlayPrevious;
    @BindView(R.id.ivPlayNext)
    ImageView ivPlayNext;
    @BindView(R.id.ivPlayPlay)
    ImageView ivPlayPlay;

    @BindView(R.id.tvLessonList)
    TextView tvLessonList;
    @BindView(R.id.tvLessonText)
    TextView tvLessonText;
    @BindView(R.id.tvDownload)
    TextView tvDownload;
    @BindView(R.id.tvLessonShare)
    TextView tvLessonShare;

    private IPlayer mPlayerService = ecfoApplication.app.getMusicPlayerService();

    IPlayerListener mPlayerListener = new IPlayerListener.Stub() {
        @Override
        public void action(int action, Message msg) throws RemoteException {
            switch (action) {
                case MUSIC_ACTION_PLAY:
                case MUSIC_ACTION_PAUSE:
                    changePlayState();
                    break;
                case PLAYER_LISTENER_ACTION_NORMAL:
                    onSeekPlay(msg);
                    break;
            }
        }
    };

    private void onSeekPlay(Message msg) throws RemoteException {
        final Lesson lesson = (Lesson) mPlayerService.getCurrentSongInfo().obj;
        if (lesson == null) {
            return;
        }
        int currentPosition = msg.arg1;
        int totalDuration = msg.arg2;
        float pos = seekBar.getMax() * currentPosition / totalDuration;
        seekBar.setProgress((int) pos);
        tvLessonText.setText(lesson.getTitle());
    }

    private void changePlayState() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        registerListener();
        ivClose.setOnClickListener(view -> {
            Util.finishActivity(this);
        });
        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                LogUtils.i(TAG, "getProgressOnActionUp");
                try {
                    ecfoApplication.app.getMusicPlayerService().action(MUSIC_ACTION_SEEK_PLAY, Integer.toString(progress));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                LogUtils.i(TAG, "getProgressOnFinally");
            }
        });
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterListener();
    }
}
