package com.example.ecomm_mobileapp.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ecomm_mobileapp.database.ShopRepository;
<<<<<<< HEAD
import com.example.ecomm_mobileapp.database.entities.Product;
=======
import com.example.ecomm_mobileapp.database.entities.User;
>>>>>>> a195031 (Added product and payment entities, as well as preliminary viewholders. Still needs to be worked.)

import java.util.List;

public class ShopViewModel extends AndroidViewModel {
<<<<<<< HEAD
    private ShopRepository repository;

    public ShopViewModel (Application application) {
        super(application);
        repository = ShopRepository.getRepository(application);
    }

    public LiveData<List<Product>> getAllProducts() {
        return repository.getAllProducts();
    }

    public void insertProduct(Product product) {repository.insertProduct(product);}



=======

    private ShopRepository shopRepository;
    private LiveData<List<User>> allUsers;

    public ShopViewModel (Application application) {
        super(application);
        shopRepository = new ShopRepository(application);
   //    allUsers = shopRepository.getUserName();
    }

    LiveData<List<User>> getAllWords() { return allUsers; }

    public void insert(User user) { shopRepository.insert(user); }
>>>>>>> a195031 (Added product and payment entities, as well as preliminary viewholders. Still needs to be worked.)
}
