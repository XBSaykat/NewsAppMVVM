package com.xbsaykat.newsappmvvm.net;

import androidx.lifecycle.MutableLiveData;

import com.xbsaykat.newsappmvvm.model.NewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private static NewsRepository newsRepository;

    public static NewsRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    private NewsApi newsApi;

    public NewsRepository(){
        newsApi = RetrofitService.creatServices(NewsApi.class);
    }

    public MutableLiveData<NewsResponse> getNews(String source, String key){
        MutableLiveData<NewsResponse> newsDate= new MutableLiveData<>();
        newsApi.getNewsList(source,key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                newsDate.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsDate.setValue(null);
            }
        });
        return  newsDate;
    }

    public MutableLiveData<NewsResponse> getNewsbySearch(String keyword,String apiKey){
        MutableLiveData<NewsResponse> newsData= new MutableLiveData<>();
        newsApi.getNewsSearch(keyword,apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return  newsData;
    }
}
