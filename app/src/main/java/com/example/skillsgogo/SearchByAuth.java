package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.skillsgogo.CourseImp.Course;
import com.example.skillsgogo.CourseImp.CourseListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchByAuth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_auth);
        ListView mylist = findViewById(R.id.listsearchbyauth);

        String author = getIntent().getStringExtra("author");


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("skillsgogo/skillsgogo_doc/course")
                .whereEqualTo("author", author)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Course> courses = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Course course = new Course(document.getId(),document.getString("title"), document.getString("description"), document.getString("author"), document.getString("content"), document.getString("authorEmail"));
                            courses.add(course);
                            CourseListAdapter adapter = new CourseListAdapter(SearchByAuth.this, courses);
                            mylist.setAdapter(adapter);
                            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                    Course selectedCourse = courses.get(i);
                                    String title=selectedCourse.getTitle();
                                    String author=selectedCourse.getAuthor();
                                    String authorEmail=selectedCourse.getAuthorEmail();
                                    String description=selectedCourse.getDescription();
                                    String content=selectedCourse.getContent();
                                    String id = selectedCourse.getId();
                                    Intent intent=new Intent(SearchByAuth.this,CourseDetails.class);
                                    intent.putExtra("title", title);
                                    intent.putExtra("author", author);
                                    intent.putExtra("authorEmail", authorEmail);
                                    intent.putExtra("description", description);
                                    intent.putExtra("content", content);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                }
                            });
                            Log.d("gab", document.getId() + " => " + document.getData());
                        }
                    }
                    else {
                        Log.d("gab", "Error getting documents: ", task.getException());
                    }
                });
    }
}