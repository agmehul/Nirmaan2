package com.nirmaan_bits.nirmaan.idea;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class idea {
    String author;
    String content;
    String time;
    String project;
    int upvotes;
    Map<String, Boolean> upvoters = new HashMap<>();
    @Exclude
    String key;

    public idea() {
    }

    public idea(String author,String content, String time, String project, int upvotes, String key) {
        this.author = author;
        this.content = content;
        this.time = time;
        this.project = project;
        this.upvotes = upvotes;
        this.key = key;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public Map<String, Boolean> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(Map<String, Boolean> upvoters) {
        this.upvoters = upvoters;
    }

    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
