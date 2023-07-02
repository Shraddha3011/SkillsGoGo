package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillsgogo.CourseImp.FeedBack;
import com.example.skillsgogo.CourseImp.FeedBackListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
public class ReadFeedback extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_feedback);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("skillsgogo/skillsgogo_doc/course/" + id + "/feedback").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<FeedBack> many = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String feedback = document.getString("feedback");
                            String rating = document.getString("rating");

                            FeedBack feed = new FeedBack(feedback,rating);
                            many.add(feed);
                        }

                        FeedBackListAdapter adapter = new FeedBackListAdapter(ReadFeedback.this, many);
                        ListView listView = findViewById(R.id.listfeed);
                        listView.setAdapter(adapter);
                    } else {
                        Log.d("ReadFeedback", "Error getting documents: ", task.getException());
                    }
                });
    }
}
