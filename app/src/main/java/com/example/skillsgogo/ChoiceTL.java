package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceTL extends AppCompatActivity {
    Button t = findViewById(R.id.teach);
    Button l = findViewById(R.id.learn);
    Button c = findViewById(R.id.Community);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_tl);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ChoiceTL.this,TeacherHome.class);
                startActivity(i);

            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ChoiceTL.this,StudentHome.class);
                startActivity(i);

            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChoiceTL.this,Community.class);
                startActivity(i);
            }
        });
    }
}