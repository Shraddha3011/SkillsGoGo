package com.example.skillsgogo;


import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skillsgogo.CourseImp.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCourse extends AppCompatActivity {
    String id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        //create course on submit button
        Button subbtn = findViewById(R.id.submitcourse);
        subbtn.setOnClickListener(view -> {
            EditText e_title = findViewById(R.id.title);
            EditText e_description = findViewById(R.id.description);
            EditText e_tutorial = findViewById(R.id.tutorial);
            EditText e_author = findViewById(R.id.author);
            //EditText e_date = findViewById(R.id.date);
            //EditText e_time = findViewById(R.id.time);
            //get users email
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            String authorEmail = null;
            if (currentUser != null) {
                authorEmail = currentUser.getEmail();
                Log.d("gogo", "Current user email: " + authorEmail);
            } else {
                Log.d("gogo", "No user is currently signed in");
            }


            //store everything in object first
            Course c = new Course(id,String.valueOf(e_title.getText()), String.valueOf(e_description.getText()), String.valueOf(e_author.getText()), String.valueOf(e_tutorial.getText()), authorEmail);

            // Access a Cloud Firestore instance from your Activity
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Convert the Course object to a Map
            Map<String, Object> data = c.toMap();

            Map<String, Object> mainDocName = new HashMap<>();
            mainDocName.put("name", "skillsgogo");

            //create the "skillsgogo" collection
            //"skillsgogo" collection with the ID "skillsgogo_doc" and sets its data to a map containing the name "skillsgogo"
            db.collection("skillsgogo").document("skillsgogo_doc").set(mainDocName)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("gogo", "skillsgogo collection created");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("gogo", "Error creating skillsgogo collection", e);
                        }
                    });

            /// create the "course" subcollection


            db.collection("skillsgogo")
                    .document("skillsgogo_doc")
                    .collection("course")
                    .add(c)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("gogo", "Course added with ID: " + documentReference.getId());
                            id = documentReference.getId();
                            //go to db and store document-id in id

                            DocumentReference courseRef = db.collection("skillsgogo/skillsgogo_doc/course").document(id);

// Update the document with the ID field
                            courseRef
                                    .update("id", id)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "ID stored successfully in the document");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.d("Firestore", "Error storing ID in the document: " + e.getMessage());
                                    });

                            Intent i = new Intent(AddCourse.this, TeacherHome.class);
                            startActivity(i);
                            finish();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("gogo", "Error adding course", e);
                        }
                    });

        });


    }


}