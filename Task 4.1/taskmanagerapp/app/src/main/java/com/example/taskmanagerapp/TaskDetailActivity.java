package com.example.taskmanagerapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView titleTextView = findViewById(R.id.detailTitleTextView);
        TextView descriptionTextView = findViewById(R.id.detailDescriptionTextView);
        TextView dueDateTextView = findViewById(R.id.detailDueDateTextView);
        Button backButton = findViewById(R.id.backButton);

        //gets data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titleTextView.setText(extras.getString("TASK_TITLE", ""));
            descriptionTextView.setText(extras.getString("TASK_DESCRIPTION", ""));
            dueDateTextView.setText(extras.getString("TASK_DUE_DATE", ""));
        }

        backButton.setOnClickListener(v -> finish());
    }
}