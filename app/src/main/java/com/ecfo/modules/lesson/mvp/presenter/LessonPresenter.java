package com.ecfo.modules.lesson.mvp.presenter;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.ecfo.modules.lesson.mvp.module.ApiServiceLesson;
import com.ecfo.modules.lesson.mvp.module.beans.FreeLesson;
import com.ecfo.modules.lesson.mvp.view.LessonView;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ofo on 2018/5/7.
 */

public class LessonPresenter extends MvpBasePresenter<LessonView> {
    private static final String TAG = "LessonPresenter";
    private CompositeSubscription subscriptions;
    private ApiServiceLesson lessonService;

    public LessonPresenter() {
        subscriptions = new CompositeSubscription();
        lessonService = new ApiServiceLesson();
    }

    public void getFreeLesson() {
        if(!isViewAttached()){
            return;
        }
        getView().showLoading(true);

        Map<String,String> requestForState = new HashMap<>();
        requestForState.put("userId", "100");

        subscriptions.add(
                lessonService.getFreeLessons(new Subscriber<FreeLesson>() {
                    @Override
                    public void onCompleted() {
                        Log.i("XXX","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.file("getFreeLessons error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(FreeLesson lesson) {
                        LogUtils.v(TAG);
                        getView().showLessons(null);
                    }
                },requestForState)
        );
        getView().showLoading(false);
    }
}
