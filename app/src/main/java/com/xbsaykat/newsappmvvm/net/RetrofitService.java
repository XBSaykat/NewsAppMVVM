package com.xbsaykat.newsappmvvm.net;

import com.xbsaykat.newsappmvvm.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit =new Retrofit.Builder().
    baseUrl(BuildConfig.base_url).
    addConverterFactory(GsonConverterFactory.create()).
    build();


    public static <S> S creatServices(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
