package com.qmobileme.noqoodypay.network;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saneebsalam
 * on 15/04/2021.
 */

public class LoginMethod extends Thread {

    private final String Tag;
    private final Call responseCall;
    JSONObject responseobject;

    public <T> LoginMethod(Call<T> responseCall, String Tag) {
        this.Tag = Tag;
        this.responseCall = responseCall;
    }

    @Override
    public void run() {
        super.run();
        callmethod();
    }

    <T> void callmethod() {

        responseCall.clone().enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                try {
                    if (response.isSuccessful()) {
                        responseobject = new JSONObject(new Gson().toJson(response.body()));
                        if (responseobject.has("success"))
                            EventBus.getDefault().post(new DataReceiveEvent(responseobject.getBoolean("success"), Tag, responseobject.toString()));
                        else
                            EventBus.getDefault().post(new DataReceiveEvent(true, Tag, responseobject.toString()));
                    } else
                        EventBus.getDefault().post(new DataReceiveEvent(false, Tag, response.errorBody().string()));

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                LoginMethod.this.interrupt();
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
                EventBus.getDefault().post(new DataReceiveEvent(false, Tag, t.getMessage()));
                LoginMethod.this.interrupt();
            }
        });
    }


}
