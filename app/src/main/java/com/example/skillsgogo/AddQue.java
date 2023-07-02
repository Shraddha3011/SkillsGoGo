package com.example.skillsgogo;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsgogo.CourseImp.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class AddQue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_que);


        Button quebtn = findViewById(R.id.quebtn);
        quebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= getIntent().getStringExtra("id");

                EditText que = findViewById(R.id.que);
                EditText op1 = findViewById(R.id.op1);
                EditText op2 = findViewById(R.id.op2);
                EditText op3 = findViewById(R.id.op3);
                EditText op4 = findViewById(R.id.op4);
                EditText ans = findViewById(R.id.ans);

                Question q = new Question(que.getText().toString(),op1.getText().toString(),op2.getText().toString(),op3.getText().toString(),op4.getText().toString(),ans.getText().toString());

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Convert the Question object to a Map
                Map<String, Object> data = q.toMap();


                db.collection("skillsgogo/skillsgogo_doc/course").document(id).collection("question").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddQue.this, "quetion added", Toast.LENGTH_SHORT).show();
                                Log.d("gabque","que added");
                                Intent idIntent = getIntent();
                                String id = idIntent.getStringExtra("id");
                                Intent i  = new Intent(AddQue.this,UpdateFetch.class);
                                i.putExtra("id",id);
                                startActivity(i);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {

                            public void onFailure( Exception e) {
                                Toast.makeText(AddQue.this, "error in adding que", Toast.LENGTH_SHORT).show();
                                Log.d("gabque","error que");
                            }
                        });






            }
        });
    }
}