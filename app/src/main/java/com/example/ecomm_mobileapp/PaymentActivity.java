package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.databinding.ActivityPaymentBinding;
import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.viewHolders.PaymentAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;
    private ShopRepository repository;
    private ShopViewModel shopViewModel;

    private int loggedInUserId = 1;

    private List<Payment> paymentInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        RecyclerView recyclerView = binding.paymentRecyclerviewItems;
        final PaymentAdapter adapter = new PaymentAdapter(new PaymentAdapter.PaymentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllPaymentsByUserId(loggedInUserId).observe(this, payment -> {
            adapter.submitList(payment);
        });



        binding.paymentButtonBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.paymentButtonToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPaymentMethodActivity();
            }
        });
    }

    static Intent paymentIntentFactory(Context context, int userId) {
        return new Intent(context, PaymentActivity.class);
    }

    private void goToPaymentMethodActivity() {
        Intent intent = new Intent(this, PaymentMethodActivity.class);
        startActivity(intent);
    }


}