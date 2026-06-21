package com.example.demo.service;

import com.example.demo.model.entity.ShelfItem;
import com.example.demo.model.entity.ReadingStatus;
import java.util.List;
import java.util.UUID;

public interface ShelfService {
    // Adds a book to a user's collection or updates its tracking state
    void addOrUpdateBookStatus(UUID bookId, String username, ReadingStatus status);

    // Retrieves everything currently sitting on a user's shelf grid
    List<ShelfItem> getUserShelfItems(String username);

    // Removes a book completely from a user's shelves
    void removeBookFromShelf(UUID bookId, String username);
}