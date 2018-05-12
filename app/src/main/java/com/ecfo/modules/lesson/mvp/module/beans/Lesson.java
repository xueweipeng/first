package com.ecfo.modules.lesson.mvp.module.beans;

/**
 * Created by ofo on 2018/5/12.
 */

public class Lesson {
    public String id;
    public String title;
    public String lesson_url;
    public String small_pic_url;
    public String big_pic_url;

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

}
