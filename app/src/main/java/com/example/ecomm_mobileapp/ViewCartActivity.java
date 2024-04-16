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

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.databinding.ActivityMainBinding;
import com.example.ecomm_mobileapp.databinding.ActivityViewCartBinding;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;
import com.example.ecomm_mobileapp.viewHolders.ViewCartViewAdapter;

public class ViewCartActivity extends AppCompatActivity {

    private ActivityViewCartBinding binding;
    private ShopRepository repository;

    // default currently at admin1 with password admin1
    // TODO: fix later incorporating SavedInstanceState and SharedPreferences
    private int loggedInUserId = 1;

    private ShopViewModel shopViewModel;

//    TODO: talk to Maria and talk about adding new Model,Holder,Adapters for each RecyclerView instance within the app
//    private ShopViewModel shopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        Button btnBackToMain = (Button)findViewById(R.id.viewcart_button_back_to_main);
        Button btnCheckout = (Button)findViewById(R.id.viewcart_button_checkout);

        //TODO: incorporate RecyclerView in order to view products in cart of associated userId along with the quantity of the item they possess.
        RecyclerView recyclerView = binding.viewCartRecyclerviewItems;
        final ViewCartViewAdapter adapter = new ViewCartViewAdapter(new ViewCartViewAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProductsInCartByUserId(loggedInUserId).observe(this, products -> {
            adapter.submitList(products);
        });


        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CheckoutActivity.checkoutActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

    }

    static Intent viewCartActivityIntentFactory(Context context, int userId) {

        Intent intent = new Intent(context, ViewCartActivity.class);

        return intent;
    }
}