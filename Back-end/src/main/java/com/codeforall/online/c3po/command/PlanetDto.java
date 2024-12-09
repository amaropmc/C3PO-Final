package com.codeforall.online.c3po.command;

import com.codeforall.online.c3po.model.Planet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A class which represent the data transfer object related to {@link Planet}.
 */
public class PlanetDto {
    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 64)
    private String name;

    /**
     * Get the planet id
     * @return the planet id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the planet id
     * @param id the planet id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the planet name
     * @return the planet name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the planet name
     * @param name the planet name
     */
    public void setName(String name) {
        this.name = name;
    }
}
