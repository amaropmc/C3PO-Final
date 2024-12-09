package com.codeforall.online.c3po.command;

import com.codeforall.online.c3po.model.Question;
import jakarta.validation.constraints.*;

import java.util.List;

/**
 * A class which represent the data transfer object related to {@link Question}.
 */
public class QuestionDto {
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    private String description;
    @NotNull
    @PositiveOrZero
    private Integer score;
    @NotEmpty
    private List<AnswerDto> answers;

    /**
     * Get the question id
     * @return the question id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the question id
     * @param id the question id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the question description
     * @return the question description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the question score
     * @return the question score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Set the question score
     * @param score the question score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Set the question description
     * @param description the question description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the questions answers as Data Transfer Objects
     * @return a list of answers DTO
     */
    public List<AnswerDto> getAnswers() {
        return answers;
    }

    /**
     * Set the questions answers as Data Transfer Objects
     * @param answers a list of answers DTO
     */
    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}
