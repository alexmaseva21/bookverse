package com.example.demo.web;

import com.example.demo.model.entity.Challenge;
import com.example.demo.model.entity.ChallengeType;
import com.example.demo.service.ChallengeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/goals")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public String viewGoals(HttpSession session, Model model) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        // Run a fresh database progress sync before rendering the gauges
        challengeService.syncUserChallengeProgress(username);

        List<Challenge> challenges = challengeService.getUserChallenges(username);
        model.addAttribute("challenges", challenges);
        model.addAttribute("username", username);

        return "goals";
    }

    @PostMapping("/create")
    public String launchGoal(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("challengeType") ChallengeType challengeType,
                             @RequestParam(value = "targetCount", required = false) Integer targetCount,
                             @RequestParam(value = "targetGenre", required = false) String targetGenre,
                             @RequestParam(value = "expiryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate,
                             HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        // Handle structural fallback defaults for empty input variables
        if (challengeType == ChallengeType.GENRE_MATCH) {
            targetCount = 1;
        } else if (targetCount == null || targetCount < 1) {
            targetCount = 1;
        }

        challengeService.createChallenge(username, title, description, challengeType, targetCount, targetGenre, expiryDate);
        return "redirect:/goals";
    }
}