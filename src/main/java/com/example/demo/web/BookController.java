package com.example.demo.web;

import com.example.demo.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/explore")
    public String exploreCatalog(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("allBooks", bookService.searchBooks(keyword));
            model.addAttribute("keyword", keyword); // Sends keyword back to keep it in the input box
        } else {
            model.addAttribute("allBooks", bookService.getAllBooks());
        }
        return "explore";
    }
}