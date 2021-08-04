package com.qmobileme.noqoodypay.network;

/**
 * Created by Saneeb Salam
 * on 20-10-2020.
 */
public class DataReceiveEvent {
    private String eventTag;
    private boolean status;
    private String responseMessage;

    public DataReceiveEvent(boolean status, String eventTag, String responseMessage) {
        this.status = status;
        this.eventTag = eventTag;
        this.responseMessage = responseMessage;
    }


    public boolean getResponseStatus() {
        return status;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getEventTag() {
        return eventTag;
    }
}