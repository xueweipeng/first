package com.ecfo.modules.lesson.mvp.module.beans;

import java.util.List;

public class LoadResponse {

    /**
     * code : 0
     * data : {"succ":["42302406","17179410"]}
     * message : success
     */

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
        private List<String> succ;

        public List<String> getSucc() {
            return succ;
        }

        public void setSucc(List<String> succ) {
            this.succ = succ;
        }
    }
}
