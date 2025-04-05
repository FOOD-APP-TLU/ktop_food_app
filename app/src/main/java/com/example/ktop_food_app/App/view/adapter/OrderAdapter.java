package com.example.ktop_food_app.App.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ktop_food_app.App.model.data.entity.CartItem;
import com.example.ktop_food_app.App.model.data.entity.Order;
import com.example.ktop_food_app.databinding.ItemTrackOrdersBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;
    private final Context context;
    private final OnOrderClickListener listener;
    private final DecimalFormat decimalFormat;

    public OrderAdapter(Context context, List<Order> orderList, OnOrderClickListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat("#,###", symbols);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrackOrdersBinding binding = ItemTrackOrdersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemTrackOrdersBinding binding;

        public OrderViewHolder(ItemTrackOrdersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.txtOrderId.setText(order.getOrderId());
            binding.txtOrderStatus.setText(order.getStatus());
            String allProduct = String.valueOf(order.getItems().size());
            binding.totalAllProduct.setText("(" + allProduct+ " " + "Products"+"):");
            binding.totalAllProductPrice.setText(decimalFormat.format(order.getTotalPrice()) + " d");

            if(order.getItems() != null && !order.getItems().isEmpty()) {
                CartItem item = order.getItems().get(0);
                // Load ảnh của sản phẩm đầu tiên vào ImageView
                Glide.with(context)
                        .load(item.getImagePath())
                        .into(binding.productFirstImage);
                binding.productName.setText(item.getName());
                String quanTiTyOfFirstProduct = String.valueOf(item.getQuantity());
                binding.productQuantity.setText("x" + quanTiTyOfFirstProduct);
            }

            // Xử lý sự kiện click item
            binding.getRoot().setOnClickListener(v -> listener.onOrderClick(order));
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }
}
