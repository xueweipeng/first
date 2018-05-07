package com.ecfo.modules.lesson.mvp.module;

import com.ecfo.modules.lesson.mvp.module.beans.FreeLesson;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiService {

//    //1.检查车牌号的状态，以及是否是任务车
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("xht/api/label/check")
//    Observable<BikeStateBean> getBikeState(@Body UCBean entity);
//
//    //2、分拣车辆， 判断好坏
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("xht/api/label/sort")
//    Observable<SortResponse> sortBike(@Body UWBean entity);
//
//
//    //3.统计信息，返回这个人当天分拣指标信息
//    @GET("xht/api/label/stat")
//    Observable<SortTaskBean> getSortTask(@QueryMap Map<String, String> map);
//
//
//    //4.返回这个人当天分拣详情
//    @GET("xht/api/label/sort-list")
//    Observable<SortResultBean> getSortResult(@QueryMap Map<String, String> map);


    @GET("ecfo/free_lessons")
    Observable<FreeLesson> getFreeLessons(@QueryMap Map<String, String> map);
}
