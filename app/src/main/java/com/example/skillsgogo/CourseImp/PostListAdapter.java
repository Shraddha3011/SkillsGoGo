package com.example.skillsgogo.CourseImp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.skillsgogo.R;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post> {

    private LayoutInflater inflater;
    private List<Post> posts;

    public PostListAdapter(Context context, List<Post> posts) {
        super(context, R.layout.activity_read_post, posts);
        this.posts = posts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_read_post, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.authname);
        TextView emailTextView = convertView.findViewById(R.id.post);


        Post post = posts.get(position);
        nameTextView.setText(post.getName());
        emailTextView.setText(post.getEmail());


        return convertView;
    }

    @Override
    public Post getItem(int position) {
        return super.getItem(position);
    }
}
