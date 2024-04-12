package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

<<<<<<< HEAD
import androidx.annotation.NonNull;
=======
>>>>>>> a195031 (Added product and payment entities, as well as preliminary viewholders. Still needs to be worked.)
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;

<<<<<<< HEAD
public class ShopViewHolder extends RecyclerView.ViewHolder {
    private final TextView productViewItem;
    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        // TODO: change this later for multiple views
        productViewItem = itemView.findViewById(R.id.main_recyclerview_product_name);
    }

    public void bind (String text) {productViewItem.setText(text);}

    static ShopViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_items, parent, false);
=======
public class ShopViewHolder extends RecyclerView.ViewHolder  {
    private final TextView shopItemView;

    private ShopViewHolder(View itemView) {
        super(itemView);
//        shopItemView = itemView.findViewById(R.id.recyclerItemTextview);
    }

    public void bind(String text) {
        shopItemView.setText(text);
    }

    static ShopViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recyclerview_item, parent, false);
>>>>>>> a195031 (Added product and payment entities, as well as preliminary viewholders. Still needs to be worked.)
        return new ShopViewHolder(view);
    }
}
