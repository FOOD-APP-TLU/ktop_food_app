package com.example.ktop_food_app.App.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktop_food_app.App.model.Entity.CartItem;
import com.example.ktop_food_app.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;
    private OnTotalPriceChangedListener totalPriceChangedListener;

    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(long totalPrice);
    }

    // Thêm interface mới để xử lý khi item bị xóa
    public interface OnItemRemovedListener {
        void onItemRemoved(int position);
    }

    private OnItemRemovedListener itemRemovedListener;

    public void setOnItemRemovedListener(OnItemRemovedListener listener) {
        this.itemRemovedListener = listener;
    }

    public CartAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
        if (context instanceof OnTotalPriceChangedListener) {
            this.totalPriceChangedListener = (OnTotalPriceChangedListener) context;
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText(String.format("%d * %,d đ", item.getQuantity(), item.getPrice()));
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        holder.totalPriceTextView.setText(String.format("%,d đ", item.getTotalPrice()));
        holder.productImageView.setImageResource(item.getImageResource());

        holder.decreaseButton.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                updateTotalPrice();
            } else {
                // Khi số lượng giảm xuống 0, xóa item
                removeItem(position);
            }
        });

        holder.increaseButton.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            updateTotalPrice();
        });
    }

    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            notifyItemRemoved(position);
            // Cập nhật lại vị trí của các item sau khi xóa
            notifyItemRangeChanged(position, cartItems.size());
            // Cập nhật tổng giá
            updateTotalPrice();

            // Thông báo cho activity biết item đã bị xóa
            if (itemRemovedListener != null) {
                itemRemovedListener.onItemRemoved(position);
            }
        }
    }

    private void updateTotalPrice() {
        long totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }

        // Notify the activity about the price change
        if (totalPriceChangedListener != null) {
            totalPriceChangedListener.onTotalPriceChanged(totalPrice);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView nameTextView, priceTextView, quantityTextView, totalPriceTextView;
        ImageButton decreaseButton, increaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            nameTextView = itemView.findViewById(R.id.product_name);
            priceTextView = itemView.findViewById(R.id.product_price);
            quantityTextView = itemView.findViewById(R.id.quantity_text);
            totalPriceTextView = itemView.findViewById(R.id.total_price);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            increaseButton = itemView.findViewById(R.id.increase_button);
        }
    }
}
