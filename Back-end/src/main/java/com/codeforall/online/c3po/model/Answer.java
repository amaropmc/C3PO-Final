package com.codeforall.online.c3po.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * A class that represents an answer to a question of the quiz
 */
@Entity
@Table(name = "answers")
public class Answer extends AbstractModel {
    private String description;
    private Boolean correct;
    @ManyToOne
    private Question question;

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
     * Get the answer correctness
     * @return either true or false
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Set the answer correctness
     * @param correct either true or false
     */
    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    /**
     * Get the answer question
     * @return the answer question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Set the answer question
     * @param question the answer question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Answer answer)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        return correct == answer.correct &&
                Objects.equals(description, answer.description) &&
                Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Integer)super.hashCode(), description, correct, question);
    }
}
