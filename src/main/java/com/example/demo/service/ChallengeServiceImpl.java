package com.example.demo.service;

import com.example.demo.model.entity.*;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.ShelfRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository, ShelfRepository shelfRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.shelfRepository = shelfRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createChallenge(String username, String title, String description, ChallengeType type, Integer targetCount, String targetGenre, LocalDate expiryDate) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Challenge challenge = new Challenge();
        challenge.setUser(user);
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setChallengeType(type);
        challenge.setTargetCount(targetCount);
        challenge.setTargetGenre(targetGenre);
        challenge.setExpiryDate(expiryDate);
        challenge.setCurrentProgress(0);
        challenge.setCompleted(false);

        challengeRepository.save(challenge);

        // Immediately sync progress in case they already have matching completed books!
        syncUserChallengeProgress(username);
    }

    @Override
    public List<Challenge> getUserChallenges(String username) {
        return userRepository.findByUsername(username)
                .map(challengeRepository::findAllByUser)
                .orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public void syncUserChallengeProgress(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 1. Fetch all items on the user's shelves
        List<ShelfItem> shelfItems = shelfRepository.findAllByUser(user);

        // 2. Filter out books that are fully marked as COMPLETED
        List<ShelfItem> completedBooks = shelfItems.stream()
                .filter(item -> item.getStatus() == ReadingStatus.COMPLETED)
                .toList();

        // 3. Get all active challenges for this user
        List<Challenge> activeChallenges = challengeRepository.findAllByUserAndIsCompletedFalse(user);

        for (Challenge challenge : activeChallenges) {
            if (challenge.getChallengeType() == ChallengeType.BOOKS_COUNT) {
                // Quantitative Goal: Total count of completed books
                int progress = completedBooks.size();
                challenge.setCurrentProgress(progress);

                if (progress >= challenge.getTargetCount()) {
                    challenge.setCompleted(true);
                }
            } else if (challenge.getChallengeType() == ChallengeType.GENRE_MATCH) {
                // Qualitative Goal: Scan if any completed book matches the target genre badge
                boolean hasMatchingGenre = completedBooks.stream()
                        .anyMatch(item -> item.getBook().getGenre() != null &&
                                item.getBook().getGenre().equalsIgnoreCase(challenge.getTargetGenre()));

                if (hasMatchingGenre) {
                    challenge.setCurrentProgress(1);
                    challenge.setTargetCount(1); // Ensure math displays 1/1
                    challenge.setCompleted(true);
                } else {
                    challenge.setCurrentProgress(0);
                    challenge.setTargetCount(1); // Displays 0/1
                }
            }
            challengeRepository.save(challenge);
        }
    }
}