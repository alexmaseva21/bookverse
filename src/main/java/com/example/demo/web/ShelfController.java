package com.example.demo.web;

import com.example.demo.model.entity.ShelfItem;
import com.example.demo.model.entity.ReadingStatus;
import com.example.demo.service.ShelfService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shelves")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @GetMapping
    public String viewShelves(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        List<ShelfItem> allItems = shelfService.getUserShelfItems(username);

        // Separate items out into distinct categorical lists for the dashboard rows
        List<ShelfItem> wantToRead = allItems.stream()
                .filter(item -> item.getStatus() == ReadingStatus.WANT_TO_READ)
                .collect(Collectors.toList());

        List<ShelfItem> reading = allItems.stream()
                .filter(item -> item.getStatus() == ReadingStatus.READING)
                .collect(Collectors.toList());

        List<ShelfItem> completed = allItems.stream()
                .filter(item -> item.getStatus() == ReadingStatus.COMPLETED)
                .collect(Collectors.toList());

        model.addAttribute("wantToReadList", wantToRead);
        model.addAttribute("readingList", reading);
        model.addAttribute("completedList", completed);
        model.addAttribute("username", username);

        return "shelves";
    }

    // Handles adding a book from a catalog card click shortcut
    @PostMapping("/add")
    public String addToShelf(@RequestParam("bookId") UUID bookId,
                             @RequestParam("status") ReadingStatus status,
                             HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        shelfService.addOrUpdateBookStatus(bookId, username, status);
        return "redirect:/shelves";
    }

    // Handles removing an item from the shelf interface
    @PostMapping("/remove")
    public String removeFromShelf(@RequestParam("bookId") UUID bookId, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        shelfService.removeBookFromShelf(bookId, username);
        return "redirect:/shelves";
    }
    // Handles shifting a book seamlessly to a different shelf
    @PostMapping("/update-status")
    public String updateBookStatus(@RequestParam("bookId") UUID bookId,
                                   @RequestParam("status") ReadingStatus status,
                                   HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        shelfService.addOrUpdateBookStatus(bookId, username, status);
        return "redirect:/shelves";
    }
}