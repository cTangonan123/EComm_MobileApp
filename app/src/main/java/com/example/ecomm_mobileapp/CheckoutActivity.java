package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.database.entities.relations.CartWithProduct;
import com.example.ecomm_mobileapp.databinding.ActivityCheckoutBinding;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private ActivityCheckoutBinding binding;
    private ShopRepository repository;

    private ShopViewModel shopViewModel;

    private Button btnBackToStore;
    private Button btnCheckout;
    private int loggedInUserId = 1;

    private List<CartWithProduct> cartWithProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        btnBackToStore = binding.backToStoreButton;
        btnCheckout = binding.checkoutButton;

        LiveData<List<Cart>> cartObserver = repository.getAllCartsByUserId(loggedInUserId);

        cartObserver.observe(this, carts -> {
            if (carts != null) {
                for (int i = 0; i < carts.size(); i++) {
                Log.i(MainActivity.TAG, carts.get(i).toString());
                Cart tempCart = carts.get(i);
                    LiveData<Product> productObserver = repository.getProductByProductId(carts.get(i).getProductId());
                    final int j = i;
                    productObserver.observe(this, product -> {
                       if (product!= null) {
                           Log.i(MainActivity.TAG, product.toString());
                           Product tempProduct = product;
                           CartWithProduct tCwP = new CartWithProduct(tempCart, product);
                           cartWithProduct.add(tCwP);
                           Log.i(MainActivity.TAG, tCwP.toString());
                           Log.i(MainActivity.TAG, "This works! :\n" + cartWithProduct.toString());
                       }
                    });
                }

            }
        });

        btnBackToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Create a method which places an order
                // TODO: Fix Order and OrderDAO in order to take in multiple Carts.
                Toast.makeText(CheckoutActivity.this, "You clicked the checkout button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    static Intent checkoutActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        return intent;
    }
}