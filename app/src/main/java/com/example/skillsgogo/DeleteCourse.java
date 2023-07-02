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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteCourse extends AppCompatActivity {
    CourseListAdapter adapter;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);
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
                                    String docid = selectedCourse.getId();
                                    String name=selectedCourse.getTitle();
                                    /*if (docid == null) {
                                        Toast.makeText(delete.this, docid+ "null", Toast.LENGTH_SHORT).show();
                                    }*/
                                    builder=new AlertDialog.Builder(DeleteCourse.this);
                                    builder.setTitle("Alert!")
                                            .setMessage("Are u sure you want to delete this course?")
                                            .setCancelable(true)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //use this docid as reference to document and delete entire document
                                                    db.collection("skillsgogo").document("skillsgogo_doc")
                                                            .collection("course")
                                                            .document(docid)
                                                            .delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d("delete", "Course deleted with ID: " + i);
                                                                    Toast.makeText(DeleteCourse.this, name+ " Course deleted successfully", Toast.LENGTH_SHORT).show();
                                                                    // Redirect to the home screen after deleting the course
                                                                    Intent intent = new Intent(DeleteCourse.this, TeacherHome.class);
                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.e("delete", "Error deleting course", e);
                                                                    Toast.makeText(DeleteCourse.this, "Error deleting course", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(DeleteCourse.this, TeacherHome.class);
                                                                    startActivity(intent);
                                                                }
                                                            });


                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            })
                                            .show();

                                    //Intent intent=new Intent(delete.this,alert.class);
                                    //startActivity(intent);




                                }
                            });
                            Log.d("gab", document.getId() + " => " + document.getData());
                        }
                    }
                    else {
                        Log.d("gab", "Error getting documents: ", task.getException());
                    }
                });





       /* Button subbtn = findViewById(R.id.deletecourse);
        subbtn.setOnClickListener(view -> {
            Intent i = new Intent(delete.this, Student_home.class);
            EditText id = findViewById(R.id.id);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            String authorEmail = null;
            if (currentUser != null) {
                authorEmail = currentUser.getEmail();
                Log.d("gab", "Current user email: " + authorEmail);
            } else {
                Log.d("gab", "No user is currently signed in");
            }
            // Get a reference to the Firestore database
            // Get a reference to the Firestore database
            FirebaseFirestore db = FirebaseFirestore.getInstance();

// Get the ID of the course to delete
            String courseId = id.getText().toString();

// Delete the course from the "course" subcollection under the "skillsgogo_doc" document
            db.collection("skillsgogo").document("skillsgogo_doc")
                    .collection("course")
                    .document(courseId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("delete", "Course deleted with ID: " + courseId);
                            Toast.makeText(delete.this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
                            // Redirect to the home screen after deleting the course
                            Intent intent = new Intent(delete.this, Student_home.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("delete", "Error deleting course", e);
                            Toast.makeText(delete.this, "Error deleting course", Toast.LENGTH_SHORT).show();
                        }
                    });

        });*/
    }
}