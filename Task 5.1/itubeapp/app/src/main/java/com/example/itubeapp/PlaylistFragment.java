package com.example.itubeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {
    private RecyclerView playlistRecyclerView;
    private PlaylistAdapter playlistAdapter;
    private List<PlaylistItem> playlistItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        playlistRecyclerView = view.findViewById(R.id.playlistRecyclerView);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initialise adapter with empty list
        playlistAdapter = new PlaylistAdapter(playlistItems);
        playlistRecyclerView.setAdapter(playlistAdapter);

        return view;
    }

    public void updatePlaylist(List<PlaylistItem> items) {
        playlistItems.clear();
        playlistItems.addAll(items);
        if (playlistAdapter != null) {
            playlistAdapter.notifyDataSetChanged();
        }
    }
}