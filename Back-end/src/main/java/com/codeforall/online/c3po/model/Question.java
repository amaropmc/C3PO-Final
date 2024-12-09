package com.codeforall.online.c3po.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents the questions that is part of a quiz
 */
@Entity
@Table(name = "questions")
public class Question extends AbstractModel {
    private String description;
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            mappedBy = "question",
            fetch = FetchType.LAZY
    )
    private Set<Answer> answers = new HashSet<>();
    private Integer score;
    @ManyToOne
    private Planet planet;

    /**
     * Add an answer to a question
     * @param answer the answer to be added
     */
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    /**
     * Removes an answer from a question
     * @param answer the answer to be removed
     */
    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    /**
     * Get the question description
     * @return the question description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the question description
     * @param description the question description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get a set of question's answers
     * @return a set of answers
     */
    public Set<Answer> getAnswers() {
        return answers;
    }

    /**
     * Set a question's answers
     * @param answers the question's answers to set
     */
    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    /**
     * Get the question's score
     * @return the question's score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Sets the question's score
     * @param score the question score to set
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Get the question's planet
     * @return the question's planet
     */
    public Planet getPlanet() {
        return planet;
    }

    /**
     * Set the question's planet
     * @param planet the question's planet to set
     */
    public void setPlanet(Planet planet) {
        this.planet = planet;
    }
}
