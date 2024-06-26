package com.example.ecomm_mobileapp.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomm_mobileapp.R;
import com.example.ecomm_mobileapp.database.ShopRepository;
import com.example.ecomm_mobileapp.database.entities.User;

public class AdminViewUsersViewHolder extends RecyclerView.ViewHolder{
    private final TextView userFirstNameTextView;
    private final TextView userLastNameTextView;
    private final TextView usernameTextView;
    private final TextView passwordTextView;

    private final Button deleteUserButton;
    ShopRepository repository;



    public AdminViewUsersViewHolder(@NonNull View itemView) {
        super(itemView);
        userFirstNameTextView = itemView.findViewById(R.id.admin_viewusers_recyclerview_first_name);
        userLastNameTextView = itemView.findViewById(R.id.admin_viewusers_recyclerview_last_name);
        usernameTextView = itemView.findViewById(R.id.admin_viewusers_recyclerview_username);
        passwordTextView = itemView.findViewById(R.id.admin_viewusers_recyclerview_password);
        deleteUserButton = itemView.findViewById(R.id.admin_viewusers_recyclerview_remove_button);

    }

    public void bind (User user) {
        userFirstNameTextView.setText(user.getFirstName());
        userLastNameTextView.setText(user.getLastName());
        usernameTextView.setText(user.getUserName());
        passwordTextView.setText(user.getPassword());


//        deleteUserButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                repository.deleteUser(user);
//            }
//        });
    }

    static AdminViewUsersViewHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_users_recyclerview_items, parent, false);

        return new AdminViewUsersViewHolder(view);
    }
}
