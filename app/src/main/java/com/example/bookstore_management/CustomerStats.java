package com.example.bookstore_management;

import android.content.Context;

import java.util.Calendar;

public class CustomerStats {
    public String customerName;
    public int total;

    public static MonthlyStats getMonthlyStats(Context context) {
        BookDatabase db = BookDatabase.getDatabase(context);
        Calendar calendar = Calendar.getInstance();

        // Lấy thời gian đầu tháng (00:00 ngày 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startOfMonth = calendar.getTimeInMillis();

        // Lấy thời gian cuối tháng (23:59 ngày cuối cùng)
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        long endOfMonth = calendar.getTimeInMillis();

        return db.orderDao().getMonthlyStats(startOfMonth, endOfMonth);
    }
}
