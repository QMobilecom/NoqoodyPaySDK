package com.qmobileme.noqoodypay.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Saneeb Salam
 * on 28/07/2021.
 */
public class PaymentChannel {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("ChannelName")
    @Expose
    private String channelName;
    @SerializedName("ImageLocation")
    @Expose
    private String imageLocation;
    @SerializedName("PaymentURL")
    @Expose
    private String paymentURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getPaymentURL() {
        return paymentURL;
    }

    public void setPaymentURL(String paymentURL) {
        this.paymentURL = paymentURL;
    }

}
