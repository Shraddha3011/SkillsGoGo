package com.example.skillsgogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateFetch extends AppCompatActivity {
    EditText e_title ;
    EditText e_description ;
    EditText e_tutorial ;
    EditText e_author;
    //EditText e_date ;
    //EditText e_time ;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fetch);
        id = getIntent().getStringExtra("id");
        Button addpdf = findViewById(R.id.addpdf);
        addpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateFetch.this,AddPdf.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
        e_title = findViewById(R.id.title);
        e_description = findViewById(R.id.description);
        e_tutorial = findViewById(R.id.tutorial);
        e_author = findViewById(R.id.author);
        //e_date = findViewById(R.id.date);
        //e_time = findViewById(R.id.time);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("skillsgogo/skillsgogo_doc/course").document(id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            // Retrieve the data from the document snapshot
                            String title = documentSnapshot.getString("title");
                            String description = documentSnapshot.getString("description");
                            String tutorial = documentSnapshot.getString("content");
                            String date = documentSnapshot.getString("date");
                            String author = documentSnapshot.getString("author");
                            String time = documentSnapshot.getString("time");
                            // Retrieve other fields as needed
                            // Populate the EditText fields with the retrieved data
                            e_title.setText(title);
                            e_description.setText(description);
                            e_tutorial.setText(tutorial);
                            //e_date.setText(date);
                            e_author.setText(author);
                            //e_time.setText(time);
                            // Populate other EditText fields
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("updatefetch", "Error retrieving course data", e);
                        Toast.makeText(UpdateFetch.this, "Error retrieving course data", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateFetch.this, TeacherHome.class);
                        startActivity(intent);
                    }
                });

        Button b = findViewById(R.id.addque);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateFetch.this,AddQue.class);
                intent.putExtra("id", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    public void go(View view) {
        id= getIntent().getStringExtra("id");

        // Get the updated values from the EditText fields
        String updatedTitle = e_title.getText().toString();
        String updatedDescription = e_description.getText().toString();
        String updatedTutorial = e_tutorial.getText().toString();
        //String updatedDate = e_date.getText().toString();
        String updatedAuthor = e_author.getText().toString();
        //String updatedTime = e_time.getText().toString();

        // Perform the update operation in the Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("skillsgogo/skillsgogo_doc/course").document(id)
                .update(
                        "title", updatedTitle,
                        "description", updatedDescription,
                        "content", updatedTutorial,
//                        "date", updatedDate,
                        "author", updatedAuthor
//                        "time", updatedTime
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateFetch.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateFetch.this,UpdateCourse.class);
                        startActivity(i);
                        finish();
                        // Redirect to the desired activity or perform any other action upon successful update
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("updatefetch", "Error updating course", e);
                        Toast.makeText(UpdateFetch.this, "Error updating course", Toast.LENGTH_SHORT).show();
                    }
                });




    }
}