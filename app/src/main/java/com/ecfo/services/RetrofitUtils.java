package com.ecfo.services;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtils {
    private static final int DEFAULT_TIMEOUT = 5;
    private static String baseUrl = "https://ecfo.com/";
    private Retrofit retrofit;
    private static class SingletonHolder {
        private static RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    public static RetrofitUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

}
