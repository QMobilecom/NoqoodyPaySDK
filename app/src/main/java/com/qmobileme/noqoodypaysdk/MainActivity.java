package com.qmobileme.noqoodypaysdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qmobileme.noqoodypay.NoqoodyPay;
import com.qmobileme.noqoodypay.NoqoodyPay_Keys;
import com.qmobileme.noqoodypaysdk.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    EditText editTextTextEmailAddress, editTextPassword, et_amount;
    //    APIServiceProvider aPIServiceProvider;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        et_amount = findViewById(R.id.et_amount);

    }

    public void OnSubmit(View view) {

        if (editTextTextEmailAddress.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Merchant Email", Toast.LENGTH_SHORT).show();
        else if (editTextPassword.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Merchant Password", Toast.LENGTH_SHORT).show();
        else if (et_amount.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
        else if (binding.cusEmail.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Customer Email", Toast.LENGTH_SHORT).show();
        else if (binding.cusEmail.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Customer Email", Toast.LENGTH_SHORT).show();
        else if (binding.cusMobile.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Customer Mobile", Toast.LENGTH_SHORT).show();
        else if (binding.projectcode.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Project Code", Toast.LENGTH_SHORT).show();
        else if (binding.description.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
        else if (binding.redirecturl.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Redirect URL", Toast.LENGTH_SHORT).show();
        else if (binding.reference.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Reference Number", Toast.LENGTH_SHORT).show();
        else if (binding.clientsecret.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Client Secret", Toast.LENGTH_SHORT).show();
        else if (binding.baseurl.getText().toString().isEmpty())
            Toast.makeText(this, "Please Enter Base URL", Toast.LENGTH_SHORT).show();
        else
            NoqoodyPay.Pay(MainActivity.this, binding.baseurl.getText().toString(), editTextTextEmailAddress.getText().toString(),
                    editTextPassword.getText().toString(), Double.parseDouble(et_amount.getText().toString()),
                    binding.cusEmail.getText().toString(), binding.cusMobile.getText().toString(),
                    binding.projectcode.getText().toString(), binding.description.getText().toString(),
                    binding.redirecturl.getText().toString(), binding.reference.getText().toString(),
                    binding.clientsecret.getText().toString());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NoqoodyPay_Keys.Activity_RequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getBooleanExtra(NoqoodyPay_Keys.paymentresult_status, false)) {
                    Toast.makeText(this, data.getStringExtra(NoqoodyPay_Keys.paymentresult), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, data.getStringExtra(NoqoodyPay_Keys.paymentresult), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}