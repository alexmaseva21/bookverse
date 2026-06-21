package com.example.demo.web;

import com.example.demo.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ReviewRepository reviewRepository;

    // Injecting the review repository to fetch community activity
    public HomeController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // If a user is logged in, pass their username along to personalize the welcome banner
        String currentUser = (String) session.getAttribute("currentUser");
        if (currentUser != null) {
            model.addAttribute("username", currentUser);
        }

        // Fetch all public reviews ordered by creation date (newest first)
        // and link them right to the global community feed module
        model.addAttribute("globalReviews", reviewRepository.findAllByIsPrivateFalseOrderByCreatedAtDesc());

        return "index";
    }
}