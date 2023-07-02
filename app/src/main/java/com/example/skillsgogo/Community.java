package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Community extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        Button read = findViewById(R.id.readpost);
        Button add = findViewById(R.id.addpost);
        Button my = findViewById(R.id.mypost);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Community.this,ReadPost.class);
                startActivity(i);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Community.this,AddPost.class);
                startActivity(i);

            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Community.this,MyPost.class);
                startActivity(i);

            }
        });
    }
}