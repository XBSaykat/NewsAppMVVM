package com.xbsaykat.newsappmvvm.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xbsaykat.newsappmvvm.R;
import com.xbsaykat.newsappmvvm.adapter.Adapter;
import com.xbsaykat.newsappmvvm.model.NewsArticle;
import com.xbsaykat.newsappmvvm.model.NewsResponse;
import com.xbsaykat.newsappmvvm.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<NewsArticle> articleArrayList = new ArrayList<>();
    private Adapter newsAdapter;
    private RecyclerView rvHeadline;
    private MainActivityViewModel newsViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponent();
        initListener();
    }

    private void initComponent() {
        rvHeadline = findViewById(R.id.recyclerView);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        newsViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        onLoadingSwipeRefresh();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new Adapter(articleArrayList, MainActivity.this);
            rvHeadline.setLayoutManager(new LinearLayoutManager(this));
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    private void initListener() {
        newsAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            NewsArticle article = articleArrayList.get(position);
            intent.putExtra("url", article.getUrl());
            intent.putExtra("title", article.getTitle());
            intent.putExtra("img", article.getUrlToImage());
            intent.putExtra("date", article.getPublishedAt());
            intent.putExtra("source", article.getSource().getName());
            intent.putExtra("author", article.getAuthor());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem search = menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    newsViewModel.Search(query);
                    articleArrayList.clear();
                    swipeRefreshLayout.setRefreshing(true);
                    newsViewModel.getNewsSearchObserveData()
                            .observe(MainActivity.this, newsResponse -> {
                                if (newsResponse != null) {
                                    List<NewsArticle> newsArticles = newsResponse.getArticles();
                                    articleArrayList.addAll(newsArticles);
                                    newsAdapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                            });
                } else if (query.length() == 0) {
                    LoadDataFromNews();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    LoadDataFromNews();
                }
                return false;
            }
        });
        search.getIcon().setVisible(false, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        LoadDataFromNews();
    }

    private void LoadDataFromNews() {
        swipeRefreshLayout.setRefreshing(true);
        newsViewModel.getNews("US", "business");
        newsViewModel.getNewsObserveData().observe(this, newsResponse -> {
                    articleArrayList.clear();
                    if (newsResponse != null) {
                        List<NewsArticle> newsArticles = newsResponse.getArticles();
                        articleArrayList.addAll(newsArticles);
                        newsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                });
    }

    private void onLoadingSwipeRefresh() {
        swipeRefreshLayout.post(() -> LoadDataFromNews());
    }
}