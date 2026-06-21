package com.example.demo.service;

import com.example.demo.model.dto.ReviewSubmitDTO;
import com.example.demo.model.entity.Review;
import com.example.demo.model.entity.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveReview(ReviewSubmitDTO reviewSubmitDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Review review = new Review();
        review.setContent(reviewSubmitDTO.getContent());
        review.setStars(reviewSubmitDTO.getStars());
        review.setPrivate(reviewSubmitDTO.isPrivate());
        review.setUser(user);

        reviewRepository.save(review);
    }
}
