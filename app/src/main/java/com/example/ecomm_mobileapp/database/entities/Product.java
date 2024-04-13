package com.example.ecomm_mobileapp.database.entities;

import androidx.annotation.NonNull;

@Entity(tableName = "product_table")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int productID;

    @NonNull
    private String productName;
    @NonNull
    private String productDescription;

    @NonNull
    private String productImage;
    @NonNull
    private double price;

    //Constructor
    public Product(@NonNull String productName, @NonNull String productDescription, @NonNull String productImage, double price) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    @NonNull
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(@NonNull String productDescription) {
        this.productDescription = productDescription;
    }

    @NonNull
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(@NonNull String productImage) {
        this.productImage = productImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
