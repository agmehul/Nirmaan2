package com.nirmaan_bits.nirmaan;

public class contact_members {
    String mail;
    String name;
    long num;
    String position;

    public contact_members() {
    }

    public contact_members(String mail, String name, long num, String position) {
        this.mail = mail;
        this.name = name;
        this.num = num;
        this.position = position;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
