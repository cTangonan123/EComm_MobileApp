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

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.database.entities.User;
import com.example.ecomm_mobileapp.databinding.ActivityAdminAddItemsBinding;
import com.example.ecomm_mobileapp.databinding.ActivityAdminAddUserBinding;
import com.example.ecomm_mobileapp.databinding.ActivityAdminViewItemsBinding;

public class AdminAddItemsActivity extends AppCompatActivity {

    private int loggedInUserId = 1;

    private EditText productNameEditText;
    private EditText productDescriptionEditText;
    private EditText productPriceEditText;
    private Button addItemButton;
    private Button backToStoreButton;

    private ActivityAdminAddItemsBinding binding;

    private ShopRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productNameEditText = findViewById(R.id.editText_admin_product_name);
        productDescriptionEditText = findViewById(R.id.editText_admin_product_description);
        productPriceEditText = findViewById(R.id.editText_admin_product_price);
        addItemButton = findViewById(R.id.buttonAddItem);
        backToStoreButton = binding.backToStoreButton;

        repository = ShopRepository.getRepository(getApplication());

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameEditText.getText().toString();
                String productDescription = productDescriptionEditText.getText().toString();
                String productPrice = productPriceEditText.getText().toString();

                if (productName.isEmpty() || productDescription.isEmpty() || productPrice.isEmpty()) {
                    Toast.makeText(AdminAddItemsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double productPrices = Double.parseDouble(productPrice);

                saveProductInformation(productName, productDescription, productPrices);

                Toast.makeText(AdminAddItemsActivity.this, "New Product Added", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AdminAddItemsActivity.this, AdminViewItemsActivity.class));
                finish();
            }
        });

        backToStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });
    }


    private void saveProductInformation(String productName, String productDescription, Double productPrice) {
        Product product = new Product(productName, productDescription, productPrice);
        repository.insertProduct(product);
    }

    static Intent adminAddItemsIntentFactory(Context context, int userId) {
        Intent intent =  new Intent(context, AdminAddItemsActivity.class);
        return intent;
    }
}