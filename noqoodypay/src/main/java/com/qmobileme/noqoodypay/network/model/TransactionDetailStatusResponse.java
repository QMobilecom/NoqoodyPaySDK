package com.qmobileme.noqoodypay.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Saneeb Salam
 * on 02/08/2021.
 */
public class TransactionDetailStatusResponse {

    @SerializedName("TransactionID")
    @Expose
    private String transactionID;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Amount")
    @Expose
    private Double amount;
    @SerializedName("TransactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("TransactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("Reference")
    @Expose
    private String reference;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("TransactionMessage")
    @Expose
    private String transactionMessage;
    @SerializedName("PUN")
    @Expose
    private String pun;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("DollarAmount")
    @Expose
    private Double dollarAmount;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("PayeeName")
    @Expose
    private String payeeName;
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

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransactionMessage() {
        return transactionMessage;
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    public String getPun() {
        return pun;
    }

    public void setPun(String pun) {
        this.pun = pun;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(Double dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
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
