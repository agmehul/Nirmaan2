package com.nirmaan_bits.nirmaan.note;

public class Note {
    private String title;
    private String content;
    private String date;
    private String author;
    private String project;


    public Note(){}
    public Note(String title,String content,String date,String author,String project){
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
