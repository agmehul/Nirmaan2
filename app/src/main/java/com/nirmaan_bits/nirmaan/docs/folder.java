package com.nirmaan_bits.nirmaan.docs;

import com.google.firebase.database.Exclude;

public class folder {
    String name;
    int size;
    @Exclude
    String key;

    public folder(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public folder(String name, int size, String key) {
        this.name = name;
        this.size = size;
        this.key = key;
    }

    public folder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
