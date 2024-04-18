package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.UserDAO;
import com.example.ecomm_mobileapp.database.entities.User;
import com.example.ecomm_mobileapp.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ShopRepository repository;

    private EditText userNameField;
    private EditText passwordField;

    private UserDAO userDao;

    private int loggedInUserId = -1;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userNameField = binding.editTextLoginUserName;
        passwordField = binding.editTextLoginPassword;

        repository = ShopRepository.getRepository(getApplication());

        getDatabase();

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.buttonCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUserActivity();
            }
        });
    }

    private void verifyUser() {
        String username = userNameField.getText().toString().trim(); //added due to some issues, may take out later.
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            toastMaker("Username and password must not be blank");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (password.equals(user.getPassword())) {
                        saveLoginStatus(username);
                        saveAdminStatus(user.isAdmin());
                        startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                        finish();
                    } else {
                        toastMaker("Invalid password");
                        binding.editTextLoginPassword.setSelection(0);
                    }
                } else {
                    toastMaker(String.format("%s is not a valid username, please create a new account", username));
                    binding.editTextLoginUserName.setSelection(0);
                }
            }
        });
    }

    private void saveLoginStatus(String username) {
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("loggedInUsername", username);
        editor.apply();
    }

    private void saveAdminStatus(boolean isAdmin) {
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isAdmin", isAdmin);
        editor.apply();
    }

    private void createNewUserActivity() {
        Intent intent = CreateNewUserActivity.createNewUserIntentFactory(LoginActivity.this);
        startActivity(intent);
    }

    private void getDatabase() {
        userDao = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .userDAO();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context, int userId) {
        return new Intent(context, LoginActivity.class);
    }
}

