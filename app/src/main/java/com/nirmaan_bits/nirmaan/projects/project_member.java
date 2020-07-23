package com.nirmaan_bits.nirmaan.projects;

public class project_member {
    String name;
    String num;
    String year;
    String email;

    public project_member() {
    }

    public project_member(String name, String num, String year,String email) {
        this.name = name;
        this.num = num;
        this.year = year;
        this.email = email;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
