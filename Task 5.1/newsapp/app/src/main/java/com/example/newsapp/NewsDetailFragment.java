package com.example.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsDetailFragment extends Fragment implements RelatedNewsAdapter.OnRelatedNewsItemClickListener {

    private static final String ARG_NEWS_ID = "news_id";
    private static final String ARG_NEWS_TITLE = "news_title";
    private static final String ARG_NEWS_IMAGE_RES = "news_image_res";
    private static final String ARG_NEWS_DESCRIPTION = "news_description";

    private int newsId;
    private String newsTitle;
    private int newsImageRes;
    private String newsDescription;

    public NewsDetailFragment() {

    }

    public static NewsDetailFragment newInstance(NewsItem newsItem) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NEWS_ID, newsItem.getId());
        args.putString(ARG_NEWS_TITLE, newsItem.getTitle());
        args.putInt(ARG_NEWS_IMAGE_RES, newsItem.getImageResourceId());
        args.putString(ARG_NEWS_DESCRIPTION, newsItem.getFullDescription());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsId = getArguments().getInt(ARG_NEWS_ID);
            newsTitle = getArguments().getString(ARG_NEWS_TITLE);
            newsImageRes = getArguments().getInt(ARG_NEWS_IMAGE_RES);
            newsDescription = getArguments().getString(ARG_NEWS_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTextView = view.findViewById(R.id.detailTitleTextView);
        ImageView imageView = view.findViewById(R.id.detailImageView);
        TextView descriptionTextView = view.findViewById(R.id.detailDescriptionTextView);
        RecyclerView relatedNewsRecyclerView = view.findViewById(R.id.relatedNewsRecyclerView);

        titleTextView.setText(newsTitle);
        descriptionTextView.setText(newsDescription);
        imageView.setImageResource(newsImageRes);

        List<NewsItem> relatedNewsList = NewsDataProvider.getRelatedNews(newsId);
        RelatedNewsAdapter adapter = new RelatedNewsAdapter(requireContext(), relatedNewsList, this);
        relatedNewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        relatedNewsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRelatedNewsItemClick(NewsItem newsItem) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, NewsDetailFragment.newInstance(newsItem))
                    .addToBackStack(null)
                    .commit();
        }
    }
}