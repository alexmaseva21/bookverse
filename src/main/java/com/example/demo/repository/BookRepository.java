package com.example.demo.repository;

import com.example.demo.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    // Allows users to look up titles instantly by typing in a search bar later
    List<Book> findByTitleContainingIgnoreCase(String keyword);
}