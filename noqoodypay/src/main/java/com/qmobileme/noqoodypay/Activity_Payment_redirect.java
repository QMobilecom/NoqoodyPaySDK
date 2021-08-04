package com.qmobileme.noqoodypay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.qmobileme.noqoodypay.databinding.NoqoodypayLayoutPaymentBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Saneeb Salam
 * on 2/20/2017.
 */

public class Activity_Payment_redirect extends AppCompatActivity {

    String URL, RedirectUrl;
    NoqoodypayLayoutPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NoqoodypayLayoutPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        Glide.with(this)
                .load(R.drawable.loading_noqoody)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(binding.layoutLoading.load);

        URL = getIntent().getStringExtra(NoqoodyPay_Keys.Paymenturl);
        RedirectUrl = getIntent().getStringExtra(NoqoodyPay_Keys.RedirectURL);

        DisplayURL(URL);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void DisplayURL(String value) {
        binding.layoutLoading.getRoot().setVisibility(View.VISIBLE);

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setLoadsImagesAutomatically(true);
        binding.webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        binding.webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
//                MainActivity.this.setProgress(progress * 1000);
                if (progress == 100) {
                    binding.layoutLoading.getRoot().setVisibility(View.GONE);
                }
            }
        });

        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.contains(RedirectUrl)) {

//                    binding.webview.loadUrl("javascript:HtmlViewer.showHTML" +
//                            "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    ReultMessage(false, "Payment Cancelled");
                }
            }
        });

        binding.webview.loadUrl(value);
    }

    public Document getDomElement(String xml) {
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    @Override
    public void onBackPressed() {
        ReultMessage(false, "Payment Cancelled");
    }

    private void ReultMessage(boolean Status, String TransactionMessage) {

        Intent intent = new Intent();
        intent.putExtra(NoqoodyPay_Keys.paymentresult, TransactionMessage);
        intent.putExtra(NoqoodyPay_Keys.paymentresult_status, Status);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        publishThread.interrupt();
//        subscribeThread.interrupt();
    }

    private class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            try {
                Document doc = getDomElement(html);
                NodeList nl = doc.getElementsByTagName("html");
                for (int temp = 0; temp < nl.getLength(); temp++) {
                    org.w3c.dom.Node nNode = nl.item(temp);

                    Element eElement = (Element) nNode;

                    html = eElement.getElementsByTagName("body")
                            .item(0).getTextContent();
                    html = html.replace("\\", "");
                    if (html.startsWith("\""))
                        html = html.trim().substring(1, html.length() - 1);

                }
                JSONObject response = new JSONObject(html);

                Intent intent = new Intent();

                if (response.has("success"))
                    intent.putExtra(NoqoodyPay_Keys.paymentresult_status, response.getBoolean("success"));
                if (response.has("TransactionMessage"))
                    intent.putExtra(NoqoodyPay_Keys.paymentresult, response.getString("TransactionMessage"));
                else if (response.has("message"))
                    intent.putExtra(NoqoodyPay_Keys.paymentresult, response.getString("message"));
                else {
                    intent.putExtra(NoqoodyPay_Keys.paymentresult, response.getString("Message"));
                    intent.putExtra(NoqoodyPay_Keys.paymentresult_status, response.getBoolean("IsOk"));
                }

                setResult(Activity.RESULT_OK, intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
