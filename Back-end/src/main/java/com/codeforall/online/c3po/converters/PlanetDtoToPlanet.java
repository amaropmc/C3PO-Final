package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.PlanetDto;
import com.codeforall.online.c3po.model.Planet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A concrete converter class that transforms and {@link PlanetDto} entity into an {@link Planet}
 */
@Component
public class PlanetDtoToPlanet extends AbstractConverter<PlanetDto, Planet> {

    /**
     * @see AbstractConverter#convert(List)
     */
    @Override
    public Planet convert(PlanetDto planetDto) {
        Planet planet = new Planet();

        planet.setName(planetDto.getName());

        return planet;
    }
}
