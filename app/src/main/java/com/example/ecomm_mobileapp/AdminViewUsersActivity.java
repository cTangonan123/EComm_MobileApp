package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomm_mobileapp.databinding.ActivityAdminViewUsersBinding;
import com.example.ecomm_mobileapp.databinding.ActivityViewCartBinding;


public class AdminViewUsersActivity extends AppCompatActivity {
    private ActivityAdminViewUsersBinding binding;
    private int loggedInUserId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);
    }
}