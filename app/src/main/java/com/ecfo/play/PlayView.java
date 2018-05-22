package com.ecfo.play;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecfo.IPlayer;
import com.ecfo.IPlayerListener;
import com.ecfo.R;
import com.ecfo.modules.lesson.mvp.module.beans.Lesson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ecfo.play.PlayService.MUSIC_ACTION_PAUSE;
import static com.ecfo.play.PlayService.MUSIC_ACTION_PLAY;
import static com.ecfo.play.PlayService.MUSIC_ACTION_SEEK_PLAY;

public class PlayView extends LinearLayout {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_play_avatar)
    ImageView ivAvatar;
    @BindView(R.id.iv_show_activity)
    ImageView ivShow;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time_and_lesson)
    TextView tvTimeLesson;

    private View mRootView;

    private IPlayer mMusicPlayerService;

    public PlayView(Context context) {
        this(context, null);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mRootView = View.inflate(context, R.layout.layout_play, null);
        ButterKnife.bind(this, mRootView);
        addView(mRootView);
    }

    private final int UPDATE_UI = 23;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MUSIC_ACTION_PLAY:
                case MUSIC_ACTION_PAUSE:
                case UPDATE_UI:
                    onPlay();
                    break;
                case MUSIC_ACTION_SEEK_PLAY:
                    onSeekPlay(msg);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    private void onSeekPlay(Message msg) {
    }

    private void onPlay() {
        try {
            Lesson lesson =
                    (Lesson) mMusicPlayerService.getCurrentSongInfo().obj;

            if (lesson == null) {
                return;
            }
            ivPlay.setImageResource(R.drawable.play_play);
            Glide.with(getContext()).load(lesson.small_pic_url)
                    .into(ivAvatar);
            tvTitle.setText(lesson.title);
            tvTimeLesson.setText(lesson.name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    IPlayerListener mPlayerListener = new IPlayerListener.Stub() {
        @Override
        public void action(int action, Message msg) throws RemoteException {
            mHandler.sendMessage(msg);
        }
    };

    public void registerListener(IPlayer musicPlayerService) {
        try {
            mMusicPlayerService = musicPlayerService;
            mMusicPlayerService.registerListener(mPlayerListener);
            mHandler.sendEmptyMessage(UPDATE_UI);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterListener() {
        try {
            if (mMusicPlayerService != null) {
                mMusicPlayerService.unregisterListener(mPlayerListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
