package com.example.demo.web;

import com.example.demo.model.dto.ChallengeDTO;
import com.example.demo.model.entity.Challenge;
import com.example.demo.model.entity.ChallengeType;
import com.example.demo.service.ChallengeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        // Supply an empty DTO if none exists in the model redirect flash attributes
        if (!model.containsAttribute("challengeDTO")) {
            model.addAttribute("challengeDTO", new ChallengeDTO());
        }

        return "goals";
    }

    @PostMapping("/create")
    public String launchGoal(@Valid @ModelAttribute("challengeDTO") ChallengeDTO challengeDTO,
                             BindingResult bindingResult,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/users/login";
        }

        // Enforce specific custom business rule constraint validation for qualitative tracks
        if (challengeDTO.getChallengeType() == ChallengeType.GENRE_MATCH &&
                (challengeDTO.getTargetGenre() == null || challengeDTO.getTargetGenre().isBlank())) {
            bindingResult.rejectValue("targetGenre", "error.targetGenre", "Genre conquest needs a target genre label!");
        }

        // If data check validation fails, bounce back with flash attribute streams
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("challengeDTO", challengeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.challengeDTO", bindingResult);
            return "redirect:/goals";
        }

        // Handle structural fallback defaults for target variables
        Integer targetCount = challengeDTO.getTargetCount();
        if (challengeDTO.getChallengeType() == ChallengeType.GENRE_MATCH) {
            targetCount = 1;
        } else if (targetCount == null || targetCount < 1) {
            targetCount = 1;
        }

        challengeService.createChallenge(
                username,
                challengeDTO.getTitle(),
                challengeDTO.getDescription(),
                challengeDTO.getChallengeType(),
                targetCount,
                challengeDTO.getTargetGenre(),
                challengeDTO.getExpiryDate()
        );

        return "redirect:/goals";
    }
}