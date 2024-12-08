package com.codeforall.online.c3po.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * A class that represents an answer to a question
 */
@Entity
@Table(name = "answers")
public class Answer extends AbstractModel {
    private String description;
    private boolean correct;
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
    public void setCorrect(boolean correct) {
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
}
