package com.ecfo.services;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseApiService {
    protected Retrofit retrofit;
    protected  <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    protected BaseApiService(){
        retrofit = RetrofitUtils.getInstance().getRetrofit();
    }
}
