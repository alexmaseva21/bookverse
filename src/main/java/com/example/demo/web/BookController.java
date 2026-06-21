package com.example.demo.web;

import com.example.demo.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/explore")
    public String exploreCatalog(Model model) {
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "explore"; // Looks for explore.html inside templates/
    }
}