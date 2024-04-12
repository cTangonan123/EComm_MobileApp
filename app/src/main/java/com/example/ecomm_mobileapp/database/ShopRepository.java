package com.example.ecomm_mobileapp.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ShopRepository {
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private LiveData<List<User>> allUsers;
    private LiveData<List<Product>> allProducts;

    private static ShopRepository repository;

    public ShopRepository(Application application) {
        ShopDatabase db = ShopDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.productDAO = db.productDAO();
        this.allUsers = userDAO.getAllUsers();
        this.allProducts = productDAO.getAllProducts();


    }



    public LiveData<List<User>> getAllUsers() {
        return this.allUsers;
    }

    public static ShopRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<ShopRepository> future = ShopDatabase.databaseWriteExecutor.submit(
                new Callable<ShopRepository>() {
                    @Override
                    public ShopRepository call() throws Exception {
                        repository = new ShopRepository(application);
                        return null;
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting Shop Repository, thread error");
        }
        return null;
    }

    public void insertUser(User... user) {
        ShopDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }


    public LiveData<List<Product>> getAllProducts() {
        return this.allProducts;
    }

    public void insertProduct(Product... product) {
        ShopDatabase.databaseWriteExecutor.execute(() -> {
            productDAO.insert(product);
        });
    }
}
