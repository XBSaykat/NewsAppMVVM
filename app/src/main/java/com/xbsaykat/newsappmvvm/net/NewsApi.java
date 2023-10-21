package com.xbsaykat.newsappmvvm.net;

import com.xbsaykat.newsappmvvm.BuildConfig;
import com.xbsaykat.newsappmvvm.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET(BuildConfig.api_url_1)
    Call<NewsResponse> getNewsList(
            @Query("country") String newsSource,
            @Query("category") String category,
            @Query("apiKey") String apiKey);
    @GET(BuildConfig.api_url_2)
    Call<NewsResponse> getNewsSearch(
            @Query("q") String keyword,
            @Query("apiKey") String apiKey
    );
}
