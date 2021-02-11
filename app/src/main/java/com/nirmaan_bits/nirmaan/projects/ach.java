package com.nirmaan_bits.nirmaan.projects;

import com.google.firebase.database.Exclude;

public class ach {
    String content;
    @Exclude
    String key;

    public ach(String content, String key) {
        this.content = content;
        this.key = key;
    }

    public ach() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
