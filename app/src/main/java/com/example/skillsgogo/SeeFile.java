package com.example.skillsgogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeeFile extends AppCompatActivity {
    private ListView listView;
    private List<DocumentSnapshot> fileList;
    private FirebaseFirestore db;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_file);

        listView = findViewById(R.id.list_viewseefie);
        fileList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Get the course ID from the previous activity
        courseId = getIntent().getStringExtra("id");
        Toast.makeText(SeeFile.this, courseId, Toast.LENGTH_SHORT).show();

        loadFiles();

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentSnapshot fileSnapshot = fileList.get(position);
                String fileUrl = fileSnapshot.getString("fileUrl");
                openFile(fileUrl);
            }
        });
    }

    private void loadFiles() {
        // Retrieve files from Firestore collection
        db.collection("skillsgogo/skillsgogo_doc/course").document(courseId).collection("file")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int numFiles = 0; // Variable to keep track of the number of files fetched
                            for (DocumentSnapshot document : task.getResult()) {
                                fileList.add(document);
                                numFiles++;
                            }
                            // Display a toast message with the number of files fetched
                            Toast.makeText(SeeFile.this, "Fetched " + numFiles + " files", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SeeFile.this, "Failed to load files from Firestore", Toast.LENGTH_SHORT).show();
                        }

                        // Create an ArrayAdapter to display the file names in the ListView
                        List<String> fileNames = getFileNames(fileList);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SeeFile.this, android.R.layout.simple_list_item_1, fileNames);
                        listView.setAdapter(adapter);
                    }
                });
    }

    private List<String> getFileNames(List<DocumentSnapshot> fileList) {
        List<String> fileNames = new ArrayList<>();
        for (DocumentSnapshot document : fileList) {
            String fileName = document.getString("name");
            if (fileName != null) {
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    private void openFile(String fileUrl) {
        // Replace this with your code to open the file using an appropriate application
        Uri uri = Uri.parse(fileUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(SeeFile.this, "No PDF viewer application found", Toast.LENGTH_SHORT).show();
        }
    }
}
