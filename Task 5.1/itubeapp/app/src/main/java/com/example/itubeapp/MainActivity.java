package com.example.itubeapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    private UserDao userDao;
    private int currentUserId = -1; //-1 means no users logged in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDao = new UserDao(this);

        //start with the LoginFragment
        replaceFragment(new LoginFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        //add to back stack if it's not the initial fragment
        if (!(fragment instanceof HomeFragment)) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}