package com.qmobileme.noqoodypay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.qmobileme.noqoodypay.databinding.NoqoodypayLayoutPaymentlistBinding;
import com.qmobileme.noqoodypay.network.APIServiceProvider;
import com.qmobileme.noqoodypay.network.ApiInterface;
import com.qmobileme.noqoodypay.network.DataReceiveEvent;
import com.qmobileme.noqoodypay.network.GetMethod;
import com.qmobileme.noqoodypay.network.LoginMethod;
import com.qmobileme.noqoodypay.network.PostMethod;
import com.qmobileme.noqoodypay.network.adapter.Adapter_PaymentList;
import com.qmobileme.noqoodypay.network.model.GenerateLinksResponse;
import com.qmobileme.noqoodypay.network.model.Login_Response;
import com.qmobileme.noqoodypay.network.model.PaymentChannel;
import com.qmobileme.noqoodypay.network.model.PaymentChannelsResponse;
import com.qmobileme.noqoodypay.network.model.TransactionDetailStatusResponse;
import com.qmobileme.noqoodypay.util.Encoder;
import com.qmobileme.noqoodypay.util.Showdialog_Failed;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Saneeb Salam
 * on 26/06/2021.
 */
public class Activity_NoqoodyPay extends AppCompatActivity {
    public NumberFormat NumberFormat_Currency = new DecimalFormat("#,#00.00");
    ApiInterface apiInterface;
    HashMap<String, String> request = new HashMap();
    JSONObject requestJSON;
    HashMap<String, String> headerrequest = new HashMap();
    String UserName, Password, CustomerMobile = "", ProjectCode = "", Description = "",
            RedirectURL = "", CustomerEmail = "", PaymentURL = "", ReferenceNo = "", ClientSecret = "";
    Double Amount;
    Login_Response login_response;
    GenerateLinksResponse generateLinksResponse;
    NoqoodypayLayoutPaymentlistBinding binding;
    Encoder encoder;
    Adapter_PaymentList adapterpaymentlist;
    List<PaymentChannel> paymentChannelList = new ArrayList<>();
    int RequestPayment = 1;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NoqoodypayLayoutPaymentlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();


        apiInterface = APIServiceProvider.getClient().create(ApiInterface.class);
        encoder = new Encoder();

        UserName = getIntent().getStringExtra(NoqoodyPay_Keys.UserName);
        Password = getIntent().getStringExtra(NoqoodyPay_Keys.Password);
        Amount = getIntent().getDoubleExtra(NoqoodyPay_Keys.amount, 0.0);
        CustomerEmail = getIntent().getStringExtra(NoqoodyPay_Keys.CustomerEmail);
        CustomerMobile = getIntent().getStringExtra(NoqoodyPay_Keys.CustomerMobile);
        ProjectCode = getIntent().getStringExtra(NoqoodyPay_Keys.ProjectCode);
        Description = getIntent().getStringExtra(NoqoodyPay_Keys.Description);
        RedirectURL = getIntent().getStringExtra(NoqoodyPay_Keys.RedirectURL);
        ReferenceNo = getIntent().getStringExtra(NoqoodyPay_Keys.Reference);
        ClientSecret = getIntent().getStringExtra(NoqoodyPay_Keys.ClientSecret);


        binding.merchantName.setText(UserName);
        binding.description.setText(Description);
        binding.totalamount.setText("Total Amount: QAR " + NumberFormat_Currency.format(Amount));
        binding.granttotal.setText("QAR " + NumberFormat_Currency.format(Amount));

        Glide.with(this)
                .load(R.drawable.loading_noqoody)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(binding.load);

        //Payment List
        adapterpaymentlist = new Adapter_PaymentList(binding.rv.getContext(), paymentChannelList);
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        binding.rv.setAdapter(adapterpaymentlist);
        adapterpaymentlist.setOnClickListener(position -> PaymentURL = paymentChannelList.get(position).getPaymentURL());

        Login();

