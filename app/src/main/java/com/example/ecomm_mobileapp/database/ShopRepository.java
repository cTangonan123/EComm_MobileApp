package com.example.ecomm_mobileapp.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Order;
import com.example.ecomm_mobileapp.database.entities.Payment;
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
    private final CartDAO cartDAO;
    private final OrderDAO orderDAO;
    private final PaymentDAO paymentDAO;
    private final LiveData<List<User>> allUsers;
    private final LiveData<List<Product>> allProducts;
    private final LiveData<List<Cart>> allCarts;
    private final LiveData<List<Order>> allOrders;
    private final LiveData<List<Payment>> allPayments;

    private static ShopRepository repository;

    public ShopRepository(Application application) {
        ShopDatabase db = ShopDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.productDAO = db.productDAO();
        this.cartDAO = db.cartDAO();
        this.orderDAO = db.orderDAO();
        this.paymentDAO = db.paymentDAO();
        this.allUsers = userDAO.getAllUsers();
        this.allProducts = productDAO.getAllProducts();
        this.allCarts = cartDAO.getAllCart();
        this.allOrders = orderDAO.getAllOrders();
        this.allPayments = paymentDAO.getAllPayments();



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

    public LiveData<List<Cart>> getAllCarts() {
        return this.allCarts;
    }

    public void insertUser(Cart... cart) {
        ShopDatabase.databaseWriteExecutor.execute(() -> {
            cartDAO.insert(cart);
        });
    }

    public LiveData<List<Order>> getAllOrders() {
        return this.allOrders;
    }

    public void insertUser(Order... order) {
        ShopDatabase.databaseWriteExecutor.execute(() -> {
            orderDAO.insert(order);
        });
    }

    public LiveData<List<Payment>> getAllPayments() {
        return this.allPayments;
    }

    public void insertUser(Payment... payment) {
        ShopDatabase.databaseWriteExecutor.execute(() -> {
            paymentDAO.insert(payment);
        });
    }
}
