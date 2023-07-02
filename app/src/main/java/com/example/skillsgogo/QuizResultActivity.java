package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);

        // Find the TextView to display the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.append("Score: " + score + "/" + totalQuestions);
    }
}