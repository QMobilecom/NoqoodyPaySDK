package com.qmobileme.noqoodypay.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Saneeb Salam
 * on 28/07/2021.
 */
public class PaymentChannelsResponse {
    @SerializedName("PaymentChannels")
    @Expose
    private List<PaymentChannel> paymentChannels = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public List<PaymentChannel> getPaymentChannels() {
        return paymentChannels;
    }

    public void setPaymentChannels(List<PaymentChannel> paymentChannels) {
        this.paymentChannels = paymentChannels;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
