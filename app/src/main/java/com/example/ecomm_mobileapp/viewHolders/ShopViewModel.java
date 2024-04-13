package com.example.ecomm_mobileapp.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.User;

import java.util.List;

public class ShopViewModel extends AndroidViewModel {

    private ShopRepository shopRepository;
    private LiveData<List<User>> allUsers;

    public ShopViewModel (Application application) {
        super(application);
        shopRepository = new ShopRepository(application);
   //    allUsers = shopRepository.getUserName();
    }

    LiveData<List<User>> getAllWords() { return allUsers; }

    public void insert(User user) { shopRepository.insert(user); }
}
