package com.qmobileme.noqoodypay;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Saneeb Salam
 * on 26/06/2021.
 */
public class NoqoodyPay {

    public NoqoodyPay() {

    }

    public static void Pay(Activity activity, String UserName, String Password, Double Amount,
                           String CustomerEmail, String CustomerMobile, String ProjectCode, String Description,
                           String RedirectURL, String Reference, String ClientSecret) {
        Intent intent = new Intent(activity, Activity_NoqoodyPay.class);
        intent.putExtra(NoqoodyPay_Keys.UserName, UserName);
        intent.putExtra(NoqoodyPay_Keys.Password, Password);
        intent.putExtra(NoqoodyPay_Keys.amount, Amount);
        intent.putExtra(NoqoodyPay_Keys.CustomerEmail, CustomerEmail);
        intent.putExtra(NoqoodyPay_Keys.CustomerMobile, CustomerMobile);
        intent.putExtra(NoqoodyPay_Keys.ProjectCode, ProjectCode);
        intent.putExtra(NoqoodyPay_Keys.Description, Description);
        intent.putExtra(NoqoodyPay_Keys.RedirectURL, RedirectURL);
        intent.putExtra(NoqoodyPay_Keys.Reference, Reference);
        intent.putExtra(NoqoodyPay_Keys.ClientSecret, ClientSecret);
        activity.startActivityForResult(intent, NoqoodyPay_Keys.Activity_RequestCode);

    }
}
