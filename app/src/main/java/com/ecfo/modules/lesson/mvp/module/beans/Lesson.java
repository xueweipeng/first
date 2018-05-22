package com.ecfo.modules.lesson.mvp.module.beans;

import android.os.Parcel;
import android.os.Parcelable;


public class Lesson implements Parcelable {
    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };
    public String id;
    public String title;
    public String name;
    public String lesson_url;
    public String small_pic_url;
    public String big_pic_url;

    public Lesson(Parcel in) {
        id = in.readString();
        title = in.readString();
        name = in.readString();
        lesson_url = in.readString();
        small_pic_url = in.readString();
        big_pic_url = in.readString();
    }

    public String getLesson_url() {
        return lesson_url;
    }

    public void setLesson_url(String lesson_url) {
        this.lesson_url = lesson_url;
    }

    public String getSmall_pic_url() {
        return small_pic_url;
    }

    public void setSmall_pic_url(String small_pic_url) {
        this.small_pic_url = small_pic_url;
    }

    public String getBig_pic_url() {
        return big_pic_url;
    }

    public void setBig_pic_url(String big_pic_url) {
        this.big_pic_url = big_pic_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(name);
        parcel.writeString(lesson_url);
        parcel.writeString(small_pic_url);
        parcel.writeString(big_pic_url);
    }
}
