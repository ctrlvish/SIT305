package com.example.newsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnNewsItemClickListener{

    private RecyclerView topStoriesRecyclerView;
    private RecyclerView newsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialise top stories recycler view
        topStoriesRecyclerView = findViewById(R.id.topStoriesRecyclerView);
        topStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //get top stories data
        List<NewsItem> topStoriesList = NewsDataProvider.getTopStories();

        //create and set top stories adapter
        NewsAdapter topStoriesAdapter = new NewsAdapter(this, topStoriesList, this);
        topStoriesRecyclerView.setAdapter(topStoriesAdapter);

        //initialise news recycler view
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //get news data
        List<NewsItem> newsList = NewsDataProvider.getNews();

        //create and set news adapter
        NewsAdapter newsAdapter = new NewsAdapter(this, newsList, this);
        newsRecyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onNewsItemClick(NewsItem newsItem) {
        //create and display the news detail fragment
        NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(newsItem);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}