package com.example.ecomm_mobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.databinding.ActivityPaymentMethodBinding;

public class PaymentMethodActivity extends AppCompatActivity {

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;

    private EditText cardFirstNameEditText;
    private EditText cardLastNameEditText;
    private EditText cardNumberEditText;
    private EditText cardCVVEditText;
    private Button createPaymentMethod;
    private Button btnBackToMain;

    private Button btnLogout;

    private ShopRepository repository;

    private ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_payment_method);
        loginUser(savedInstanceState);

        cardFirstNameEditText = findViewById(R.id.editTextCardFirstName);
        cardLastNameEditText = findViewById(R.id.editTextCardLastName);
        cardNumberEditText = findViewById(R.id.editTextCardNumber);
        cardCVVEditText = findViewById(R.id.editTextCVV);
        createPaymentMethod = findViewById(R.id.buttonAddPaymentMethod);
        btnBackToMain = findViewById(R.id.payment_button_back_to_main);
        btnLogout = binding.paymentHeaderLogoutButton;

        repository = ShopRepository.getRepository(getApplication());

        createPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardFirstName = cardFirstNameEditText.getText().toString();
                String cardLastName = cardLastNameEditText.getText().toString();
                String cardNumber = cardNumberEditText.getText().toString();
                String cardCVV = cardCVVEditText.getText().toString();

                if (cardFirstName.isEmpty() || cardLastName.isEmpty() || cardNumber.isEmpty() || cardCVV.isEmpty()) {
                    Toast.makeText(PaymentMethodActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidCardNumber(cardNumber, cardCVV)) {
                    Toast.makeText(PaymentMethodActivity.this, "Please enter a valid card", Toast.LENGTH_SHORT).show();
                    return;
                }

                savePaymentInfo(cardFirstName, cardLastName, cardNumber, cardCVV, loggedInUserId);

                Toast.makeText(PaymentMethodActivity.this, "Payment method added successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
                finish();
                logout();
            }
        });
    }

    private boolean isValidCardNumber(String cardNumber, String cvv) {
        return cardNumber != null && cardNumber.length() == 16 && cvv != null && cvv.length() == 3;
    }
    private void savePaymentInfo(String cardFirstName, String cardLastName, String cardNumber, String cardCVV, int userId) {
        Payment payment = new Payment(cardFirstName, cardLastName, cardNumber, cardCVV, userId);
        repository.insertPayment(payment);

        Intent paymentIntent = PaymentActivity.paymentIntentFactory(getApplicationContext(), loggedInUserId);
        startActivity(paymentIntent);
    }

    static Intent paymentMethodIntentFactory(Context context, int userId) {
        return new Intent(context, PaymentMethodActivity.class);
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