package com.xbsaykat.newsappmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xbsaykat.newsappmvvm.model.NewsResponse;
import com.xbsaykat.newsappmvvm.net.NewsRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    private MutableLiveData<NewsResponse> mutableLiveData;
    private MutableLiveData<NewsResponse> mutableLiveDataBySearch;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if(mutableLiveData != null){
            return;
        }
        newsRepository = new NewsRepository();
        mutableLiveData = newsRepository.getNews("US", "YOUR API KEY");
    }
    public void Search(String keyword) {
        if(mutableLiveDataBySearch != null){
            return;
        }
        newsRepository = new NewsRepository();
        mutableLiveDataBySearch = newsRepository.getNewsbySearch(keyword,"YOUR API KEY");
    }

    public LiveData<NewsResponse> getNewsRepository(){
        return mutableLiveData;
    }
    public LiveData<NewsResponse> getNewsRepositoryBySearch(){
        return mutableLiveDataBySearch;
    }
}
