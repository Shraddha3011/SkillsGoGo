package com.example.skillsgogo.CourseImp;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String id;
    private String email;
    private String name;
    private String postText;

    public Post(String id, String email, String name, String postText) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.postText = postText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", postText='" + postText + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", name);
        result.put("postText", postText);
        return result;
    }
}
