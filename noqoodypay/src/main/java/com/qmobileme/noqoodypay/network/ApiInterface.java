package com.qmobileme.noqoodypay.network;


import com.google.gson.JsonObject;
import com.qmobileme.noqoodypay.network.model.Login_Response;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by saneebsalam
 * on 15/04/2021.
 */
public interface ApiInterface {

    //    String baseUrl = "http://api.qatarpay.com/api/";
    String baseUrl = "https://www.noqoodypay.com/sdk/";
//    String baseUrl = "http://151.106.28.182:9222/";

    String login = "Token";
    String GenerateLinks_URL = "api/PaymentLink/GenerateLinks";
    String PaymentChannels_URL = "api/PaymentLink/PaymentChannels";
    String GetTransactionDetailStatusByClientReference_URL = "api/Members/GetTransactionDetailStatusByClientReference";


    @POST
    @FormUrlEncoded
    Call<Login_Response> getLogin(@Url String url, @FieldMap HashMap<String, String> jsonObject);

    @POST()
    Call<JsonObject> postcall(@Url String URL, @HeaderMap Map<String, String> header, @Body JsonObject input);

    @GET()
    Call<JsonObject> getcall(@Url String URL, @QueryMap Map<String, String> request, @HeaderMap Map<String, String> header);

//    @POST(VerifyOtpforsdk_URL)
//    Call<QRCodePaymentsdk_Response> getVerifyOtpforsdk(@HeaderMap HashMap<String, String> header, @Body JsonObject jsonObject);


}