        binding.paynow.setOnClickListener(view -> {
            if (PaymentURL.isEmpty())
                new Showdialog_Failed(this, "Please select one payment method");
            else {
                Intent intent = new Intent(this, Activity_Payment_redirect.class);
                intent.putExtra(NoqoodyPay_Keys.Paymenturl, PaymentURL);
                intent.putExtra(NoqoodyPay_Keys.RedirectURL, RedirectURL);
                startActivityForResult(intent, RequestPayment);
            }

        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DataReceiveEvent event) {
        JSONObject response;
        try {
            binding.rvLayoutLoading.setVisibility(View.GONE);

            response = new JSONObject(event.getResponseMessage());

            if (event.getResponseStatus()) {
                if (event.getEventTag().equalsIgnoreCase(apiInterface.login)) {
                    login_response = new Gson().fromJson(response.toString(), Login_Response.class);
                    PaymentGenerateLinks(login_response);
                } else if (event.getEventTag().equalsIgnoreCase(ApiInterface.GenerateLinks_URL)) {
                    PaymentChannels(response);
                } else if (event.getEventTag().equalsIgnoreCase(ApiInterface.PaymentChannels_URL)) {
                    PaymentChannelData(response);
                } else if (event.getEventTag().equalsIgnoreCase(ApiInterface.GetTransactionDetailStatusByClientReference_URL)) {
                    TransactionDetailStatusResponse transactionDetailStatusResponse = new Gson().fromJson(response.toString(), TransactionDetailStatusResponse.class);
                    ResultMessage(transactionDetailStatusResponse.getSuccess(), transactionDetailStatusResponse.getMessage(), response.toString());
                }

            } else {
                if (event.getEventTag().equalsIgnoreCase(apiInterface.login)) {
                    if (response.has("error_description"))
                        new Showdialog_Failed(this, response.getString("error_description")).setOnClickListener(() -> {
                            try {
                                ResultMessage(false, response.getString("error_description"), response.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    else
                        new Showdialog_Failed(this, "Failed...").setOnClickListener(() -> ResultMessage(false, "Failed", ""));
                } else if (event.getEventTag().equalsIgnoreCase(ApiInterface.GetTransactionDetailStatusByClientReference_URL)) {
                    ResultMessage(false, "Transaction not found", "");
                } else
                    new Showdialog_Failed(this, response.getString("message")).setOnClickListener(() -> ResultMessage(false, "Failed", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void Login() {

        binding.rvLayoutLoading.setVisibility(View.VISIBLE);
        request.clear();
        request.put("grant_type", "password");
        request.put("response_type", "token");
        request.put("username", UserName);
        request.put("password", Password);


        Call<Login_Response> ResponseCall = apiInterface.getLogin(apiInterface.login, request);
        new LoginMethod(ResponseCall, apiInterface.login).start();
    }

    void PaymentGenerateLinks(Login_Response login_response) {

        Glide.with(this)
                .load(login_response.getImageLocation())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).error(R.drawable.noqoody_payprofile))
                .into(binding.merchantimage);

        binding.rvLayoutLoading.setVisibility(View.VISIBLE);

        requestJSON = new JSONObject();
        try {
            requestJSON.put("ProjectCode", ProjectCode);
            requestJSON.put("Description", Description);
            requestJSON.put("Amount", Amount);
            requestJSON.put("CustomerEmail", CustomerEmail);
            requestJSON.put("CustomerMobile", CustomerMobile);
            requestJSON.put("CustomerName", UserName);
            requestJSON.put("Reference", ReferenceNo);
//        request.put("MerchantDefinedData", System.currentTimeMillis() + "define");
            requestJSON.put("SecureHash", encoder.HmacSHA256(CustomerEmail + UserName +
                    CustomerMobile + Description + ProjectCode + ReferenceNo, ClientSecret));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        headerrequest.clear();
        headerrequest.put("Authorization", login_response.getToken_type() + " " + login_response.getAccess_token());


//        Call<GenerateLinksResponse> ResponseCall = apiInterface.getGenerateLinks(headerrequest, new Gson().fromJson(request.toString(), JsonObject.class));
        new PostMethod(ApiInterface.GenerateLinks_URL, requestJSON, headerrequest).start();
    }

    void PaymentChannels(JSONObject response) {
        binding.rvLayoutLoading.setVisibility(View.VISIBLE);

        generateLinksResponse = new Gson().fromJson(response.toString(), GenerateLinksResponse.class);

        request.clear();
        request.put("session_id", generateLinksResponse.getSessionId());
        request.put("uuid", generateLinksResponse.getUuid());

        new GetMethod(ApiInterface.PaymentChannels_URL, request, headerrequest).start();
    }

    private void PaymentChannelData(JSONObject response) {
        paymentChannelList = new Gson().fromJson(response.toString(), PaymentChannelsResponse.class).getPaymentChannels();
        adapterpaymentlist.setResults(paymentChannelList);
    }

    void paymentStatus() {

        binding.rvLayoutLoading.setVisibility(View.VISIBLE);

        request.clear();
        request.put("ReferenceNo", ReferenceNo);
        new GetMethod(ApiInterface.GetTransactionDetailStatusByClientReference_URL, request, headerrequest).run();
    }

    @Override
    public void onBackPressed() {
        ResultMessage(false, "Back Pressed", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestPayment) {
            paymentStatus();
        }
    }

    private void ResultMessage(boolean Status, String TransactionMessage, String paymentresultData) {

        Intent intent = new Intent();
        intent.putExtra(NoqoodyPay_Keys.paymentresult_status, Status);
        intent.putExtra(NoqoodyPay_Keys.paymentresult, TransactionMessage);
        intent.putExtra(NoqoodyPay_Keys.paymentresultData, paymentresultData);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }


}
