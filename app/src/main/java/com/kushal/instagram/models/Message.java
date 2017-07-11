package com.kushal.instagram.models;

/**
 * Created by kusha on 7/11/2017.
 */

public class Message {
    public Message(){


    }

    public String getReceiversUid() {
        return receiversUid;
    }

    public void setReceiversUid(String receiversUid) {
        this.receiversUid = receiversUid;
    }
    String senderUid;

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    String receiversUid;
    String dp;

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    String data ;
}
