package com.ecfo.modules.lesson.mvp.presenter;


import com.ecfo.modules.lesson.mvp.view.MvpView;

public interface MvpPresenter <V extends MvpView> {
    /**
     * Set or attach the view to this presenter
     */
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    void detachView(boolean retainInstance);
}
