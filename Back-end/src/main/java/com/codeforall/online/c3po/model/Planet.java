package com.codeforall.online.c3po.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents a planet the user can travel to
 */
@Entity
@Table(name = "planets")
public class Planet extends AbstractModel {

    @Column(nullable = false)
    private String name;
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = false,
            mappedBy = "planet",
            fetch = FetchType.EAGER
    )
    private Set<Question> questions = new HashSet<>();

    /**
     * Add a question to a planet
     * @param question the question to be added
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setPlanet(this);
    }

    /**
     * Removes a question from a planet
     * @param question the question to be removed
     */
    public void removeQuestion(Question question) {
        this.questions.remove(question);
        question.setPlanet(null);
    }

    /**
     * Get the planet name
     * @return the planet name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the planet name
     * @param name the planet name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get a set of the planet's questions
     * @return a set of questions
     */
    public Set<Question> getQuestions() {
        return questions;
    }

    /**
     * Set a planet's questions
     * @param questions the questions to set
     */
    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Planet planet)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        return Objects.equals(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Integer)super.hashCode(), name);
    }
}
