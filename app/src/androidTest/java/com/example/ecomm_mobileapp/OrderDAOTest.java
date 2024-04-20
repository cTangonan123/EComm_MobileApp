package com.example.ecomm_mobileapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.OrderDAO;
import com.example.ecomm_mobileapp.database.entities.Order;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderDAOTest {
    private ShopDatabase database;
    private OrderDAO orderDAO;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        ShopDatabase.class)
                .allowMainThreadQueries()
                .build();

        orderDAO = database.orderDAO();
    }

    @After
    public void cleanup() {
        database.close();
    }

    @Test
    public void deleteOrder() throws InterruptedException {
        Order order = new Order(100.0);
        orderDAO.insert(order);

        // Delete the order
        orderDAO.delete(order);

        List<Order> orders = LiveDataTestUtil.getValue(orderDAO.getAllOrders());


        assertFalse(orders.isEmpty());
    }

}