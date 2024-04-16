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

public class PaymentMethodActivity extends AppCompatActivity {

    private EditText cardFirstNameEditText;
    private EditText cardLastNameEditText;
    private EditText cardNumberEditText;
    private EditText cardCVVEditText;
    private Button createPaymentMethod;

    private ShopRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        cardFirstNameEditText = findViewById(R.id.editTextCardFirstName);
        cardLastNameEditText = findViewById(R.id.editTextCardLastName);
        cardNumberEditText = findViewById(R.id.editTextCardNumber);
        cardCVVEditText = findViewById(R.id.editTextCVV);
        createPaymentMethod = findViewById(R.id.buttonAddPaymentMethod);

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

                if (!isValidCardNumber(cardNumber)) {
                    Toast.makeText(PaymentMethodActivity.this, "Please enter a valid 16-digit card number", Toast.LENGTH_SHORT).show();
                    return;
                }

                savePaymentInfo(cardFirstName, cardLastName, cardNumber, cardCVV);

                Toast.makeText(PaymentMethodActivity.this, "Payment method added successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(PaymentMethodActivity.this, Payment.class));
                finish();
            }
        });
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.length() == 16;
    }
    private void savePaymentInfo(String cardFirstName, String cardLastName, String cardNumber, String cardCVV) {
        Payment payment = new Payment(cardFirstName, cardLastName, cardNumber, cardCVV);
        repository.insertPayment(payment);

        Intent paymentIntent = createPaymentMethodIntentFactory(PaymentMethodActivity.this, cardFirstName, cardLastName, cardNumber);
        startActivity(paymentIntent);
    }

    public static Intent createPaymentMethodIntentFactory(Context context, String firstName, String lastName, String cardNumber) {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("cardNumber", cardNumber);
        return intent;
    }
}