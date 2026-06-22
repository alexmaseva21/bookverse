package com.example.demo.model.dto;

import com.example.demo.model.entity.ChallengeType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ChallengeDTO {

    @NotBlank(message = "Goal title cannot be blank!")
    @Size(min = 3, max = 40, message = "Title must be between 3 and 40 characters!")
    private String title;

    @NotBlank(message = "Description cannot be blank!")
    @Size(min = 5, message = "Description must be at least 5 characters long!")
    private String description;

    @NotNull(message = "You must select a challenge strategy!")
    private ChallengeType challengeType;

    private Integer targetCount;
    private String targetGenre;

    @FutureOrPresent(message = "The target deadline cannot be in the past!")
    private LocalDate expiryDate;

    public ChallengeDTO() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ChallengeType getChallengeType() { return challengeType; }
    public void setChallengeType(ChallengeType challengeType) { this.challengeType = challengeType; }

    public Integer getTargetCount() { return targetCount; }
    public void setTargetCount(Integer targetCount) { this.targetCount = targetCount; }

    public String getTargetGenre() { return targetGenre; }
    public void setTargetGenre(String targetGenre) { this.targetGenre = targetGenre; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}