package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.usernameInput);
        goButton = findViewById(R.id.goButton);

        goButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            if (username.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_username_error), Toast.LENGTH_SHORT).show();
                return;
            }

            //start ChatActivity with username
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}