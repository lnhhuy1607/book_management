package com.example.bookstore_management;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookDAO {

    @Insert
    void insertBook(Book book);

    // Cập nhật sách theo mã (id)
    @Query("UPDATE books SET title = :title, author = :author, quantity = :quantity, price = :price WHERE id = :bookId")
    void updateBookById(int bookId, String title, String author, int quantity, double price);

    // Xóa sách theo mã (id)
    @Query("DELETE FROM books WHERE id = :bookId")
    void deleteBookById(int bookId);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();


    // Tìm kiếm sách theo tiêu đề
    @Query("SELECT * FROM books WHERE title LIKE :query")
    List<Book> searchBooks(String query);

    // Lấy sách theo mã (id)
    @Query("SELECT * FROM books WHERE id = :bookId")
    Book getBookById(int bookId);

    @Query("DELETE FROM order_details WHERE bookId = :bookId")
    void deleteOrderDetailsByBookId(int bookId);


}
