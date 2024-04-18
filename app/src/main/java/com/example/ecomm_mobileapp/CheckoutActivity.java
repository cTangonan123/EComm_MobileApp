package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.databinding.ActivityCheckoutBinding;
import com.example.ecomm_mobileapp.viewHolders.CheckoutItemsAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;
import com.example.ecomm_mobileapp.viewHolders.ViewCartViewAdapter;

public class CheckoutActivity extends AppCompatActivity {

    private ActivityCheckoutBinding binding;
    private ShopRepository repository;

    private ShopViewModel shopViewModel;

    private Button btnBackToStore;
    private Button btnSelectPayment;
    private int loggedInUserId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        btnBackToStore = binding.backToStoreButton;
        btnSelectPayment = binding.selectPaymentButton;

        RecyclerView recyclerView = binding.checkoutRecyclerviewItems;
        final CheckoutItemsAdapter adapter1 = new CheckoutItemsAdapter(new CheckoutItemsAdapter.ShopDiff());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProductsInCartByUserId(loggedInUserId).observe(this, products -> {
            adapter1.submitList(products);
        });



        btnBackToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        btnSelectPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Create a method which places an order
                // TODO: Fix Order and OrderDAO in order to take in multiple Carts.
                Toast.makeText(CheckoutActivity.this, "You clicked the Select Payment Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    static Intent checkoutActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        return intent;
    }
}