package com.example.ktop_food_app.App.model.data.remote;

import com.example.ktop_food_app.App.model.data.entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Lớp FirebasePaymentData chịu trách nhiệm giao tiếp trực tiếp với Firebase Realtime Database
public class FirebasePaymentData {
    private final DatabaseReference databaseReference;

    // Constructor: Khởi tạo tham chiếu đến Firebase Realtime Database với URL cụ thể
    public FirebasePaymentData() {
        databaseReference = FirebaseDatabase.getInstance("https://ktop-food-database-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    }

    // Phương thức lấy thông tin người dùng từ Firebase
    public void loadUserInfo(String userId, OnUserInfoLoadedListener listener) {
        // Truy vấn node users/{userId}/profile để lấy thông tin người dùng
        databaseReference.child("users").child(userId).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy địa chỉ, tên, và số điện thoại từ dữ liệu trả về
                    String address = dataSnapshot.child("address").getValue(String.class);
                    String username = dataSnapshot.child("displayName").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phone").getValue(String.class);
                    // Gọi callback để thông báo thành công và trả về thông tin
                    listener.onUserInfoLoaded(address, username, phoneNumber);
                } else {
                    // Nếu không tìm thấy thông tin, thông báo lỗi
                    listener.onUserInfoError("Không tìm thấy thông tin người dùng");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Nếu truy vấn bị hủy hoặc lỗi, thông báo lỗi
                listener.onUserInfoError("Lỗi khi tải thông tin: " + databaseError.getMessage());
            }
        });
    }

    // Phương thức kiểm tra và áp dụng mã giảm giá từ Firebase
    public void applyVoucherCode(String voucherCode, OnVoucherAppliedListener listener) {
        // Tham chiếu đến node voucher trong Firebase
        DatabaseReference voucherRef = databaseReference.child("voucher");
        voucherRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean voucherFound = false;
                double discount = 0.0;
                // Duyệt qua từng mục trong node voucher
                for (DataSnapshot voucherSnapshot : dataSnapshot.getChildren()) {
                    // Lấy mã giảm giá và giá trị giảm từ dữ liệu
                    String dbVoucherCode = voucherSnapshot.child("code").getValue(String.class);
                    Long dbDiscount = voucherSnapshot.child("discount").getValue(Long.class);
                    // So sánh mã nhập với mã trong database
                    if (dbVoucherCode != null && dbVoucherCode.equals(voucherCode)) {
                        discount = (dbDiscount != null) ? dbDiscount.doubleValue() : 0.0;
                        voucherFound = true;
                        break;
                    }
                }
                // Nếu tìm thấy mã hợp lệ, trả về giá trị giảm giá
                if (voucherFound) {
                    listener.onVoucherApplied(discount);
                } else {
                    // Nếu không tìm thấy, thông báo lỗi
                    listener.onVoucherError("Mã giảm giá không hợp lệ!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Nếu truy vấn bị hủy hoặc lỗi, thông báo lỗi
                listener.onVoucherError("Lỗi khi kiểm tra mã giảm giá: " + databaseError.getMessage());
            }
        });
    }

    // Phương thức xử lý thanh toán và lưu đơn hàng vào Firebase
    public void processPayment(Order order, String userId, OnPaymentProcessedListener listener) {
        String orderId = order.getOrderId();
        // Lưu đơn hàng vào node orders
        databaseReference.child("orders").child(orderId).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    // Xóa giỏ hàng của người dùng sau khi đặt hàng thành công
                    databaseReference.child("users").child(userId).child("cart").removeValue()
                            .addOnSuccessListener(aVoid1 -> listener.onPaymentSuccess())
                            .addOnFailureListener(e -> listener.onPaymentFailure("Lỗi khi xóa giỏ hàng: " + e.getMessage()));
                })
                .addOnFailureListener(e -> listener.onPaymentFailure("Lỗi khi đặt hàng: " + e.getMessage()));
    }

    // Interface callback để thông báo kết quả lấy thông tin người dùng
    public interface OnUserInfoLoadedListener {
        void onUserInfoLoaded(String address, String username, String phoneNumber);
        void onUserInfoError(String error);
    }

    // Interface callback để thông báo kết quả áp dụng mã giảm giá
    public interface OnVoucherAppliedListener {
        void onVoucherApplied(double discount);
        void onVoucherError(String error);
    }

    // Interface callback để thông báo kết quả xử lý thanh toán
    public interface OnPaymentProcessedListener {
        void onPaymentSuccess();
        void onPaymentFailure(String error);
    }
}