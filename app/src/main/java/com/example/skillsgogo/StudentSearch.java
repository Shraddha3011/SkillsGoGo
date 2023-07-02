package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skillsgogo.CourseImp.Course;
import com.example.skillsgogo.CourseImp.CourseListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentSearch extends AppCompatActivity {

    private EditText authorEditText;
    private EditText courseEditText;
    private EditText categoryEditText;
    private Button searchButton1;
    private Button searchButton2;
    private Button searchButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);

        authorEditText = findViewById(R.id.authoret);
        courseEditText = findViewById(R.id.courseet);
        categoryEditText = findViewById(R.id.categoryet);
        searchButton1 = findViewById(R.id.search_auth);
        searchButton2 = findViewById(R.id.search_title);
        searchButton3 = findViewById(R.id.search_cat);


        searchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String author = authorEditText.getText().toString().trim();
                Intent i = new Intent(StudentSearch.this,SearchByAuth.class);
                i.putExtra("author",author);
                startActivity(i);




            }
        });
        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseTitle = courseEditText.getText().toString().trim();
                Intent i = new Intent(StudentSearch.this,SearchByTitle.class);
                i.putExtra("title",courseTitle);
                startActivity(i);



            }
        });
        searchButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categoryEditText.getText().toString().trim();
                Intent i = new Intent(StudentSearch.this,SearchBycat.class);
                i.putExtra("category",category);
                startActivity(i);



            }
        });


    }
}
