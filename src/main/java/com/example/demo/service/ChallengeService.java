package com.example.demo.service;

import com.example.demo.model.entity.Challenge;
import com.example.demo.model.entity.ChallengeType;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ChallengeService {
    // Allows a user to accept or create a brand new tracking goal
    void createChallenge(String username, String title, String description, ChallengeType type, Integer targetCount, String targetGenre, LocalDate expiryDate);

    // Gets all goals belonging to a reader
    List<Challenge> getUserChallenges(String username);

    // The core sync engine: Scans completed shelves and dynamically syncs the +1 progress states
    void syncUserChallengeProgress(String username);
}