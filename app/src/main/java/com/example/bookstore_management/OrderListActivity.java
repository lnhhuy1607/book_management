package com.example.bookstore_management;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    private ListView lvOrders;
    private BookDatabase db;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        lvOrders = findViewById(R.id.lvOrders);
        db = BookDatabase.getDatabase(this);

        loadOrders();
    }

    private void loadOrders() {
        new Thread(() -> {
            orderList = db.orderDao().getAllOrders();
            runOnUiThread(() -> {
                OrderAdapter adapter = new OrderAdapter(this, orderList);
                lvOrders.setAdapter(adapter);
            });
        }).start();
    }
}
