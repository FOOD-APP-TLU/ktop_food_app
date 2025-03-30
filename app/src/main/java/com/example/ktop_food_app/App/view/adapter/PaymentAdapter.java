package com.example.ktop_food_app.App.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.ktop_food_app.App.model.data.entity.PaymentItem;
import com.example.ktop_food_app.R;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<PaymentItem> paymentItems;

    public PaymentAdapter(List<PaymentItem> paymentItems) {
        this.paymentItems = paymentItems;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentItem paymentItem = paymentItems.get(position);

        // Gán dữ liệu vào các view
        holder.foodTitleTextView.setText(paymentItem.getFoodTitle());
        holder.priceTextView.setText(String.format("%,.0f đ", paymentItem.getPrice()));
        holder.quantityTextView.setText(String.valueOf(paymentItem.getQuantity()));
        holder.totalItemPriceTextView.setText(String.format("%,.0f đ", paymentItem.getTotalItemPrice()));

        // Tải hình ảnh bằng Glide nếu có (giả sử imagePath là URL hoặc đường dẫn hợp lệ)
        if (paymentItem.getImagePath() != null && !paymentItem.getImagePath().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(paymentItem.getImagePath())
                    .placeholder(R.drawable.img_user) // Hình ảnh placeholder khi đang tải
                    .error(R.drawable.img_user) // Hình ảnh khi tải lỗi
                    .into(holder.foodImageView);
        } else {
            holder.foodImageView.setImageResource(R.drawable.img_user); // Hình mặc định nếu không có ảnh
        }
    }

    @Override
    public int getItemCount() {
        return paymentItems != null ? paymentItems.size() : 0;
    }

    // ViewHolder để giữ các view của mỗi item
    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView foodTitleTextView, priceTextView, quantityTextView, totalItemPriceTextView;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.food_image_view);
            foodTitleTextView = itemView.findViewById(R.id.food_title_text_view);
            priceTextView = itemView.findViewById(R.id.price_text_view);
            quantityTextView = itemView.findViewById(R.id.quantity_text_view);
            totalItemPriceTextView = itemView.findViewById(R.id.total_item_price_text_view);
        }
    }
}