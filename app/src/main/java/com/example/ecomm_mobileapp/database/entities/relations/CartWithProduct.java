package com.example.ecomm_mobileapp.database.entities.relations;

import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.Objects;

public class CartWithProduct {
    private Cart cart;
    private Product product;

    public CartWithProduct(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartWithProduct that = (CartWithProduct) o;
        return Objects.equals(cart, that.cart) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, product);
    }

    @Override
    public String toString() {
        return "CartWithProduct{" +
                "cart=" + cart.toString() +
                ", product=" + product.toString() +
                '}';
    }
}
