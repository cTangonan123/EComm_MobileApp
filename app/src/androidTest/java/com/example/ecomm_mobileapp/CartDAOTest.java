package com.example.ecomm_mobileapp;

import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.ecomm_mobileapp.database.CartDAO;
import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.entities.Cart;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CartDAOTest {
    private ShopDatabase database;
    private CartDAO cartDAO;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        ShopDatabase.class)
                .allowMainThreadQueries()
                .build();

        cartDAO = database.cartDAO();
    }

    @After
    public void cleanup() {
        database.close();
    }

    @Test
    public void deleteCart() {
        Cart cart = new Cart(1, 1, 5, 3);
        cartDAO.insert(cart);

        cartDAO.delete(cart);

        Cart deletedCart = cartDAO.getCartFromProductIdAndUserId(1, 1).getValue();
        assertNull(deletedCart);
    }

}