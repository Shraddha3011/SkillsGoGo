package com.example.skillsgogo.CourseImp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.skillsgogo.R;

import java.util.List;
public class FeedBackListAdapter extends ArrayAdapter<FeedBack> {

    private LayoutInflater inflater;
    private List<FeedBack> feedbackList;

    public FeedBackListAdapter(Context context, List<FeedBack> feedbackList) {
        super(context, R.layout.activity_read_feedback, feedbackList);
        this.feedbackList = feedbackList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_read_feedback, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.feedback);
        TextView descriptionTextView = convertView.findViewById(R.id.rating);

        FeedBack feedback = feedbackList.get(position);
        titleTextView.setText(feedback.getFeedback());
        descriptionTextView.setText(feedback.getRating());

        return convertView;
    }
}
