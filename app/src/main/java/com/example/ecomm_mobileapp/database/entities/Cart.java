package com.example.ecomm_mobileapp.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ecomm_mobileapp.database.ShopDatabase;

import java.util.Objects;

@Entity(tableName = ShopDatabase.CART_TABLE,
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Product.class,
                        parentColumns = "id",
                        childColumns = "productId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("userId"), @Index("productId")})

public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int productId;
    private double cartPrice;
    private int cartQuantity;

    public Cart(double cartPrice, int cartQuantity) {
        this.cartPrice = cartPrice;
        this.cartQuantity = cartQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(double cartPrice) {
        this.cartPrice = cartPrice;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id && userId == cart.userId && productId == cart.productId && Double.compare(cartPrice, cart.cartPrice) == 0 && cartQuantity == cart.cartQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, cartPrice, cartQuantity);
    }
}
