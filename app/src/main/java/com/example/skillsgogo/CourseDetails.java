package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CourseDetails extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        String title= getIntent().getStringExtra("title");
        String author= getIntent().getStringExtra("author");
        String authorEmail= getIntent().getStringExtra("authorEmail");
        String description= getIntent().getStringExtra("description");
        String content= getIntent().getStringExtra("content");
        String id= getIntent().getStringExtra("id");
        //String content = selectedCourse.getContent();
        // Display the course title and content in the TextViews
        TextView courseTitleTextView = findViewById(R.id.course_title);
        courseTitleTextView.setText(title);

        TextView courseAuthorTextView= findViewById(R.id.course_author);
        courseAuthorTextView.setText(author);

        TextView courseEmailTextView = findViewById(R.id.course_authorEmail);
        courseEmailTextView.setText(authorEmail);

        TextView courseDescriptionTextView = findViewById(R.id.course_description);
        courseDescriptionTextView.setText(description);

        TextView courseContentTextView = findViewById(R.id.course_content);
        courseContentTextView.setText(content);

        Button seeFilesButton = findViewById(R.id.see);

        seeFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, SeeFile.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        Button b = findViewById(R.id.givequiz);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CourseDetails.this,GiveQuiz.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        Button compiler = findViewById(R.id.compiler);
        compiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.programiz.com/c-programming/online-compiler/"; // Replace with the URL of the online compiler
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Button fb = findViewById(R.id.feedback);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CourseDetails.this,GiveFeedBack.class);
                i.putExtra("id",id);
                startActivity(i);


            }
        });

        Button rf = findViewById(R.id.readfeedback);
        rf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(CourseDetails.this,ReadFeedback.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });




    }




}