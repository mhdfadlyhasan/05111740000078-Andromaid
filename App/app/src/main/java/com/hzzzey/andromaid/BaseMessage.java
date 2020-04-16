package com.hzzzey.andromaid;

public class BaseMessage {
    public Boolean isSendByUser;
    public String time,Content;
    BaseMessage(Boolean isSendByUser, String time, String Content){
        this.isSendByUser = isSendByUser;
        this.time = time;
        this.Content = Content;
    }
}
