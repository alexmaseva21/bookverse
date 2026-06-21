package com.example.demo.repository;

import com.example.demo.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    // Fetches public reviews so other community members can see them
    List<Review> findAllByIsPrivateFalseOrderByCreatedAtDesc();
}
