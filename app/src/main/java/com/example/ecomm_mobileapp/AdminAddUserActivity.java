package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.User;
import com.example.ecomm_mobileapp.databinding.ActivityAdminAddUserBinding;

public class AdminAddUserActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;

    private TextView usernameTextView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameEditText;
    private EditText passwordEditText;

    private Button adminAddNewUserButton;
    private Button adminBackToMainButton;
    private Button adminBackToViewUsersButton;
    private Button btnLogout;

    private ActivityAdminAddUserBinding binding;

    private ShopRepository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginUser(savedInstanceState);

        firstNameEditText = findViewById(R.id.editTextAddFirstName);
        lastNameEditText = findViewById(R.id.editTextAddLastName);
        userNameEditText = findViewById(R.id.editTextAddUsername);
        passwordEditText = findViewById(R.id.editTextAddPassword);
        adminBackToMainButton = findViewById(R.id.admin_view_users_button_back_to_main);
        adminBackToViewUsersButton = findViewById(R.id.admin_view_users_button_checkout);
        usernameTextView = binding.textViewUserName;
        btnLogout = binding.mainHeaderLogoutButton;

        adminAddNewUserButton = findViewById(R.id.buttonAdminAddUser);

        repository = ShopRepository.getRepository(getApplication());

        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");


        usernameTextView.setText(username);

        adminAddNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(AdminAddUserActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkUsernameExistsAndSaveUser(username, password, firstName, lastName);
            }
        });

        adminBackToViewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminViewUsersActivity.adminViewUsersActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        adminBackToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkUsernameExistsAndSaveUser(String username, String password, String firstName, String lastName) {
        repository.getUserByUserName(username).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Toast.makeText(AdminAddUserActivity.this, "This username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    saveUserInfo(username, password, firstName, lastName);

                    Toast.makeText(AdminAddUserActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(AdminViewUsersActivity.adminViewUsersActivityIntentFactory(getApplicationContext(), loggedInUserId));
                    finish();
                }
            }
        });
    }

    private void saveUserInfo(String username, String password, String firstName, String lastName) {
        User user = new User(username, password, false, firstName, lastName);
        repository.insertUser(user);
    }
    static Intent adminAddUserIntentFactory(Context context, int userId) {
        Intent intent =  new Intent(context, AdminAddUserActivity.class);
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