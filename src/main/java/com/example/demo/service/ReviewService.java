package com.example.demo.service;

import com.example.demo.model.dto.ReviewSubmitDTO;

public interface ReviewService {
    void saveReview(ReviewSubmitDTO reviewSubmitDTO, String username);
}
