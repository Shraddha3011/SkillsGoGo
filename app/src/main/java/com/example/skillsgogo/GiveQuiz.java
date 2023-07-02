package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GiveQuiz extends AppCompatActivity {

    private CountDownTimer quizTimer;
    private TextView timerTextView;
    private int score = 0;
    private TextView questionTitleTextView;
    private TextView answerTextView;
    private Button nextButton;

    private FirebaseFirestore db;
    private List<String> questionList;
    private int currentQuestionIndex = 0;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_give_quiz);


        timerTextView = findViewById(R.id.timerTextView);
        questionTitleTextView = findViewById(R.id.questionTitle);
        answerTextView = findViewById(R.id.answer);

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextQuestion();
            }
        });


        Intent i = getIntent();
        id = i.getStringExtra("id");

        db = FirebaseFirestore.getInstance();
        questionList = new ArrayList<>();

        // Retrieve the questions from the subcollection
        db.collection("skillsgogo/skillsgogo_doc/course/" + id + "/question")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        long numberOfQuestions = (long) task.getResult().size();
                        //Toast.makeText(GiveQuiz.this, ""+numberOfQuestions, Toast.LENGTH_SHORT).show();
                        long timePerQuestion = 60000; // 1 minute in milliseconds
                        long quizDuration = 60000; // 1 minutes in milliseconds
                        long tickInterval = 1000; // 1 second

                        quizTimer = new CountDownTimer(quizDuration*numberOfQuestions, tickInterval) {
                            public void onTick(long millisUntilFinished) {
                                // Update the UI with the remaining time
                                long minutes = millisUntilFinished / 1000 / 60;
                                long seconds = (millisUntilFinished / 1000) % 60;
                                String timeFormatted = String.format("Time remaining: %02d:%02d", minutes, seconds);
                                timerTextView.setText(timeFormatted);
                            }

                            public void onFinish() {
                                // Handle the quiz completion when the timer finishes
                                finishQuiz();
                            }
                        };

                        // Start the quiz timer
                        quizTimer.start();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String question = document.getString("question");
                            questionList.add(question);
                        }
                        displayNextQuestion();
                    } else {
                        // Handle error
                    }
                });


    }

    private void finishQuiz() {
        // Stop the quiz timer if it's running
        if (quizTimer != null) {
            quizTimer.cancel();
        }

        // Perform necessary actions for quiz completion
        // For example, display a completion message or submit answers
        // ...
    }

    private void endQuiz() {
        if (quizTimer != null) {
            quizTimer.cancel();
        }

        // Perform necessary actions for quiz completion
        // For example, display the score
        String scoreMessage = "Quiz ended!\nYour score: " + score + "/" + questionList.size();
        Toast.makeText(this, scoreMessage, Toast.LENGTH_SHORT).show();

        // Start the QuizResultActivity and pass the score as an extra
        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questionList.size());
        startActivity(intent);
        finish();
    }

    private void displayNextQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            String question = questionList.get(currentQuestionIndex);
            questionTitleTextView.setText(question);

            // Retrieve the current question's options and answer
            db.collection("skillsgogo/skillsgogo_doc/course/" + id + "/question")
                    .whereEqualTo("question", question)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String option1 = document.getString("op1");
                                String option2 = document.getString("op2");
                                String option3 = document.getString("op3");
                                String option4 = document.getString("op4");
                                String answer = document.getString("ans");

                                RadioButton op1 = findViewById(R.id.op1);
                                op1.setText(option1);

                                RadioButton op2 = findViewById(R.id.op2);
                                op2.setText(option2);

                                RadioButton op3 = findViewById(R.id.op3);
                                op3.setText(option3);

                                RadioButton op4 = findViewById(R.id.op4);
                                op4.setText(option4);

                                op1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkAnswer(op1, answer);
                                    }
                                });

                                op2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkAnswer(op2, answer);
                                    }
                                });

                                op3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkAnswer(op3, answer);
                                    }
                                });

                                op4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkAnswer(op4, answer);
                                    }
                                });
                                RadioGroup radioGroup = findViewById(R.id.optionsGroup);
                                radioGroup.clearCheck();
                            }
                        } else {
                            // Handle error
                            endQuiz();
                        }
                    });

            currentQuestionIndex++;
        } else {
            Toast.makeText(this, "End of Quiz", Toast.LENGTH_SHORT).show();
            endQuiz();
            // Display quiz completion message or perform any other necessary actions
        }
    }

    private void checkAnswer(RadioButton selectedOption, String answer) {

        String selectedAnswer = selectedOption.getText().toString();
        if (selectedAnswer.equals(answer)) {
            answerTextView.setText("Correct!");
            score++;
        } else {
            answerTextView.setText("Incorrect!");
        }
        // Clear the selection of the RadioGroup

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the quiz timer to avoid memory leaks
        if (quizTimer != null) {
            quizTimer.cancel();
        }
    }
}
