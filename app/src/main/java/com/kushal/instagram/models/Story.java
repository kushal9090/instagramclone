package com.kushal.instagram.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kusha on 7/14/2017.
 */

public class Story implements Parcelable{
    public Story(){


    }
    String  dp;
    String email;
    String name;
    String story;

    protected Story(Parcel in) {
        dp = in.readString();
        email = in.readString();
        name = in.readString();
        story = in.readString();
        key = in.readString();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
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

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dp);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(story);
        parcel.writeString(key);
    }
}
