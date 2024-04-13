package com.example.ecomm_mobileapp.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.Product;
import com.example.ecomm_mobileapp.database.entities.User;

import java.util.List;

public class ShopViewModel extends AndroidViewModel {
    private ShopRepository repository;

    public ShopViewModel(Application application) {
        super(application);
        repository = ShopRepository.getRepository(application);
    }

    public LiveData<List<Product>> getAllProducts() {
        return repository.getAllProducts();
    }

    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }

}
