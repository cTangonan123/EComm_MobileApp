package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.databinding.ActivityViewCartBinding;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;
import com.example.ecomm_mobileapp.viewHolders.ViewCartViewAdapter;

import java.util.List;
import java.util.Locale;

public class ViewCartActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ActivityViewCartBinding binding;
    private ShopRepository repository;

    // default currently at admin1 with password admin1
    // TODO: fix later incorporating SavedInstanceState and SharedPreferences

    private ShopViewModel shopViewModel;

    private boolean isUserHavePayments = false;

//    TODO: talk to Maria and talk about adding new Model,Holder,Adapters for each RecyclerView instance within the app
//    private ShopViewModel shopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginUser(savedInstanceState); //



        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        Button btnBackToMain = (Button)findViewById(R.id.viewcart_button_back_to_main);
        Button btnToPayment = (Button)findViewById(R.id.viewcart_button_payment);
//        Button btnCheckout = (Button)findViewById(R.id.viewcart_button_checkout);
        Button btnLogout = (Button)findViewById(R.id.viewcart_header_logout_button);
        TextView usernameTextView = findViewById(R.id.textViewUserName);
        TextView subtotalTextView = findViewById(R.id.viewcart_total);

        //This binds the username to the textView in the main xml
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");


        usernameTextView.setText(username);

        //TODO: incorporate RecyclerView in order to view products in cart of associated userId along with the quantity of the item they possess.
        RecyclerView recyclerView = binding.viewCartRecyclerviewItems;
        final ViewCartViewAdapter adapter = new ViewCartViewAdapter(new ViewCartViewAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProductsInCartByUserId(loggedInUserId).observe(this, products -> {
            double subtotal = 0;
            for (int i = 0; i < products.size(); i++) {
                subtotal += products.get(i).getProductPrice();
            }
            subtotalTextView.setText(String.format(Locale.US, "Subtotal: $%,.2f", subtotal));
            adapter.submitList(products);

        });

        LiveData<List<Payment>> paymentObserver = repository.getAllPaymentsByUserid(loggedInUserId);
        paymentObserver.observe(this, payments -> {
            if (payments != null) {
                if(payments.size() != 0) isUserHavePayments = true;
            }
        });



        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });

//        btnCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(CheckoutActivity.checkoutActivityIntentFactory(getApplicationContext(), loggedInUserId));
//                finish();
//            }
//        });

        btnToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserHavePayments) {
                    Intent intent = PaymentActivity.paymentIntentFactory(getApplicationContext(), loggedInUserId);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = PaymentMethodActivity.paymentMethodIntentFactory(getApplicationContext(), loggedInUserId);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(ViewCartActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = LoginActivity.loginIntentFactory(ViewCartActivity.this, loggedInUserId);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent viewCartActivityIntentFactory(Context context, int userId) {

        Intent intent = new Intent(context, ViewCartActivity.class);

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