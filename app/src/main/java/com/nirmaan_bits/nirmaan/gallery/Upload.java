package com.nirmaan_bits.nirmaan.gallery;

import com.google.firebase.database.Exclude;

public class Upload {
   // private String mName;
    private String mImageUrl;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }

    public Upload( String imageUrl) {

        mImageUrl = imageUrl;
    }


    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
