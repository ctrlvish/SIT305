package com.example.itubeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<PlaylistItem> playlistItems;

    public PlaylistAdapter(List<PlaylistItem> playlistItems) {
        this.playlistItems = playlistItems;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        PlaylistItem item = playlistItems.get(position);
        holder.videoUrlTextView.setText(item.getVideoUrl());
    }

    @Override
    public int getItemCount() {
        return playlistItems.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView videoUrlTextView;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            videoUrlTextView = itemView.findViewById(R.id.videoUrlTextView);
        }
    }
}