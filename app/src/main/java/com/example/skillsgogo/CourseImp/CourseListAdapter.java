package com.example.skillsgogo.CourseImp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.skillsgogo.R;

import java.util.List;

public class CourseListAdapter extends ArrayAdapter<Course> {

    private LayoutInflater inflater;
    private List<Course> courses;

    public CourseListAdapter(Context context, List<Course> courses) {
        super(context, R.layout.activity_add_course, courses);
        this.courses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_read_course, parent, false);
        }
        TextView titleTextView = convertView.findViewById(R.id.course_title);
        TextView descriptionTextView = convertView.findViewById(R.id.course_description);

        Course course = courses.get(position);
        titleTextView.setText(course.getTitle());
        descriptionTextView.setText(course.getDescription());

        return convertView;
    }

    @Override
    public Course getItem(int position) {
        return super.getItem(position);
    }
}
