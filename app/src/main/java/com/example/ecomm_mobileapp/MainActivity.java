package com.example.ecomm_mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.ecomm_mobileapp.viewHolders.ShopAdapter;
import com.example.ecomm_mobileapp.viewHolders.ShopViewHolder;
import com.example.ecomm_mobileapp.viewHolders.ShopViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.ecomm_mobileapp.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.ecomm_mobileapp.SAVED_INSTANCE_USER_ID_KEY";

    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.ecomm_mobileapp.SHARED_PREFERENCE_USERID_KEY";
    private ActivityMainBinding binding;
    private ShopRepository repository;

    private ShopViewModel shopViewModel;
//    private ShopDatabase db;

    private final int loggedInUserId = 1;

    public static final String TAG = "CRT_SHOP";

    private int loggedInUserId = -1;
    private SharedPreferences prefs = null;
    private UserDAO userDao;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loginUser(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Log.i(MainActivity.TAG, "Hello World");

        repository = ShopRepository.getRepository(getApplication());

        getDatabase();

        getPrefs();

        checkForLoggedInUser();

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        RecyclerView recyclerView = binding.mainRecyclerviewItems;
        final ShopAdapter adapter = new ShopAdapter(new ShopAdapter.ShopDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shopViewModel.getAllProducts().observe(this, products -> {
            adapter.submitList(products);
        });


        // TODO: finish adding holder, adapter, and ... for our Shop Class
        // TODO: Learn how to implement multiple Recycler Views.

        // TODO: create class and intent factory for loginActivity
        Button btnLogout = (Button) findViewById(R.id.main_header_logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();

                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                Intent intent = LoginActivity.loginIntentFactory(MainActivity.this);
                startActivity(intent);
                finish();
            }
        });

        // TODO: create class and intent factory for viewCartActivity
        Button btnViewCart = (Button)findViewById(R.id.main_button_viewcart);
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked the ViewCart button", Toast.LENGTH_LONG).show();
                startActivity(ViewCartActivity.viewCartActivityIntentFactory(getApplicationContext(), loggedInUserId) );
            }
        });
    }

    private void logoutUser() {
            loggedInUserId = LOGGED_OUT;
            updateSharedPreference();
            getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

            startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedpreference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    private void loginUser(Bundle savedInstanceState) {
        // Check SharedPreferences for logged-in user ID
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedpreference_file_key), Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);

        // Check if logged-in user ID is saved in savedInstanceState
        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        // Check if logged-in user ID is passed from MainActivity
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        // If the user is not logged in, return
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }

        // Observe the LiveData to update UI when user data changes
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user; // added for EC
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }

    private boolean redirectionOccurred = false;

    private void checkForLoggedInUser() {
        // Check if logged in user ID is passed from MainActivity
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        // If the user ID is already passed from MainActivity or found in SharedPreferences, redirect to MainActivity
        if (loggedInUserId != LOGGED_OUT || prefs != null && prefs.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT) != LOGGED_OUT) {
            redirectToMainActivity();
            return;
        }

        // If not found in SharedPreferences, check the Room database for existing users
        LiveData<List<User>> usersLiveData = userDao.getAllUsers();
        usersLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users.size() <= 0) {
                    User admin = new User("admin1", "admin1", true, "Admin", "AdminLastName");
                    userDao.insert(admin);
                    User testUser1 = new User("testUser1", "testUser1", false, "User", "UserLastName");
                    userDao.insert(testUser1);
                }

                // Redirect to MainActivity if not already redirected
                if (!redirectionOccurred) {
                    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                    finish();
                    redirectionOccurred = true;
                }
            }
        });
    }

    private void redirectToMainActivity() {
        Intent mainIntent = MainActivity.createMainActivityIntent(this);
        startActivity(mainIntent);
        finish(); // Finish the LoginActivity to prevent going back
    }


    private void addUserToPreference(int userId) {
        if (prefs == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MAIN_ACTIVITY_USER_ID, userId);
        editor.apply();
    }

    private void getDatabase() {
        userDao = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .userDAO();
    }

    private void getPrefs() {
        prefs = this.getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
    }

    public static Intent createMainActivityIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    static Intent mainActivityIntentFactory(Context context, String username) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Add flags to clear the activity stack
        return intent;
    }
}


