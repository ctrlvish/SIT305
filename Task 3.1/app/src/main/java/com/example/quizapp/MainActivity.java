package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText nameEditText;
    private TextView questionText;
    private Button option1, option2, option3, submitButton;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int selectedOption = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        nameEditText = findViewById(R.id.nameEditText);
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        submitButton = findViewById(R.id.submitButton);

        String savedUsername = getIntent().getStringExtra("USERNAME");
        if (savedUsername != null && !savedUsername.isEmpty()) {
            nameEditText.setText(savedUsername);
        }

        questions = new ArrayList<>();
        questions.add(new Question("What is 2+2?", "3", "4", "5", 2));
        questions.add(new Question("Capital of France?", "Berlin", "Paris", "Madrid", 2));
        questions.add(new Question("Red Planet?", "Earth", "Mars", "Jupiter", 2));
        questions.add(new Question("Largest mammal?", "Elephant", "Blue Whale", "Giraffe", 2));

        displayQuestion();

        option1.setOnClickListener(v -> {
            selectedOption = 1;
            updateOptionColors();
            submitButton.setEnabled(true);
        });
        option2.setOnClickListener(v -> {
            selectedOption = 2;
            updateOptionColors();
            submitButton.setEnabled(true);
        });
        option3.setOnClickListener(v -> {
            selectedOption = 3;
            updateOptionColors();
            submitButton.setEnabled(true);
        });

        submitButton.setOnClickListener(v -> {
            boolean isCorrect = selectedOption == questions.get(currentQuestionIndex).getCorrectAnswer();
            showAnswerFeedback(isCorrect);

            //disable options and submit button after answer is submitted
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            submitButton.setEnabled(false);

            //move to next question after a 1 sec delay
            new android.os.Handler().postDelayed(() -> {
                handleAnswer(selectedOption);
                //reset for next question
                selectedOption = 0;
                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                updateOptionColors();
            }, 1000);
        });
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionText.setText(currentQuestion.getQuestionText());
        option1.setText(currentQuestion.getOption1());
        option2.setText(currentQuestion.getOption2());
        option3.setText(currentQuestion.getOption3());
    }

    private void updateOptionColors() {
        //reset all buttons to default color
        option1.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
        option2.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
        option3.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));

        //highlight selected button
        Button selectedButton = null;
        if (selectedOption == 1) selectedButton = option1;
        else if (selectedOption == 2) selectedButton = option2;
        else if (selectedOption == 3) selectedButton = option3;

        if (selectedButton != null) {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_purple));
        }
    }

    private void showAnswerFeedback(boolean isCorrect) {
        Button selectedButton = null;
        if (selectedOption == 1) selectedButton = option1;
        else if (selectedOption == 2) selectedButton = option2;
        else if (selectedOption == 3) selectedButton = option3;

        if (selectedButton != null) {
            int colorRes = isCorrect ? R.color.correct_answer : R.color.wrong_answer;
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }

    private void handleAnswer(int selectedOption) {
        //check if answer is correct
        if (selectedOption == questions.get(currentQuestionIndex).getCorrectAnswer()) {
            score++;
        }
        //move to next question or finish quiz
        progressBar.setProgress((currentQuestionIndex + 1) * 25);

        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion();
        } else {
            //quiz finished go to result activity
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("USERNAME", nameEditText.getText().toString());
            startActivity(intent);
            finish();
        }
    }
}