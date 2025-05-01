package com.example.itubeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;

public class HomeFragment extends Fragment {
    private YouTubePlayerView youTubePlayerView;
    private EditText urlEditText;
    private Button playButton, addToPlaylistButton, myPlaylistButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialiseViews(view);
        setupYouTubePlayer();
        setupButtonListeners();
        return view;
    }

    private void initialiseViews(View view) {
        youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
        urlEditText = view.findViewById(R.id.urlEditText);
        playButton = view.findViewById(R.id.playButton);
        addToPlaylistButton = view.findViewById(R.id.addToPlaylistButton);
        myPlaylistButton = view.findViewById(R.id.myPlaylistButton);
    }

    private void setupYouTubePlayer() {
        getLifecycle().addObserver(youTubePlayerView);
    }

    private void setupButtonListeners() {
        playButton.setOnClickListener(v -> playYoutubeVideo());

        addToPlaylistButton.setOnClickListener(v -> {
            if (!isUserLoggedIn()) {
                promptUserToSignUp();
                return;
            }
            addCurrentUrlToPlaylist();
        });

        myPlaylistButton.setOnClickListener(v -> {
            if (!isUserLoggedIn()) {
                promptUserToSignUp();
                return;
            }
            navigateToPlaylist();
        });
    }

    private void playYoutubeVideo() {
        String url = getVideoUrl();
        if (url.isEmpty()) {
            showToast("Please enter a YouTube URL");
            return;
        }

        String videoId = extractVideoId(url);
        if (videoId == null) {
            showToast("Invalid YouTube URL");
            return;
        }

        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer ->
                youTubePlayer.loadVideo(videoId, 0));
    }

    private void addCurrentUrlToPlaylist() {
        String url = getVideoUrl();
        if (url.isEmpty()) {
            showToast("Please enter a YouTube URL");
            return;
        }

        PlaylistDao playlistDao = new PlaylistDao(requireContext());
        long result = playlistDao.addToPlaylist(getCurrentUserId(), url);

        String message = result != -1 ? "Added to playlist" : "Failed to add to playlist";
        showToast(message);
    }

    private void navigateToPlaylist() {
        PlaylistFragment playlistFragment = new PlaylistFragment();
        List<PlaylistItem> playlist = new PlaylistDao(requireContext())
                .getUserPlaylist(getCurrentUserId());
        playlistFragment.updatePlaylist(playlist);
        ((MainActivity) requireActivity()).replaceFragment(playlistFragment);
    }

    //helper methods
    private String getVideoUrl() {
        return urlEditText.getText().toString().trim();
    }

    private String extractVideoId(String url) {
        if (url.contains("youtube.com/watch?v=")) {
            return url.substring(url.indexOf("v=") + 2);
        } else if (url.contains("youtu.be/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return null;
    }

    private boolean isUserLoggedIn() {
        return getCurrentUserId() != -1;
    }

    private int getCurrentUserId() {
        return ((MainActivity) requireActivity()).getCurrentUserId();
    }

    private void promptUserToSignUp() {
        showToast("Please sign up first");
        ((MainActivity) requireActivity()).replaceFragment(new SignupFragment());
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        youTubePlayerView.release();
    }
}