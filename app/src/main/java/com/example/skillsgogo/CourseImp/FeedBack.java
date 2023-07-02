package com.example.skillsgogo.CourseImp;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class FeedBack {
     String feedback;
    String rating;

    public FeedBack(String feedback, String rating) {
        this.feedback = feedback;
        this.rating = rating;
    }
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("feedback", feedback);
        result.put("rating", rating);

        return result;

    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
