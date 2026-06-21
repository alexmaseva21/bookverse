package com.example.demo.web;

import com.example.demo.model.dto.ReviewSubmitDTO;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ModelAttribute("reviewDTO")
    public ReviewSubmitDTO reviewDTO() {
        return new ReviewSubmitDTO();
    }

    @GetMapping("/new")
    public String newReview(HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/users/login"; // Must be logged in to write a review!
        }
        return "reviews";
    }

    @PostMapping("/new")
    public String saveReview(@Valid ReviewSubmitDTO reviewDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {

        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewDTO", reviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewDTO", bindingResult);
            return "redirect:/reviews/new";
        }

        reviewService.saveReview(reviewDTO, username);
        return "redirect:/"; // Success! Go back home
    }
}
