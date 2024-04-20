package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.databinding.ActivityPaymentBinding;
import com.example.ecomm_mobileapp.viewHolders.PaymentAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ActivityPaymentBinding binding;
    private ShopRepository repository;
    private ShopViewModel shopViewModel;



    private List<Payment> paymentInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginUser(savedInstanceState); //

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        TextView usernameTextView = binding.textViewUserName;
        Button logoutButton = binding.paymentHeaderLogoutButton;

        RecyclerView recyclerView = binding.paymentRecyclerviewItems;
        final PaymentAdapter adapter = new PaymentAdapter(new PaymentAdapter.PaymentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllPaymentsByUserId(loggedInUserId).observe(this, payment -> {
            adapter.submitList(payment);
        });

        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");
        usernameTextView.setText(username);



        binding.paymentButtonBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.paymentButtonToAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPaymentMethodActivity();
            }
        });
        binding.paymentButtonToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CheckoutActivity.checkoutActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });

        binding.paymentHeaderLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
                finish();
                logout();
            }
        });
    }

    static Intent paymentIntentFactory(Context context, int userId) {
        return new Intent(context, PaymentActivity.class);
    }

    private void goToPaymentMethodActivity() {
        Intent intent = PaymentMethodActivity.paymentMethodIntentFactory(getApplicationContext(), loggedInUserId);
        startActivity(intent);
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