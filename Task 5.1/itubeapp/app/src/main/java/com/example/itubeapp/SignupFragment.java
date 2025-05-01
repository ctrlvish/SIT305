package com.example.itubeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class SignupFragment extends Fragment {
    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        fullNameEditText = view.findViewById(R.id.fullNameEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        createAccountButton = view.findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (validateInput(fullName, username, password, confirmPassword)) {
                //create new user account
                User newUser = new User(fullName, username, password);
                UserDao userDao = ((MainActivity) requireActivity()).getUserDao();

                //check if username already exists
                if (userDao.checkUsernameExists(username)) {
                    Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = userDao.addUser(newUser);

                if (result != -1) {
                    //get the newly created user ID and set as current user
                    User createdUser = userDao.getUserByUsername(username);
                    ((MainActivity) requireActivity()).setCurrentUserId(createdUser.getId());

                    Toast.makeText(requireContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                    //navigate back to home
                    ((MainActivity) requireActivity()).replaceFragment(new LoginFragment());
                } else {
                    Toast.makeText(requireContext(), "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean validateInput(String fullName, String username, String password, String confirmPassword) {
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}