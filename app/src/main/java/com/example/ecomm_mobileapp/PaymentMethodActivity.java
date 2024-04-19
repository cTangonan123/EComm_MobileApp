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
import com.example.ecomm_mobileapp.database.entities.Payment;
import com.example.ecomm_mobileapp.databinding.ActivityPaymentBinding;
import com.example.ecomm_mobileapp.databinding.ActivityPaymentMethodBinding;

public class PaymentMethodActivity extends AppCompatActivity {

    private EditText cardFirstNameEditText;
    private EditText cardLastNameEditText;
    private EditText cardNumberEditText;
    private EditText cardCVVEditText;
    private Button createPaymentMethod;
    private Button btnBackToMain;

    private ShopRepository repository;

    private int loggedInUserId = 1;

    private ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());

        cardFirstNameEditText = findViewById(R.id.editTextCardFirstName);
        cardLastNameEditText = findViewById(R.id.editTextCardLastName);
        cardNumberEditText = findViewById(R.id.editTextCardNumber);
        cardCVVEditText = findViewById(R.id.editTextCVV);
        createPaymentMethod = findViewById(R.id.buttonAddPaymentMethod);
        btnBackToMain = findViewById(R.id.payment_button_back_to_main);

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
}