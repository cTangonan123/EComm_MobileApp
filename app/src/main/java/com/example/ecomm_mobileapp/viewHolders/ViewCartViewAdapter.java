package com.example.ecomm_mobileapp.viewHolders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.GnssAntennaInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.ecomm_mobileapp.MainActivity;
import com.example.ecomm_mobileapp.ProductViewActivity;
import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.ViewCartActivity;
import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.comboHolders.ProductAndCart;
import com.example.ecomm_mobileapp.database.entities.Cart;
import com.example.ecomm_mobileapp.database.entities.Product;

import java.util.List;
import java.util.Locale;

public class ViewCartViewAdapter extends ListAdapter<Product, ViewCartViewHolder> {
    List<Product> products;

    public ViewCartViewAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
        products = getCurrentList();

    }



    @NonNull
    @Override
    public ViewCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewCartViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCartViewHolder holder, int position) {
        Product current = getItem(position);

//        TextView productNameTextView = holder.itemView.findViewById(R.id.viewcart_recyclerview_product_name);
//        TextView productPriceTextView = holder.itemView.findViewById(R.id.viewcart_recyclerview_product_price);
//        productNameTextView.setText(current.getProductName());
//        productPriceTextView.setText(String.format(Locale.US,"$%,.2f", current.getProductPrice()));

        holder.bind(current);

        Log.i(MainActivity.TAG, "position: " + position);
    }

    public static class ShopDiff extends DiffUtil.ItemCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
