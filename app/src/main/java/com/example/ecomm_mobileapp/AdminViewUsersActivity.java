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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.databinding.ActivityAdminViewUsersBinding;
import com.example.ecomm_mobileapp.viewHolders.AdminViewUsersAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;


public class AdminViewUsersActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ActivityAdminViewUsersBinding binding;
    private ShopRepository repository;
    private ShopViewModel shopViewModel;

    Button btnBackToMain;
    Button btnAddUser;

    Button btnLogout;
    TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginUser(savedInstanceState); //

        repository = ShopRepository.getRepository(getApplication());
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        RecyclerView recyclerView = binding.adminViewusersRecyclerviewUsers;
        final AdminViewUsersAdapter adapter = new AdminViewUsersAdapter(new AdminViewUsersAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);

        });

        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");

        TextView usernameTextView;

        btnBackToMain = binding.adminViewUsersButtonBackToMain;
        btnAddUser = binding.adminViewUsersButtonAddUser;
        btnLogout = binding.adminHeaderLogoutButton;

        usernameTextView = binding.textViewUserName;
        usernameTextView.setText(username);

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminAddUserActivity.adminAddUserIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(AdminViewUsersActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
                finish();
            }
        });




    }

    static Intent adminViewUsersActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminViewUsersActivity.class);
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