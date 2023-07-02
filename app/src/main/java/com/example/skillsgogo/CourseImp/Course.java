package com.example.skillsgogo.CourseImp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Course implements Parcelable{
    private String id;
    private String title;
    private String description;
    private String author;
    // private String date;
    // private String time;
    private String content;

    private String authorEmail;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Course(String id, String title, String description, String author, String content, String authorEmail) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.author = author;
        //this.date = date;
        //this.time = time;
        this.content = content;
        this.authorEmail = authorEmail;
    }

    protected Course(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        author = in.readString();
//        date = in.readString();
//        time = in.readString();
        content = in.readString();
        authorEmail = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public String getTime() {
//        return time;
//    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("author", author);
//        result.put("date", date);
//        result.put("time", time);
        result.put("content", content);
        result.put("authorEmail", authorEmail);
        return result;


    }

    @Override
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(author);
//        dest.writeString(date);
//        dest.writeString(time);
        dest.writeString(content);
        dest.writeString(authorEmail);

    }
}