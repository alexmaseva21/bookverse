package com.example.demo.service;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.ShelfItem;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.ReadingStatus;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ShelfRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShelfServiceImpl implements ShelfService {

    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ShelfServiceImpl(ShelfRepository shelfRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.shelfRepository = shelfRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void addOrUpdateBookStatus(UUID bookId, String username, ReadingStatus status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Check if this book is already sitting on the user's shelf
        Optional<ShelfItem> existingItem = shelfRepository.findByUserAndBookId(user, bookId);

        if (existingItem.isPresent()) {
            // Update its existing reading status state
            ShelfItem item = existingItem.get();
            item.setStatus(status);
            shelfRepository.save(item);
        } else {
            // Create a brand new shelf association record
            ShelfItem newItem = new ShelfItem();
            newItem.setUser(user);
            newItem.setBook(book);
            newItem.setStatus(status);
            shelfRepository.save(newItem);
        }
    }

    @Override
    public List<ShelfItem> getUserShelfItems(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return new ArrayList<>();
        }
        return shelfRepository.findAllByUser(userOpt.get());
    }

    @Override
    @Transactional
    public void removeBookFromShelf(UUID bookId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        shelfRepository.findByUserAndBookId(user, bookId)
                .ifPresent(shelfRepository::delete);
    }
}