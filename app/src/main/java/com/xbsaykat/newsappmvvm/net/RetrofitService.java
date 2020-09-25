package com.xbsaykat.newsappmvvm.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit =new Retrofit.Builder().
    baseUrl("https://newsapi.org/v2/").
    addConverterFactory(GsonConverterFactory.create()).
    build();


    public static <S> S creatServices(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
