package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    private TextView feedbackText;
    private TextView scoreText;
    private Button restartButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        feedbackText = findViewById(R.id.feedbackText);
        scoreText = findViewById(R.id.scoreText);
        restartButton = findViewById(R.id.restartButton);
        finishButton = findViewById(R.id.finishButton);
        String username = getIntent().getStringExtra("USERNAME");

        feedbackText.setText("Quiz completed by: " + username);
        int score = getIntent().getIntExtra("SCORE", 0);
        scoreText.setText("Your Score: " + score + "/4");

        restartButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        });

        finishButton.setOnClickListener(v -> {
            finishAffinity();
        });

    }
}