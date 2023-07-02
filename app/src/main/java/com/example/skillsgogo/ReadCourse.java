package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillsgogo.CourseImp.Course;
import com.example.skillsgogo.CourseImp.CourseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadCourse extends AppCompatActivity {
    CourseListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_course);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String authorEmail = null;
        if (currentUser != null) {
            authorEmail = currentUser.getEmail();
        }

        ListView mylist = findViewById(R.id.listview);
        db.collection("skillsgogo/skillsgogo_doc/course").whereEqualTo("authorEmail", authorEmail).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        List<Course> courses = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Course course = new Course(document.getId(),document.getString("title"), document.getString("description"), document.getString("author"), document.getString("content"), document.getString("authorEmail"));
                            courses.add(course);
                            adapter = new CourseListAdapter(this, courses);
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
                                    Intent intent=new Intent(ReadCourse.this,CourseDetails.class);
                                    intent.putExtra("title", String.valueOf(title));
                                    intent.putExtra("author", String.valueOf(author));
                                    intent.putExtra("authorEmail", String.valueOf(authorEmail));
                                    intent.putExtra("description", String.valueOf(description));
                                    intent.putExtra("content", String.valueOf(content));
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



