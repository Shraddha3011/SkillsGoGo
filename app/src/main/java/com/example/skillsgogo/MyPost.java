package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.skillsgogo.CourseImp.Post;
import com.example.skillsgogo.CourseImp.PostListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyPost extends AppCompatActivity {
    PostListAdapter adapter;
    ListView mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String authorEmail = null;
        if (currentUser != null) {
            authorEmail = currentUser.getEmail();
            Log.d("gogo", "Current user email: " + authorEmail);
        } else {
            Log.d("gogo", "No user is currently signed in");
        }



        mylist = findViewById(R.id.listmypost);
        db.collection("skillsgogo/skillsgogo_doc/post").whereEqualTo("email", authorEmail).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        List<Post> ps = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post p = new Post(document.getId(),document.getString("email"), document.getString("name"), document.getString("postText"));
                            ps.add(p);
                            adapter = new PostListAdapter(MyPost.this, ps);
                            mylist.setAdapter(adapter);
                            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                    Post selectedPost = ps.get(i);
                                    String pid=selectedPost.getId();
                                    String name=selectedPost.getName();
                                    String email=selectedPost.getEmail();
                                    String postText =selectedPost.getPostText();

                                    Intent intent=new Intent(MyPost.this,PostDetails.class);
                                    intent.putExtra("pid", pid);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("postText", postText);

                                    startActivity(intent);
                                }
                            });
                            Log.d("gab", document.getId() + " => " + document.getData());
                        }
                    }
                    else {
                        Log.d("gab", "Error getting documents: ", task.getException());
                    }
                });
    }
}