package com.ecfo.modules.lesson.mvp.module.beans;

import java.util.List;


public class LoadRequest {


    /**
     * userId : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjE3NzQ2NTUxMjg5IiwiZXhwaXJlX3RpbWUiOiIyMDE4LTAzLTMwIDAwOjAwOjAwIiwicm9sZV9pZCI6Miwicm9sZV9uYW1lIjoiXHU2MjdlXHU4ZjY2XHU1NDU4In0.HKAe1zdTv2bjAlCl7PjnuCBSO1o9HpDhBBnn8eE5C0Y
     * carList : ["42302406","17179410"]
     * pileId : 120.1223,31.0,17746551289
     */

    private String userId;
    private String pileId;
    private List<String> carList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPileId() {
        return pileId;
    }

    public void setPileId(String pileId) {
        this.pileId = pileId;
    }

    public List<String> getCarList() {
        return carList;
    }

    public void setCarList(List<String> carList) {
        this.carList = carList;
    }
}
