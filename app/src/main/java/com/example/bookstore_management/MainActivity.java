package com.example.bookstore_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnManageBooks, btnManageOrders, btnStatistics, btnCreateOrder,btnDeleteBook, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnManageBooks = findViewById(R.id.btnManageBooks);
        btnManageOrders = findViewById(R.id.btnManageOrders);
        btnStatistics = findViewById(R.id.btnStatistics);

        btnManageBooks.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, BookListActivity.class)));

        btnManageOrders.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, OrderListActivity.class)));

        btnStatistics.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, com.example.bookstore_management.StatisticsActivity.class)));


        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        btnCreateOrder.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, CreateOrderActivity.class)));

        btnDeleteBook = findViewById(R.id.btnDeleteBook);
        btnDeleteBook.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, DeleteBookActivity.class)));

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }


}
