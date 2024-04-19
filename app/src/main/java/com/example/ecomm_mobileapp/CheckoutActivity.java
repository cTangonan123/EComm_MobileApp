package com.example.ecomm_mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.databinding.ActivityCheckoutBinding;
import com.example.ecomm_mobileapp.viewHolders.CheckoutItemsAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;
import com.example.ecomm_mobileapp.viewHolders.ViewCartViewAdapter;

import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ActivityCheckoutBinding binding;
    private ShopRepository repository;

    private ShopViewModel shopViewModel;

    private Button btnBackToStore;
    private Button btnSelectPayment;
    private Button btnLogout;
    private TextView usernameTextView;



    private boolean isPaymentMethodExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        loginUser(savedInstanceState);


        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        btnBackToStore = binding.backToStoreButton;
        btnSelectPayment = binding.selectPaymentButton;
        btnLogout = binding.checkoutHeaderLogoutButton;
        usernameTextView = findViewById(R.id.textViewUserName);

        //This binds the username to the textView in the main xml
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");
        usernameTextView.setText(username);


        RecyclerView recyclerView = binding.checkoutRecyclerviewItems;
        final CheckoutItemsAdapter adapter1 = new CheckoutItemsAdapter(new CheckoutItemsAdapter.ShopDiff());
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProductsInCartByUserId(loggedInUserId).observe(this, products -> {
            double total = 0;

            for (int i = 0; i < products.size(); i++) {
                total += products.get(i).getProductPrice();
            }
            binding.checkoutTextTotalcost.setText(String.format(Locale.US, "Total: $%,.2f", total));
            adapter1.submitList(products);
        });

//        LiveData<List<Payment>> paymentObserver = repository.getAllPaymentsByUserId(loggedInUserId);
//        paymentObserver.observe(this, payments -> {
//
//            if (payments != null) {
//
//                if (payments.size() > 0) {
//                    isPaymentMethodExist = true;
//                    Log.i(MainActivity.TAG, String.valueOf(isPaymentMethodExist));
//                }
//            }
//        });



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
                validateUserPaymentInfo();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(CheckoutActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = LoginActivity.loginIntentFactory(CheckoutActivity.this, loggedInUserId);
                startActivity(intent);
                finish();
            }
        });

    }

    private void validateUserPaymentInfo() {
        repository.getAllPaymentsByUserid(loggedInUserId).observe(CheckoutActivity.this, payments -> {
            if (payments.size() > 0) {
                Log.i(MainActivity.TAG, "payments is not empty");
                startActivity(PaymentActivity.paymentIntentFactory(getApplicationContext(), loggedInUserId));
            } else {
                startActivity(PaymentMethodActivity.paymentMethodIntentFactory(getApplicationContext(), loggedInUserId));
            }

        });
    }

    static Intent checkoutActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        return intent;
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpreference_file_key), Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(ACTIVITIES_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
    }

    private void logout() {
        loggedInUserId =  LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(ACTIVITIES_USER_ID, LOGGED_OUT); // should I make this value in main public?
        // Return to login Screen
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedpreference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }


}