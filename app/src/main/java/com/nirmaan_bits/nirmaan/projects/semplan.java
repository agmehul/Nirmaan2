package com.nirmaan_bits.nirmaan.projects;

import com.google.firebase.database.Exclude;

public class semplan {
    String plan;
    String complete;
    String weight;
    @Exclude
    String key;

    public semplan(String plan, String key, String complete, String weight) {
        this.plan = plan;
        this.key = key;
        this.complete = complete;
        this.weight = weight;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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
