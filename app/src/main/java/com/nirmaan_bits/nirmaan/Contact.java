
package com.nirmaan_bits.nirmaan;

public class Contact {


    private String name;
    private String year;
    private String num;
    private String pl;
    private String key;
    private String visits;
    private String email;

    public Contact() {
    }

    public Contact(String name, String year, String num, String pl, String key, String visits,String email) {
        this.name = name;
        this.year = year;
        this.num = num;
        this.pl = pl;
        this.key = key;
        this.visits  = visits;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }
}

