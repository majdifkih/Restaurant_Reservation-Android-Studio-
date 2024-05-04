package com.example.reservationrestaurant.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.reservationrestaurant.Interface.ItemClickListener;
import com.example.reservationrestaurant.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView food_name;
    public ImageView food_imag;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);
        food_name = itemView.findViewById(R.id.food_name);
        food_imag = itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
