package com.example.ecomm_mobileapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ecomm_mobileapp.database.entities.User;

import java.util.List;

public class ShopRepository {
    private UserDAO userDAO;
//    private LiveData<List<User>> allUsers;

    ShopRepository(Application application) {
        ShopDatabase db = ShopDatabase.getDatabase(application);
        this.userDAO = db.userDAO();

    }

    void insert(User user) {
        ShopDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }
}
