package com.example.ktop_food_app.App.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ktop_food_app.App.model.data.entity.CartItem;
import com.example.ktop_food_app.App.model.data.entity.Order;
import com.example.ktop_food_app.databinding.ItemOrderHistoryBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ORDER_HISTORY = 1; // Loại cho Order History
    private static final int VIEW_TYPE_TRACK_ORDER = 2;   // Loại cho Track Order (dự phòng)

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
        decimalFormat = new DecimalFormat("#,###d", symbols);
    }

    @Override
    public int getItemViewType(int position) {
        // Hiện tại chỉ xử lý Order History, luôn trả về VIEW_TYPE_ORDER_HISTORY
        return VIEW_TYPE_ORDER_HISTORY;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Chỉ xử lý VIEW_TYPE_ORDER_HISTORY
        ItemOrderHistoryBinding binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        if (holder instanceof OrderHistoryViewHolder) {
            ((OrderHistoryViewHolder) holder).bind(order);
        }
        // Phần TrackOrder đã bị comment, không cần xử lý ở đây
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);

        void onReorderClick(Order order);
    }

    // ViewHolder cho Order History
    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderHistoryBinding binding;

        public OrderHistoryViewHolder(ItemOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.txtOrderId.setText(order.getOrderId());
            binding.txtOrderStatus.setText(order.getStatus());
            String allProduct = String.valueOf(order.getItems().size());
            binding.totalAllProduct.setText("(" + allProduct + " Products):");
            String totalPriceAllProducts = String.valueOf(order.getTotalPrice());
            binding.totalAllProductPrice.setText(decimalFormat.format(order.getTotalPrice()));

            if (order.getItems() != null && !order.getItems().isEmpty()) {
                CartItem item = order.getItems().get(0);
                Glide.with(context)
                        .load(item.getImagePath())
                        .into(binding.productImage);
                binding.productName.setText(item.getName());
                String quanTiTyOfFirstProduct = String.valueOf(item.getQuantity());
                binding.productQuantity.setText("x " + quanTiTyOfFirstProduct);
            }
            binding.getRoot().setOnClickListener(v -> listener.onOrderClick(order));
            binding.btnReorder.setOnClickListener(v -> listener.onReorderClick(order));
        }
    }

    // ViewHolder cho Track Order (dự phòng, để lại comment cho tương lai)
    /*
    public class TrackOrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemTrackOrderBinding binding;

        public TrackOrderViewHolder(ItemTrackOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.txtOrderId.setText(order.getOrderId());
            binding.txtOrderStatus.setText(order.getStatus());
            String totalPriceAllProducts = String.valueOf(order.getTotalPrice());
            binding.totalAllProductPrice.setText(totalPriceAllProducts + "d");

            if (order.getItems() != null && !order.getItems().isEmpty()) {
                CartItem item = order.getItems().get(0);
                Glide.with(context)
                        .load(item.getImagePath())
                        .into(binding.productImage);
                binding.productName.setText(item.getName());
            }
            binding.getRoot().setOnClickListener(v -> listener.onOrderClick(order));
        }
    }
    */
}