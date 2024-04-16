package com.example.ecomm_mobileapp.database.comboHolders;

import androidx.room.Entity;

import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.Objects;

@Entity
public class ProductAndCart {
    private Product product;
    private Cart cart;

    public ProductAndCart(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAndCart that = (ProductAndCart) o;
        return Objects.equals(product, that.product) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, cart);
    }

    @Override
    public String toString() {
        return "ProductAndCart{" +
                "product=" + product +
                ", cart=" + cart +
                '}';
    }
}
