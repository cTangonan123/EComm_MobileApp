package com.example.ecomm_mobileapp;

import static com.example.ecomm_mobileapp.MainActivity.LOGGED_OUT;

import androidx.annotation.NonNull;
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

    private static final String ACTIVITIES_USER_ID = "com.example.ecomm_mobileapp.ACTIVITIES_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";
    public static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;

    TextView textViewProductName;
    TextView textViewProductDescription;
    EditText editTextProductQuantity;

    Button btnBackToStore;
    Button btnAddToCart;
    Button btnLogout;//

    private int productId;

    private Product currentProduct;


    static final String PRODUCT_ID_KEY = "com.example.ecomm.mobileapp.ProductViewActivity.product.id.key";

    ActivityProductViewBinding binding;
    ShopRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductViewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        loginUser(savedInstanceState); //

        textViewProductName = binding.productviewProductName;
        textViewProductDescription = binding.productviewProductDescription;
        editTextProductQuantity = binding.productviewProductQuantity;
        btnBackToStore = binding.backToStoreButton;
        btnAddToCart = binding.addToCartButton;
        btnLogout = binding.produuctviewHeaderLogoutButton;

        repository = ShopRepository.getRepository(getApplication());
        Intent intent = getIntent();
        productId = intent.getIntExtra(PRODUCT_ID_KEY, 0);

        //This binds the username to the textView in the main xml
        SharedPreferences prefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("loggedInUsername", "");

        TextView usernameTextView = findViewById(R.id.textViewUserName);
        usernameTextView.setText(username);
        //

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

        //
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(ProductViewActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = LoginActivity.loginIntentFactory(ProductViewActivity.this, loggedInUserId);
                startActivity(intent);
                finish();
            }
        });
        //

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