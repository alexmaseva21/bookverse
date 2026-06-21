package com.example.demo.web;

import com.example.demo.model.dto.UserLoginDTO;
import com.example.demo.model.dto.UserRegisterDTO;
import com.example.demo.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loginDTO")
    public UserLoginDTO loginDTO() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Handles data when clicking the "Log In" button
    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginDTO loginDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) { // Tracks active browser login state

        if (bindingResult.hasErrors() || !userService.login(loginDTO)) {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);
            return "redirect:/users/login";
        }

        // Success! Stores the username session attribute for the index page banner to read
        session.setAttribute("currentUser", loginDTO.getUsername());

        return "redirect:/";
    }

    @ModelAttribute("registerDTO")
    public UserRegisterDTO registerDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO registerDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userService.register(registerDTO)) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/users/register";
        }
        return "redirect:/users/login";
    }

    // Clears out the user session variables when navigating to /users/logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}