package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.databinding.ActivityMainBinding;
import com.example.ecomm_mobileapp.databinding.ActivityViewCartBinding;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;

public class ViewCartActivity extends AppCompatActivity {

    private ActivityViewCartBinding binding;
    private ShopRepository repository;

    private int loggedInUserId;

//    TODO: talk to Maria and talk about adding new Model,Holder,Adapters for each RecyclerView instance within the app
//    private ShopViewModel shopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());

    }

    static Intent viewCartActivityIntentFactory(Context context, int userId) {

        Intent intent = new Intent(context, ViewCartActivity.class);

        return intent;
    }
}