package com.example.bookstore_management;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DeleteBookActivity extends AppCompatActivity {
    private ListView lvBooks;
    private Button btnDeleteBook;
    private BookDatabase db;
    private List<Book> bookList;
    private ArrayAdapter<String> adapter;
    private int selectedBookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        lvBooks = findViewById(R.id.lvBooks);
        btnDeleteBook = findViewById(R.id.btnDeleteBook);
        db = BookDatabase.getDatabase(this);

        loadBooks();

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            selectedBookId = bookList.get(position).getId();
            Toast.makeText(DeleteBookActivity.this, "Đã chọn sách: " + bookList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        });

        btnDeleteBook.setOnClickListener(view -> {
            if (selectedBookId != -1) {
                deleteBook(selectedBookId);
            } else {
                Toast.makeText(this, "Vui lòng chọn sách để xóa!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBooks() {
        new Thread(() -> {
            bookList = db.bookDao().getAllBooks();
            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        bookList.stream().map(Book::getTitle).toArray(String[]::new));
                lvBooks.setAdapter(adapter);
            });
        }).start();
    }

    private void deleteBook(int bookId) {
        new Thread(() -> {
            db.bookDao().deleteOrderDetailsByBookId(bookId); // Xóa đơn hàng chứa sách
            db.bookDao().deleteBookById(bookId); // Xóa sách

            runOnUiThread(() -> {
                Toast.makeText(DeleteBookActivity.this, "Xóa sách thành công!", Toast.LENGTH_SHORT).show();
                loadBooks(); // Cập nhật danh sách sau khi xóa
                finish(); // Quay lại trang chính
            });
        }).start();
    }


}
