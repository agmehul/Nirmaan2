package com.nirmaan_bits.nirmaan.attendance;

public class attn_member {
    String name;
    String status;
    String key;

    public attn_member() {
    }

    public attn_member(String name, String status,String key) {
        this.name = name;
        this.status = status;
        this.key = key;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
