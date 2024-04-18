package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.databinding.ActivityAdminViewUsersBinding;
import com.example.ecomm_mobileapp.databinding.ActivityViewCartBinding;
import com.example.ecomm_mobileapp.viewHolders.AdminViewUsersAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;


public class AdminViewUsersActivity extends AppCompatActivity {
    private ActivityAdminViewUsersBinding binding;

    private ShopRepository repository;
    private int loggedInUserId = 1;
    private ShopViewModel shopViewModel;

    Button btnBackToMain;
    Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        RecyclerView recyclerView = binding.adminViewusersRecyclerviewUsers;
        final AdminViewUsersAdapter adapter = new AdminViewUsersAdapter(new AdminViewUsersAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);

        });

        btnBackToMain = binding.adminViewUsersButtonBackToMain;
        btnAddUser = binding.adminViewUsersButtonAddUser;

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminAddUserActivity.adminAddUserIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });




    }

    static Intent adminViewUsersActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminViewUsersActivity.class);
        return intent;
    }
}