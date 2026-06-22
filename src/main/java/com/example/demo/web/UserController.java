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

    @GetMapping("/login")
    public String login(org.springframework.ui.Model model) {
        // Guarantee the model has a clean binding object container if none leaked from flash redirect attributes
        if (!model.containsAttribute("loginDTO")) {
            model.addAttribute("loginDTO", new UserLoginDTO());
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid @ModelAttribute("loginDTO") UserLoginDTO loginDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {

        // Check format validations first (e.g., blank fields)
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);
            return "redirect:/users/login";
        }

        // Try authentication check against database record
        boolean loginSuccessful = userService.login(loginDTO);
        if (!loginSuccessful) {
            bindingResult.rejectValue("password", "error.loginDTO", "Invalid username or password match! 🌌");
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);
            // Also flag a bad credentials helper token for legacy layout alerts
            redirectAttributes.addFlashAttribute("badCredentials", true);
            return "redirect:/users/login";
        }

        // Success! Stores user session reference properties
        session.setAttribute("currentUser", loginDTO.getUsername());
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(org.springframework.ui.Model model) {
        if (!model.containsAttribute("registerDTO")) {
            model.addAttribute("registerDTO", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("registerDTO") UserRegisterDTO registerDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        // Custom Business Logic Constraint: Passwords must match [cite: 62]
        if (registerDTO.getPassword() != null && !registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.registerDTO", "Passwords do not match!");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/users/register";
        }

        // Try persistence registration database hook
        boolean registrationSuccessful = userService.register(registerDTO);
        if (!registrationSuccessful) {
            bindingResult.rejectValue("username", "error.registerDTO", "Username is already claimed in this galaxy!");
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}