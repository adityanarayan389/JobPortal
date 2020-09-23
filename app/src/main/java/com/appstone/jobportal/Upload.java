package com.appstone.jobportal;

import com.google.firebase.database.Exclude;

public class Upload {

    private String mName;
    private String mName2;
    private String mName3;
    private String mName4;
    private String mImageUrl;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }
    public Upload(String name, String name2, String name3, String name4, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        if (name2.trim().equals("")) {
            name2 = "No Name";
        }

        if (name3.trim().equals("")) {
            name3 = "No Name";
        }

        if (name4.trim().equals("")) {
            name4 = "No Name";
        }

        mName = name;
        mName2 = name2;
        mName3 = name3;
        mName4 = name4;
        mImageUrl = imageUrl;
    }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getName2() {
        return mName2;
    }
    public void setName2(String name2) {
        mName2 = name2;
    }

    public String getName3() {
        return mName3;
    }
    public void setName3(String name3) {
        mName3 = name3;
    }

    public String getName4() {
        return mName4;
    }
    public void setName4(String name4) {
        mName4 = name4;
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
