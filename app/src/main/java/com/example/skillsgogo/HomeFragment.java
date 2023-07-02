package com.example.skillsgogo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    private Button teachButton;
    private Button learnButton;
    private Button commuButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        teachButton = view.findViewById(R.id.teach);
        learnButton = view.findViewById(R.id.learn);
        commuButton = view.findViewById(R.id.Community);

        teachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTeacher();
            }
        });

        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStudent();
            }
        });

        commuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCommu();
            }
        });

        return view;
    }

    public void goTeacher() {
        Intent intent = new Intent(getActivity(), TeacherHome.class);
        startActivity(intent);
    }

    public void goStudent() {
        Intent intent = new Intent(getActivity(), StudentView.class);
        startActivity(intent);
    }

    public  void goCommu(){
        Intent intent = new Intent(getActivity(), Community.class);
        startActivity(intent);

    }

}