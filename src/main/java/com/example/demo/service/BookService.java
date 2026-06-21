package com.example.demo.service;

import com.example.demo.model.entity.Book;
import java.util.List;


public interface BookService {
    void initializeBooks();

    java.util.List<com.example.demo.model.entity.Book> searchBooks(String keyword);
    // Fetches all books from the database repository canvas
    List<Book> getAllBooks();
}