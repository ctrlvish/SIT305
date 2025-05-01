package com.example.itubeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        signupButton = view.findViewById(R.id.signupButton);

        loginButton.setOnClickListener(v -> attemptLogin());
        signupButton.setOnClickListener(v -> navigateToSignUp());

        return view;
    }

    private void attemptLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDao userDao = new UserDao(requireContext());
        User user = userDao.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            ((MainActivity) requireActivity()).setCurrentUserId(user.getId());
            ((MainActivity) requireActivity()).replaceFragment(new HomeFragment());
        } else {
            Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToSignUp() {
        ((MainActivity) requireActivity()).replaceFragment(new SignupFragment());
    }
}