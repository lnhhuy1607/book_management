package com.example.bookstore_management;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {
    private TextView tvHighestOrder, tvTopBooks, tvMonthlyStats;
    private BookDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvHighestOrder = findViewById(R.id.tvHighestOrder);
        tvTopBooks = findViewById(R.id.tvTopBooks);
        tvMonthlyStats = findViewById(R.id.tvMonthlyStats); // Thay thế thống kê khách hàng bằng thống kê tháng
        db = BookDatabase.getDatabase(this);

        loadStatistics();
    }

    private void loadStatistics() {
        new Thread(() -> {
            Order highestOrder = db.orderDao().getHighestOrder();
            List<BookStats> topBooks = db.orderDao().getTop5Books();
            MonthlyStats monthlyStats = db.orderDao().getMonthlyStats(getStartOfMonth(), getEndOfMonth());

            runOnUiThread(() -> {
                tvHighestOrder.setText("Đơn hàng giá trị cao nhất: " + (highestOrder != null ? highestOrder.getTotalPrice() : "N/A"));

                StringBuilder bookStatsText = new StringBuilder("Top 5 sách bán chạy:\n");
                for (BookStats b : topBooks) {
                    bookStatsText.append("ID: ").append(b.bookId).append(" - Số lượng: ").append(b.total).append("\n");
                }
                tvTopBooks.setText(bookStatsText.toString());

                if (monthlyStats != null) {
                    tvMonthlyStats.setText("Thống kê tháng:\nTổng số sách đã bán: " + monthlyStats.totalBooksSold
                            + "\nTổng doanh thu: " + monthlyStats.totalRevenue + " VNĐ");
                } else {
                    tvMonthlyStats.setText("Chưa có dữ liệu trong tháng này.");
                }
            });
        }).start();
    }

    private long getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long getEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTimeInMillis();
    }
}
