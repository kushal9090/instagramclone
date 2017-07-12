package com.kushal.instagram.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kusha on 7/6/2017.
 */

public class Post implements Parcelable{

    public Post(){


    }
    String posttitle;
    String displayName;

    protected Post(Parcel in) {
        posttitle = in.readString();
        displayName = in.readString();
        key = in.readString();
        profilePic = in.readString();
        email = in.readString();
        picuri = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String  key;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    String profilePic;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;
    public String getPicuri() {
        return picuri;
    }

    public void setPicuri(String picuri) {
        this.picuri = picuri;
    }

    String picuri;
    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posttitle);
        parcel.writeString(displayName);
        parcel.writeString(key);
        parcel.writeString(profilePic);
        parcel.writeString(email);
        parcel.writeString(picuri);
    }
}
