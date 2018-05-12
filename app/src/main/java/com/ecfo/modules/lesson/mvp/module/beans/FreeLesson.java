package com.ecfo.modules.lesson.mvp.module.beans;

import java.util.List;

/**
    see lessons.json
 */

public class FreeLesson {
    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<Lesson> lessons;

        public List<Lesson> getLessons() {
            return lessons;
        }

        public void setLessons(List<Lesson> lessons) {
            this.lessons = lessons;
        }
    }
}
