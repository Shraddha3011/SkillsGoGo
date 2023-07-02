package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        String pid= getIntent().getStringExtra("pid");
        String authorname= getIntent().getStringExtra("name");
        String email= getIntent().getStringExtra("email");
        String post= getIntent().getStringExtra("postText");

        //String content = selectedCourse.getContent();
        // Display the course title and content in the TextViews
        TextView courseTitleTextView = findViewById(R.id.name);
        courseTitleTextView.setText(authorname);

        TextView courseAuthorTextView= findViewById(R.id.email);
        courseAuthorTextView.setText(email);

        TextView courseEmailTextView = findViewById(R.id.post);
        courseEmailTextView.setText(post);



        Button fb = findViewById(R.id.readcomments);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(PostDetails.this,ReadComments.class);
                i.putExtra("pid",pid);
                startActivity(i);




            }
        });

        Button rf = findViewById(R.id.comment);
        rf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PostDetails.this,GiveComments.class);
                i.putExtra("pid",pid);
                startActivity(i);
            }
        });
    }
}