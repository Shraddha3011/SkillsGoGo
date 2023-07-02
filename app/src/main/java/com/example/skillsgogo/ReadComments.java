package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.skillsgogo.CourseImp.FeedBackListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReadComments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comments);

        Intent intent = getIntent();
        String id = intent.getStringExtra("pid");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("skillsgogo/skillsgogo_doc/post/" + id + "/comment").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> commentList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String comment = document.getString("comment");
                            commentList.add(comment);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ReadComments.this, android.R.layout.simple_list_item_1, commentList);

                        ListView listView = findViewById(R.id.listfeed);
                        listView.setAdapter(adapter);
                    } else {
                        Log.d("ReadComments", "Error getting documents: ", task.getException());
                    }
                });
    }
}
