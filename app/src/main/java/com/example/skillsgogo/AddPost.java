package com.example.skillsgogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.skillsgogo.CourseImp.Course;
import com.example.skillsgogo.CourseImp.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        Button subbtn = findViewById(R.id.submitpost);
        subbtn.setOnClickListener(view -> {
            EditText e_name = findViewById(R.id.yourName);
            EditText e_postText = findViewById(R.id.postText);

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
            Post c = new Post(null,authorEmail,e_name.getText().toString(), e_postText.getText().toString());

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
                    .collection("post")
                    .add(c)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("gogo", "post added with ID: " + documentReference.getId());
                            String id = documentReference.getId();
                            //go to db and store document-id in id

                            DocumentReference postRef = db.collection("skillsgogo/skillsgogo_doc/post").document(id);

                            // Update the document with the ID field
                            postRef
                                    .update("id", id)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "ID stored successfully in the post");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.d("Firestore", "Error storing ID in the document: " + e.getMessage());
                                    });

                            Intent i = new Intent(AddPost.this, HomeFragment.class);
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