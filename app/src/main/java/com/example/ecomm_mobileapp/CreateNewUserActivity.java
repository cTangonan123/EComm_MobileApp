package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.User;

public class CreateNewUserActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText1;
    private EditText passwordEditText2;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button createNewUserButton;

    private ShopRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        userNameEditText = findViewById(R.id.editTextNewUsername);
        passwordEditText1 = findViewById(R.id.editTextNewPassword1);
        passwordEditText2 = findViewById(R.id.editTextNewPassword2);
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        createNewUserButton = findViewById(R.id.buttonCreateNewAccount);

        repository = ShopRepository.getRepository(getApplication());

        createNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameEditText.getText().toString();
                String password1 = passwordEditText1.getText().toString();
                String password2 = passwordEditText2.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();

                if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(CreateNewUserActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (!passwordsMatch(password1, password2)) {
                    Toast.makeText(CreateNewUserActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveUserInfo(username, password1, firstName, lastName);

                Toast.makeText(CreateNewUserActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(CreateNewUserActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean passwordsMatch(String password1, String password2) {
        return password1.equals(password2);
    }

    private void saveUserInfo(String username, String password, String firstName, String lastName) {
        User user = new User(username, password, false, firstName, lastName); // Include first and last name in User creation
        repository.insertUser(user);
    }

    public static Intent createNewUserIntentFactory(Context context) {
        return new Intent(context, CreateNewUserActivity.class);
    }
}