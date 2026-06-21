package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // This tells Spring to look for a file named "index.html" inside src/main/resources/templates/
        return "index";
    }
}