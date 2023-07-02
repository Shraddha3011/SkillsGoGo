package com.example.skillsgogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skillsgogo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RemovePdf extends AppCompatActivity {

    private EditText fileNameEditText;
    private Button removeButton;

    private FirebaseFirestore db;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_pdf);
        Intent i = getIntent();
        id = i.getStringExtra("id");

        fileNameEditText = findViewById(R.id.fileNameEditText);
        removeButton = findViewById(R.id.removeButton);

        db = FirebaseFirestore.getInstance();

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = fileNameEditText.getText().toString().trim();
                removeFile(fileName);
            }
        });
    }

    private void removeFile(String fileName) {
        // Replace "courseId" with the actual ID of the course containing the PDF files

        String courseId = id;

        // Remove the file from Firestore
        db.collection("skillsgogo/skillsgogo_doc/course").document(courseId).collection("file")
                .whereEqualTo("name", fileName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Delete the document (file) from the collection
                                db.collection("skillsgogo/skillsgogo_doc/course")
                                        .document(courseId)
                                        .collection("file")
                                        .document(document.getId())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // File successfully removed
                                                    Toast.makeText(RemovePdf.this, "File removed successfully", Toast.LENGTH_SHORT).show();
                                                    Intent n = new Intent(RemovePdf.this,ReadCourse.class);
                                                    startActivity(n);
                                                    finish();
                                                } else {
                                                    // Failed to remove the file
                                                    Toast.makeText(RemovePdf.this, "Failed to remove the file", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            // Failed to fetch the file
                            Toast.makeText(RemovePdf.this, "Failed to fetch the file", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
