package com.example.ecomm_mobileapp.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ecomm_mobileapp.database.ShopDatabase;

import java.util.Objects;

@Entity(tableName = ShopDatabase.ORDER_TABLE,
        foreignKeys = @ForeignKey(entity = Cart.class,
                parentColumns = "id",
                childColumns = "cartId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("cartId")})

public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int cartId;

    private double orderTotal;


    public Order(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && cartId == order.cartId && Double.compare(orderTotal, order.orderTotal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, orderTotal);
    }
}