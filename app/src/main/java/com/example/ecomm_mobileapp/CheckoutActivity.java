package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }

    static Intent checkoutActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        return intent;
    }
}