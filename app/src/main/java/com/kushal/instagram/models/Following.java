package com.kushal.instagram.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kusha on 7/9/2017.
 */

public class Following  implements Parcelable{
    public Following(){

    }
    String followingname;

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    String following;

    protected Following(Parcel in) {
        followingname = in.readString();
        state = in.readString();
    }

    public static final Creator<Following> CREATOR = new Creator<Following>() {
        @Override
        public Following createFromParcel(Parcel in) {
            return new Following(in);
        }

        @Override
        public Following[] newArray(int size) {
            return new Following[size];
        }
    };

    public String getFollowingname() {
        return followingname;
    }

    public void setFollowingname(String followingname) {
        this.followingname = followingname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String state;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(followingname);
        parcel.writeString(state);
    }
}
