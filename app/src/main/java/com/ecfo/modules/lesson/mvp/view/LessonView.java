package com.ecfo.modules.lesson.mvp.view;

import com.ecfo.modules.lesson.mvp.module.beans.FreeLesson;

import java.util.List;

public interface LessonView extends MvpView {
    void showLoading(boolean loading);

    //显示错误
    void showError(String errorMessage);

    void showLessons(final List<FreeLesson> lessons);
}