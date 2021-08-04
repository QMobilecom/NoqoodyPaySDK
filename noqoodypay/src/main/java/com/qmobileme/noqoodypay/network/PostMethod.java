package com.qmobileme.noqoodypay.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Saneeb Salam
 * on 15/04/2021.
 */
public class PostMethod extends Thread {

    private final String URL;
    private final Call<JsonObject> responseCall;
    JSONObject responseobject;
    ApiInterface apiInterface;

    public <T> PostMethod(String URL, JSONObject requestJSON, HashMap<String, String> header) {
        this.URL = URL;
        apiInterface = APIServiceProvider.getClient().create(ApiInterface.class);
        responseCall = apiInterface.postcall(URL, header, new Gson().fromJson(requestJSON.toString(), JsonObject.class));
    }


    @Override
    public void run() {
        super.run();

        callmethod();

    }

    <T> void callmethod() {
        responseCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        responseobject = new JSONObject(response.body().toString());

                        if (responseobject.getBoolean("success")) {
                            EventBus.getDefault().post(new DataReceiveEvent(true, URL, responseobject.toString()));


                        } else
                            EventBus.getDefault().post(new DataReceiveEvent(false, URL, responseobject.toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new DataReceiveEvent(false, URL, "{\"message\":\"Failed...\"}"));
                    }
                } else
                    EventBus.getDefault().post(new DataReceiveEvent(false, URL, "{\"message\":\"Failed...\"}"));

            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                call.cancel();
                EventBus.getDefault().post(new DataReceiveEvent(false, URL, "{\"message\":\"Failed...\"}"));

            }
        });
    }

}
