package com.ecfo.play;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecfo.modules.lesson.mvp.module.beans.Lesson;

import java.util.ArrayList;


public class PlayServiceBean implements Parcelable {
    public static final Creator<PlayServiceBean> CREATOR = new Creator<PlayServiceBean>() {
        @Override
        public PlayServiceBean createFromParcel(Parcel in) {
            return new PlayServiceBean(in);
        }

        @Override
        public PlayServiceBean[] newArray(int size) {
            return new PlayServiceBean[size];
        }
    };
    public ArrayList<Lesson> lessonList;
    public int position;
    public int seekProgress;
    public int totalDuration;
    public String backgroundUrl;

    public PlayServiceBean(Parcel in) {
        lessonList = in.createTypedArrayList(Lesson.CREATOR);
        position = in.readInt();
        seekProgress = in.readInt();
        totalDuration = in.readInt();
        backgroundUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(lessonList);
        dest.writeInt(position);
        dest.writeInt(seekProgress);
        dest.writeInt(totalDuration);
        dest.writeString(backgroundUrl);
    }
}
