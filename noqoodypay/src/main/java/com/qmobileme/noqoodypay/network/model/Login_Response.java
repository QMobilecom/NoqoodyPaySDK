package com.qmobileme.noqoodypay.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saneeb Salam
 * on 26/06/2021.
 */
public class Login_Response {

    @SerializedName("access_token")
    String access_token;
    @SerializedName("token_type")
    String token_type;
    @SerializedName("error_description")
    String error_description;
    @SerializedName("UserCode")
    String UserCode;
    @SerializedName("UserId")
    String UserId;
    @SerializedName("FirstName")
    String FirstName;
    @SerializedName("LastName")
    String LastName;
    @SerializedName("ImageLocation")
    String ImageLocation;
    @SerializedName("Email")
    String Email;

    public String getEmail() {
        return Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getImageLocation() {
        return ImageLocation;
    }


    public String getUserId() {
        return UserId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public String getError_description() {
        return error_description;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }
}
