package com.example.demo.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewSubmitDTO {

    @NotBlank(message = "Your review content cannot be empty!")
    private String content;

    @NotNull(message = "Please select a star rating!")
    @Min(1)
    @Max(5)
    private Integer stars;

    private boolean isPrivate;

    public ReviewSubmitDTO() {}

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean aPrivate) { isPrivate = aPrivate; }
}
