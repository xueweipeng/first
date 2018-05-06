package com.ecfo.modules.lesson.mvp.view;

import com.ecfo.modules.lesson.mvp.module.beans.FreeLesson;

import java.util.List;

public interface LessonView extends MvpView {
    void showLoading(boolean loading);

    //显示错误
    void showError(String errorMessage);

    //显示堆车地点
    void showPileAddress(String address);

    //显示某堆车点车辆列表
    void showLessons(final List<FreeLesson> lessons);

    // 更新是否有已装车未提交的数据，用于back键处理
    void updateBackHandleState(boolean state);
}