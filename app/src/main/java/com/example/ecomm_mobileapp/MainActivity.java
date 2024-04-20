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
import com.example.ecomm_mobileapp.database.UserDAO;
import com.example.ecomm_mobileapp.database.entities.User;
import com.example.ecomm_mobileapp.databinding.ActivityMainBinding;
import com.example.ecomm_mobileapp.viewHolders.ShopAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;
    private ShopRepository repository;
    public static final String TAG = "CRT_SHOP";
    private ShopViewModel shopViewModel;
//    private ShopDatabase db;
    private int loggedInUserId = -1;
    private UserDAO userDao;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Log.i(MainActivity.TAG, "Hello World");

        repository = ShopRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId);
            startActivity(intent);
        }

        updateSharedPreference();

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        RecyclerView recyclerView = binding.mainRecyclerviewItems;
        final ShopAdapter adapter = new ShopAdapter(new ShopAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProducts().observe(this, products -> {
            adapter.submitList(products);
        });

        //This binds the username to the textView in the main xml
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");

        TextView usernameTextView = findViewById(R.id.textViewUserName);
        usernameTextView.setText(username);

        //Check for Admin status and displays hidden button.
        boolean isAdmin = checkAdminStatus();

        Button adminViewUsersButton = binding.mainButtonViewusers;
        Button admiViewItemsButton = binding.mainButtonViewitems;

        if (isAdmin) {
            adminViewUsersButton.setVisibility(View.VISIBLE);
            admiViewItemsButton.setVisibility(View.VISIBLE);

        } else {
            adminViewUsersButton.setVisibility(View.INVISIBLE);
            admiViewItemsButton.setVisibility(View.INVISIBLE);
        }

        // Set click listener for admin button
        adminViewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: COmplete this method.
                startActivity(AdminViewUsersActivity.adminViewUsersActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });
        admiViewItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: COmplete this method.
                startActivity(AdminViewItemsActivity.adminViewItemsActivityIntentFactory(getApplicationContext(), loggedInUserId));
                finish();
            }
        });




        // TODO: finish adding holder, adapter, and ... for our Shop Class
        // TODO: Learn how to implement multiple Recycler Views.

        // TODO: create class and intent factory for loginActivity
        Button btnLogout = (Button) findViewById(R.id.main_header_logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                Intent intent = LoginActivity.loginIntentFactory(MainActivity.this, loggedInUserId);
                startActivity(intent);
                finish();
                logout();
            }
        });

        // TODO: create class and intent factory for viewCartActivity
        Button btnViewCart = (Button)findViewById(R.id.main_button_viewcart);
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked the ViewCart button", Toast.LENGTH_LONG).show();
                startActivity(ViewCartActivity.viewCartActivityIntentFactory(getApplicationContext(), loggedInUserId) );
                finish();
            }
        });
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
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(ACTIVITIES_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId));
        finish();
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

    private boolean checkAdminStatus() {
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isAdmin", false);
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACTIVITIES_USER_ID, userId);
        return intent;
    }
}



