package com.example.bookstore_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.stream.Collectors;

public class BookListActivity extends AppCompatActivity {
    private ListView lvBooks;
    private EditText edtSearchBook;
    private Button btnSearchBook, btnAddBook;
    private BookDatabase db;
    private ArrayAdapter<String> adapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        lvBooks = findViewById(R.id.lvBooks);
        edtSearchBook = findViewById(R.id.edtSearchBook);
        btnSearchBook = findViewById(R.id.btnSearchBook);
        btnAddBook = findViewById(R.id.btnAddBook);
        db = BookDatabase.getDatabase(this);

        loadBooks("");

        btnSearchBook.setOnClickListener(view -> {
            String query = edtSearchBook.getText().toString().trim();
            loadBooks(query);
        });

        btnAddBook.setOnClickListener(view ->
                startActivity(new Intent(BookListActivity.this, BookFormActivity.class)));

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(BookListActivity.this, BookFormActivity.class);
            intent.putExtra("bookId", bookList.get(position).getId());
            startActivity(intent);
        });
    }

    private void loadBooks(String searchQuery) {
        new Thread(() -> {
            if (searchQuery.isEmpty()) {
                bookList = db.bookDao().getAllBooks();
            } else {
                bookList = db.bookDao().searchBooks("%" + searchQuery + "%");
            }

            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        bookList.stream().map(Book::getTitle).collect(Collectors.toList()));
                lvBooks.setAdapter(adapter);
            });
        }).start();
    }
}
