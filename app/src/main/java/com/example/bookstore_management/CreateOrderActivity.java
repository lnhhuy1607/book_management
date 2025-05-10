package com.example.bookstore_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderActivity extends AppCompatActivity {
    private EditText edtCustomerName, edtQuantity;
    private Spinner spnBook;
    private Button btnAddToOrder, btnSaveOrder;
    private TextView tvTotalPrice;
    private ListView lvOrderDetails;

    private BookDatabase db;
    private List<Book> bookList;
    private List<OrderDetail> orderDetails = new ArrayList<>();
    private ArrayAdapter<String> bookAdapter, orderAdapter;
    private double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtQuantity = findViewById(R.id.edtQuantity);
        spnBook = findViewById(R.id.spnBook);
        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        btnSaveOrder = findViewById(R.id.btnSaveOrder);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        lvOrderDetails = findViewById(R.id.lvOrderDetails);

        db = BookDatabase.getDatabase(this);
        loadBooks();

        btnAddToOrder.setOnClickListener(view -> addToOrder());
        btnSaveOrder.setOnClickListener(view -> saveOrder());
    }

    private void loadBooks() {
        new Thread(() -> {
            bookList = db.bookDao().getAllBooks();
            runOnUiThread(() -> {
                List<String> bookTitles = new ArrayList<>();
                for (Book book : bookList) {
                    bookTitles.add(book.getTitle() + " (Còn: " + book.getQuantity() + ")");
                }
                bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookTitles);
                bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnBook.setAdapter(bookAdapter);
            });
        }).start();
    }

    private void addToOrder() {
        int position = spnBook.getSelectedItemPosition();
        int quantity = Integer.parseInt(edtQuantity.getText().toString());

        if (position < 0 || quantity <= 0) return;

        Book selectedBook = bookList.get(position);
        if (quantity > selectedBook.getQuantity()) {
            edtQuantity.setError("Số lượng vượt quá tồn kho!");
            return;
        }

        double itemTotal = selectedBook.getPrice() * quantity;
        totalPrice += itemTotal;
        tvTotalPrice.setText("Tổng tiền: " + totalPrice + " VNĐ");

        orderDetails.add(new OrderDetail(0, selectedBook.getId(), quantity, itemTotal));
        updateOrderList();
    }

    private void updateOrderList() {
        List<String> orderSummary = new ArrayList<>();
        for (OrderDetail detail : orderDetails) {
            orderSummary.add("Sách ID: " + detail.getBookId() + " - Số lượng: " + detail.getQuantity());
        }
        orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderSummary);
        lvOrderDetails.setAdapter(orderAdapter);
    }

    private void saveOrder() {
        String customerName = edtCustomerName.getText().toString().trim();
        if (customerName.isEmpty() || orderDetails.isEmpty()) return;

        new Thread(() -> {
            long orderId = db.orderDao().insertOrder(new Order(customerName, totalPrice, System.currentTimeMillis()));

            for (OrderDetail detail : orderDetails) {
                detail.setOrderId((int) orderId);
                db.orderDao().insertOrderDetail(detail);

                // Cập nhật số lượng sách trong kho
                Book book = db.bookDao().getBookById(detail.getBookId());
                book.setQuantity(book.getQuantity() - detail.getQuantity());
                db.bookDao().updateBookById(book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice());
            }

            finish();
        }).start();
    }
}
