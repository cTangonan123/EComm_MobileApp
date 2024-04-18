package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.CartDAO;
import com.example.ecomm_mobileapp.database.ShopDatabase;
import com.example.ecomm_mobileapp.database.comboHolders.ProductAndCart;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.Locale;

public class ViewCartViewHolder extends RecyclerView.ViewHolder {
    private final TextView productNameTextView;
    private final TextView productPriceTextView;
    private final TextView productQuantityTextView;

    private final Button productRemoveFromCartButton;

    public ViewCartViewHolder(@NonNull View itemView) {
        super(itemView);



        productNameTextView = itemView.findViewById(R.id.viewcart_recyclerview_product_name);
        productPriceTextView = itemView.findViewById(R.id.viewcart_recyclerview_product_price);
        // TODO: need to pull quantity from the cart
        productQuantityTextView = itemView.findViewById(R.id.viewcart_recyclerview_product_quantity);
        productRemoveFromCartButton = itemView.findViewById(R.id.viewcart_recyclerview_product_btn_remove);
    }

    public void bind (Product product) {
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(String.format(Locale.US,"$%,.2f", product.getProductPrice()));
//        productQuantityTextView.setText(String.format(Locale.US, "Qty: %", product.getCart().getCartQuantity()));

        productRemoveFromCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: must delete cart from the cart table






            }
        });
    }

    static ViewCartViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcart_recyclerview_items, parent, false);
        return new ViewCartViewHolder(view);
    }
}
