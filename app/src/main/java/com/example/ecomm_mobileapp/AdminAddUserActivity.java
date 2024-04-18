package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.User;
import com.example.ecomm_mobileapp.databinding.ActivityAdminAddUserBinding;

public class AdminAddUserActivity extends AppCompatActivity {


    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameEditText;
    private EditText passwordEditText;

    private Button adminAddNewUserButton;
    private Button adminBackToMainButton;
    private Button adminBackToViewUsersButton;

    private ActivityAdminAddUserBinding binding;

    private ShopRepository repository;

    private int loggedInUserId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firstNameEditText = findViewById(R.id.editTextAddFirstName);
        lastNameEditText = findViewById(R.id.editTextAddLastName);
        userNameEditText = findViewById(R.id.editTextAddUsername);
        passwordEditText = findViewById(R.id.editTextAddPassword);
        adminBackToMainButton = findViewById(R.id.admin_view_users_button_back_to_main);
        adminBackToViewUsersButton = findViewById(R.id.admin_view_users_button_checkout);



        adminAddNewUserButton = findViewById(R.id.buttonAdminAddUser);

        repository = ShopRepository.getRepository(getApplication());

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
                    startActivity(new Intent(AdminAddUserActivity.this, AdminViewUsersActivity.class));
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
}