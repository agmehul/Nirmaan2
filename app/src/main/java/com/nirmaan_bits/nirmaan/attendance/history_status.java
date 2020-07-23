package com.nirmaan_bits.nirmaan.attendance;

public class history_status {
    String date;
    String status;

    public history_status() {
    }

    public history_status(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
