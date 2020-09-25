package com.xbsaykat.newsappmvvm.net;

import com.xbsaykat.newsappmvvm.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<NewsResponse> getNewsList(
            @Query("country") String newsSource,
            @Query("apiKey") String apiKey);
    @GET("everything")
    Call<NewsResponse> getNewsSearch(
            @Query("q") String keyword,
            @Query("apiKey") String apiKey
    );
    @GET("sources")
    Call<NewsResponse> getNewsSources(
            @Query("apiKey") String apiKey
    );
}
