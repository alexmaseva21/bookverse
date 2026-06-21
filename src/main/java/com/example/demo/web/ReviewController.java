package com.example.demo.web;

import com.example.demo.model.dto.ReviewSubmitDTO;
import com.example.demo.service.ReviewService;
import com.example.demo.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final BookRepository bookRepository;

    public ReviewController(ReviewService reviewService, BookRepository bookRepository) {
        this.reviewService = reviewService;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("reviewDTO")
    public ReviewSubmitDTO reviewDTO() {
        return new ReviewSubmitDTO();
    }

    // Loads the watercolor form, checking for an optional bookId link parameter
    @GetMapping("/new")
    public String newReview(@RequestParam(value = "bookId", required = false) UUID bookId,
                            HttpSession session,
                            Model model) {

        if (session.getAttribute("currentUser") == null) {
            return "redirect:/users/login";
        }

        // If coming from a specific book card, pass its metadata details to the form
        if (bookId != null) {
            bookRepository.findById(bookId).ifPresent(book -> {
                model.addAttribute("targetBookTitle", book.getTitle());
                model.addAttribute("targetBookAuthor", book.getAuthor());
                model.addAttribute("targetBookId", book.getId());
            });
        }

        return "reviews";
    }

    // Handles the final review submission details
    @PostMapping("/new")
    public String saveReview(@Valid ReviewSubmitDTO reviewDTO,
                             BindingResult bindingResult,
                             @RequestParam(value = "bookId", required = false) UUID bookId,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {

        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewDTO", reviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewDTO", bindingResult);
            // Appends the book parameter back to the URL redirect if it exists to preserve the layout text
            return "redirect:/reviews/new" + (bookId != null ? "?bookId=" + bookId : "");
        }

        reviewService.saveReview(reviewDTO, username);
        return "redirect:/books/explore";
    }
}