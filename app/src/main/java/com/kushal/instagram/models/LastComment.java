package com.kushal.instagram.models;

/**
 * Created by kusha on 7/12/2017.
 */

public class LastComment {
    public LastComment(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    String name , recent;
}
