package com.kushal.instagram.models;

/**
 * Created by kusha on 7/9/2017.
 */

public class Following {
    public Following(){

    }
    String followingname;

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
}
