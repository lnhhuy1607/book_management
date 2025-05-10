package com.example.bookstore_management;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class BookFormActivity extends AppCompatActivity {
    private EditText edtTitle, edtAuthor, edtQuantity, edtPrice;
    private Button btnSaveBook;
    private BookDatabase db;
    private int bookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        db = BookDatabase.getDatabase(this);

        if (getIntent().hasExtra("bookId")) {
            bookId = getIntent().getIntExtra("bookId", -1);
            loadBookDetails();
        }

        btnSaveBook.setOnClickListener(view -> saveBook());
    }

    private void loadBookDetails() {
        new Thread(() -> {
            Book book = db.bookDao().getBookById(bookId);
            runOnUiThread(() -> {
                if (book != null) {
                    edtTitle.setText(book.getTitle());
                    edtAuthor.setText(book.getAuthor());
                    edtQuantity.setText(String.valueOf(book.getQuantity()));
                    edtPrice.setText(String.valueOf(book.getPrice()));
                }
            });
        }).start();
    }

    private void saveBook() {
        new Thread(() -> {
            Book book = new Book(edtTitle.getText().toString(), edtAuthor.getText().toString(),
                    Integer.parseInt(edtQuantity.getText().toString()),
                    Double.parseDouble(edtPrice.getText().toString()));

            if (bookId == -1) {
                db.bookDao().insertBook(book);
            } else {
                book.setId(bookId);
                db.bookDao().updateBookById(book.getId(), book.getTitle(), book.getAuthor(),
                        book.getQuantity(), book.getPrice());
            }
            finish();
        }).start();
    }
}
