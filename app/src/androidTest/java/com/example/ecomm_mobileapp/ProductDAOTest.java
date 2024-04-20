package com.example.ecomm_mobileapp;

import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.ecomm_mobileapp.database.ProductDAO;
import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.entities.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProductDAOTest {
    private ShopDatabase database;
    private ProductDAO productDAO;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        ShopDatabase.class)
                .allowMainThreadQueries()
                .build();

        productDAO = database.productDAO();
    }

    @After
    public void cleanup() {
        database.close();
    }

    @Test
    public void deleteProduct() {
        Product product = new Product("TV", "description of TV", 4.99);
        productDAO.insert(product);

        productDAO.delete(product);

        Product deletedProduct = productDAO.getProductById(product.getId()).getValue();
        assertNull(deletedProduct);
    }

}