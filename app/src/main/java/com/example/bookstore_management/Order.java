package com.example.bookstore_management;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String customerName;
    private double totalPrice;
    private long date; // Lưu thời gian tạo đơn

    public Order(String customerName, double totalPrice, long date) {
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
}
