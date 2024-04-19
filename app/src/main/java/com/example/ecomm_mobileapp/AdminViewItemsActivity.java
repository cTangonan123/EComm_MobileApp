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

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.databinding.ActivityAdminViewItemsBinding;
import com.example.ecomm_mobileapp.databinding.ActivityAdminViewUsersBinding;
import com.example.ecomm_mobileapp.databinding.ActivityViewCartBinding;
import com.example.ecomm_mobileapp.viewHolders.AdminViewItemsAdapter;
import com.example.ecomm_mobileapp.viewHolders.AdminViewUsersAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;
import com.example.ecomm_mobileapp.viewHolders.ViewCartViewAdapter;

public class AdminViewItemsActivity extends AppCompatActivity {

    private ActivityAdminViewItemsBinding binding;
    private ShopRepository repository;

    private int loggedInUserId = 1;
    private ShopViewModel shopViewModel;

    Button btnBackToMain;
    Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        RecyclerView recyclerView = binding.adminViewItemsRecyclerviewItems;
        final AdminViewItemsAdapter adapter = new AdminViewItemsAdapter(new AdminViewItemsAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProducts().observe(this, product -> {
            adapter.submitList(product);

        });

        btnBackToMain = binding.adminItemsButtonBackToMain;
        btnAddItem = binding.adminButtonAddItems;
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminAddItemsActivity.adminAddItemsIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });
    }

    static Intent adminViewItemsActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminViewItemsActivity.class);
        return intent;
    }
}