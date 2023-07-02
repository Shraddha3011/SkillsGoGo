package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GiveFeedBack extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feed_back);
        String id= getIntent().getStringExtra("id");

        Button b = findViewById(R.id.submitBtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.feedbackEditText);
                String s = e.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                RatingBar r = findViewById(R.id.ratingBar);
                String star = String.valueOf(r.getRating());

                Map<String,String> data = new HashMap<>();
                data.put("feedback",s);
                data.put("rating",star);



                db.collection("skillsgogo/skillsgogo_doc/course").document(id).collection("feedback").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(GiveFeedBack.this, "feedback added", Toast.LENGTH_SHORT).show();
                                Log.d("gogo","feedback added");

                                Intent i  = new Intent(GiveFeedBack.this,CourseDetails.class);
                                String title= getIntent().getStringExtra("title");
                                String author= getIntent().getStringExtra("author");
                                String authorEmail= getIntent().getStringExtra("authorEmail");
                                String description= getIntent().getStringExtra("description");
                                String content= getIntent().getStringExtra("content");
                                String id= getIntent().getStringExtra("id");

                                i.putExtra("id",id);
                                i.putExtra("author",author);
                                i.putExtra("authorEmail",authorEmail);
                                i.putExtra("description",description);
                                i.putExtra("content",content);
                                i.putExtra("title",title);
                                startActivity(i);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {

                            public void onFailure( Exception e) {
                                Toast.makeText(GiveFeedBack.this, "error in adding que", Toast.LENGTH_SHORT).show();
                                Log.d("gogo","error que");
                            }
                        });
            }
        });
    }
}