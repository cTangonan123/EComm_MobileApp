package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.UserDAO;
import com.example.ecomm_mobileapp.database.entities.User;
import com.example.ecomm_mobileapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ShopRepository repository;
//    private ShopDatabase db;
    public static final String TAG = "CRT_SHOP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MainActivity.TAG, "Hello World");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: finish adding holder, adapter, and ... for our Shop Class
        // TODO: Learn how to implement multiple Recycler Views.

        // TODO: create class and intent factory for layout Login
        Button btnLogout = (Button)findViewById(R.id.main_header_logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked the Logout button", Toast.LENGTH_SHORT).show();
            }
        });

        // TODO: create class and intent factory for layout ViewCart
        Button btnViewCart = (Button)findViewById(R.id.main_button_viewcart);
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked the ViewCart button", Toast.LENGTH_LONG).show();
            }
        });


    }


}