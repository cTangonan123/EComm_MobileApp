package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.databinding.ActivityMainBinding;
import com.example.ecomm_mobileapp.databinding.ActivityProductViewBinding;

import org.w3c.dom.Text;

public class ProductViewActivity extends AppCompatActivity {
    TextView textViewProductName;
    TextView textViewProductDescription;
    EditText editTextProductQuantity;

    Button btnBackToStore;
    Button btnAddToCart;

    private int productId;

    private Product currentProduct;

    private int loggedInUserId = 1;

    static final String PRODUCT_ID_KEY = "com.example.ecomm.mobileapp.ProductViewActivity.product.id.key";

    ActivityProductViewBinding binding;
    ShopRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textViewProductName = binding.productviewProductName;
        textViewProductDescription = binding.productviewProductDescription;
        editTextProductQuantity = binding.productviewProductQuantity;
        btnBackToStore = binding.backToStoreButton;
        btnAddToCart = binding.addToCartButton;

        repository = ShopRepository.getRepository(getApplication());
        Intent intent = getIntent();
        productId = intent.getIntExtra(PRODUCT_ID_KEY, 0);

        Log.i(MainActivity.TAG, "This is the product id: " + productId);

        LiveData<Product> productObserver = repository.getProductByProductId(productId);
        productObserver.observe(this, product -> {
            this.currentProduct = product;
            if (product != null) {
                textViewProductName.setText(product.getProductName());
                textViewProductDescription.setText(product.getProductDescription());
            }
        });

        btnBackToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
                finish();
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartToUser();
                Toast.makeText(ProductViewActivity.this, "You have added " + currentProduct.getProductName() + " to your cart!", Toast.LENGTH_SHORT).show();
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addCartToUser() {
        int quantity = Integer.parseInt(String.valueOf(editTextProductQuantity.getText()));
        Cart cart = new Cart(loggedInUserId, currentProduct.getId(), currentProduct.getProductPrice(), quantity);
        repository.insertCart(cart);
    }

    public static Intent productViewActivityIntentFactory(Context context, int productId) {
        Intent intent = new Intent(context, ProductViewActivity.class);
        intent.putExtra(PRODUCT_ID_KEY, productId);
        return intent;
    }
}