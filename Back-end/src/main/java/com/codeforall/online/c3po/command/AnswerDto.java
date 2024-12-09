package com.codeforall.online.c3po.command;

import com.codeforall.online.c3po.model.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;

/**
 * A class which represent the data transfer object related to {@link Answer}.
 */
public class AnswerDto {
    private Long id;
    @NotBlank
    @NotNull
    private String description;
    @BooleanFlag
    private boolean correct;

    /**
     * Get the answer id
     * @return the answer id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the answer id
     * @param id the answer id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the answer description
     * @return the answer description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the answer description
     * @param description the answer description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get if the answer is correct or incorrect
     * @return either true or false
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Set the answer to correct or incorrect
     * @param correct either true or false
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
