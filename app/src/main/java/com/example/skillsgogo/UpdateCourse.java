package com.example.skillsgogo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCourse extends AppCompatActivity {
    CourseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

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
                                    String id=selectedCourse.getId();
                                    Intent intent=new Intent(UpdateCourse.this,UpdateFetch.class);
                                    intent.putExtra("id", String.valueOf(id));
                                    startActivity(intent);
                                }
                            });
                            Log.d("gogo", document.getId() + " => " + document.getData());
                        }
                    }
                    else {
                        Log.d("gogo", "Error getting documents: ", task.getException());
                    }
                });
    }
}
