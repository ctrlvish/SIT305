package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;
    private Context context;
    private OnNewsItemClickListener listener;

    public interface OnNewsItemClickListener {
        void onNewsItemClick(NewsItem newsItem);
    }

    public NewsAdapter(Context context, List<NewsItem> newsList, OnNewsItemClickListener listener) {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);

        holder.newsTitleTextView.setText(newsItem.getTitle());
        holder.newsSummaryTextView.setText(newsItem.getSummary());
        holder.newsImageView.setImageResource(newsItem.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNewsItemClick(newsItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        TextView newsTitleTextView;
        TextView newsSummaryTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
            newsTitleTextView = itemView.findViewById(R.id.newsTitleTextView);
            newsSummaryTextView = itemView.findViewById(R.id.newsSummaryTextView);
        }
    }
}