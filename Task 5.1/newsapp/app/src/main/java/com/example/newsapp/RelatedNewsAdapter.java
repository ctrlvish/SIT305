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

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.RelatedNewsViewHolder> {

    private List<NewsItem> relatedNewsList;
    private Context context;
    private OnRelatedNewsItemClickListener listener;

    public interface OnRelatedNewsItemClickListener {
        void onRelatedNewsItemClick(NewsItem newsItem);
    }

    public RelatedNewsAdapter(Context context, List<NewsItem> relatedNewsList, OnRelatedNewsItemClickListener listener) {
        this.context = context;
        this.relatedNewsList = relatedNewsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_news_item, parent, false);
        return new RelatedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedNewsViewHolder holder, int position) {
        NewsItem newsItem = relatedNewsList.get(position);

        holder.relatedNewsTitleTextView.setText(newsItem.getTitle());
        holder.relatedNewsSummaryTextView.setText(newsItem.getSummary());
        holder.relatedNewsImageView.setImageResource(newsItem.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRelatedNewsItemClick(newsItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return relatedNewsList.size();
    }

    public static class RelatedNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView relatedNewsImageView;
        TextView relatedNewsTitleTextView;
        TextView relatedNewsSummaryTextView;

        public RelatedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            relatedNewsImageView = itemView.findViewById(R.id.relatedNewsImageView);
            relatedNewsTitleTextView = itemView.findViewById(R.id.relatedNewsTitleTextView);
            relatedNewsSummaryTextView = itemView.findViewById(R.id.relatedNewsSummaryTextView);
        }
    }
}