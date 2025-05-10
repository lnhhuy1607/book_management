package com.example.bookstore_management;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface OrderDAO {
    @Insert
    long insertOrder(Order order);

    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Query("SELECT * FROM orders ORDER BY date DESC")
    List<Order> getAllOrders();

    // Đơn hàng có tổng tiền cao nhất
    @Query("SELECT * FROM orders ORDER BY totalPrice DESC LIMIT 1")
    Order getHighestOrder();

    // Thống kê 5 cuốn sách bán chạy nhất
    @Query("SELECT bookId, SUM(quantity) as total FROM order_details GROUP BY bookId ORDER BY total DESC LIMIT 5")
    List<BookStats> getTop5Books();

    // Thống kê 5 khách hàng mua nhiều sách nhất
    @Query("SELECT customerName, SUM(quantity) as total FROM orders INNER JOIN order_details ON orders.id = order_details.orderId GROUP BY customerName ORDER BY total DESC LIMIT 5")
    List<CustomerStats> getTop5Customers();


    @Query("SELECT SUM(od.quantity) AS totalBooksSold, SUM(o.totalPrice) AS totalRevenue " +
            "FROM orders o " +
            "JOIN order_details od ON o.id = od.orderId " +
            "WHERE o.date >= :startOfMonth AND o.date < :endOfMonth")
    MonthlyStats getMonthlyStats(long startOfMonth, long endOfMonth);

}
