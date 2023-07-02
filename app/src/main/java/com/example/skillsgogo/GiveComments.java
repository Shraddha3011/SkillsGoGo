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

import com.example.skillsgogo.CourseImp.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GiveComments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_comments);
        String id= getIntent().getStringExtra("pid");

        Button b = findViewById(R.id.submitcomment);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = findViewById(R.id.postComment);
                String s = e.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();


                Map<String,String> data = new HashMap<>();
                data.put("comment",s);



                db.collection("skillsgogo/skillsgogo_doc/post").document(id).collection("comment").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(GiveComments.this, "comment added", Toast.LENGTH_SHORT).show();
                                Log.d("gogo","comment added");
                                Intent intent = new Intent(GiveComments.this,Community.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {

                            public void onFailure( Exception e) {
                                Toast.makeText(GiveComments.this, "error in adding que", Toast.LENGTH_SHORT).show();
                                Log.d("gogo","error que");
                            }
                        });
            }
        });
    }
}