package com.example.ktop_food_app.App.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ktop_food_app.App.model.data.entity.CartItem;
import com.example.ktop_food_app.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class OrderHistoryDetailAdapter extends RecyclerView.Adapter<OrderHistoryDetailAdapter.OrderHistoryDetailViewHolder> {

    private final Context context;
    private final List<CartItem> itemList;
    private final DecimalFormat decimalFormat;

    public OrderHistoryDetailAdapter(Context context, List<CartItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat("#,###d", symbols);
    }

    @NonNull
    @Override
    public OrderHistoryDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history_detail, parent, false);
        return new OrderHistoryDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryDetailViewHolder holder, int position) {
        CartItem item = itemList.get(position);

        // Set item name
        holder.tvItemName.setText(item.getName() != null ? item.getName() : "Unknown Product");

        // Set quantity
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        // Set price and total price
        holder.tvPrice.setText(decimalFormat.format(item.getPrice()));
        holder.tvItemTotal.setText(decimalFormat.format(item.getTotalPrice()));

        // Load image using Glide
        Glide.with(context)
                .load(item.getImagePath())
                .placeholder(R.drawable.bingsuberry)
                .error(R.drawable.bingsuberry)
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    static class OrderHistoryDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView tvItemName, tvQuantity, tvPrice, tvItemTotal;

        public OrderHistoryDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.food_image_view);
            tvItemName = itemView.findViewById(R.id.food_title_text_view);
            tvQuantity = itemView.findViewById(R.id.quantity_text_view);
            tvPrice = itemView.findViewById(R.id.price_text_view);
            tvItemTotal = itemView.findViewById(R.id.total_item_price_text_view);
        }
    }
}