package com.ecfo.modules.lesson.mvp.module;

import com.ecfo.services.BaseApiService;


public class ApiServiceLesson extends BaseApiService {
    private static ApiService apiService;

    public ApiServiceLesson() {
        super();
        if (apiService == null) {
            apiService = retrofit.create(ApiService.class);
        }
    }

//    //查状态
//    public Subscription getBikeState(Subscriber<BikeStateBean> subscriber, UCBean entity) {
//        return apiService.getBikeState(entity)
//                .compose(applySchedulers())
//                .subscribe(subscriber);
//    }
//
//
//    //分拣
//    public Subscription sortBike(Subscriber<SortResponse> subscriber, UWBean entity) {
//        return apiService.sortBike(entity)
//                .compose(applySchedulers())
//                .subscribe(subscriber);
//    }
//
//    //师傅指标,参数:
//    //字段         值                    是否必须
//    //    date	      2018-03-29	    否
//    //    userId	  18510543766	    是
//    //    whId	      4（读取的仓库id）	是
//    public Subscription getSortTask(Subscriber<SortTaskBean> subscriber, Map<String, String> para) {
//        return apiService.getSortTask(para)
//                .compose(applySchedulers())
//                .subscribe(subscriber);
//    }
//
//    //查当天已经分拣了多少 参数：
//    //    字段        值               是否必须
//    //    userId	18510543766	      是
//    //    date	    2018-03-29	      否
//    public Subscription getSortResult(Subscriber<SortResultBean> subscriber, Map<String, String> para) {
//        return apiService.getSortResult(para)
//                .compose(applySchedulers())
//                .subscribe(subscriber);
//    }
}
