package com.example.demo.repository;

import com.example.demo.model.entity.ShelfItem;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.ReadingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShelfRepository extends JpaRepository<ShelfItem, UUID> {
    // Finds all shelf items belonging to a specific logged-in user
    List<ShelfItem> findAllByUser(User user);

    // Checks if a book is already placed on a user's shelf
    Optional<ShelfItem> findByUserAndBookId(User user, UUID bookId);
}