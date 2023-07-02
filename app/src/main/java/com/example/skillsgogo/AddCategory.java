package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCategory extends AppCompatActivity {

    private FirebaseFirestore db;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = FirebaseFirestore.getInstance();

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = findViewById(R.id.categoryRadioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    String selectedCategory = radioButton.getText().toString();

                    // Store the selected category in Firestore
                    DocumentReference courseRef = db.collection("skillsgogo")
                            .document("skillsgogo_doc")
                            .collection("course")
                            .document(id);
                    courseRef.update("category", selectedCategory);

                    Toast.makeText(AddCategory.this, "Category saved: " + selectedCategory, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCategory.this, "Please select a category", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(AddCategory.this,TeacherHome.class);
                startActivity(i);
                finish();
            }
        });
    }
}