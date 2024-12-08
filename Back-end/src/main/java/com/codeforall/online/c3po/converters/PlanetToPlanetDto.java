package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.PlanetDto;
import com.codeforall.online.c3po.model.Planet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A concrete converter class that transforms an {@link Planet} entity into an {@link PlanetDto}.
 */
@Component
public class PlanetToPlanetDto extends AbstractConverter<Planet, PlanetDto> {

    /**
     * @see AbstractConverter#convert(List)
     */
    @Override
    public PlanetDto convert(Planet planet) {
        PlanetDto planetDto = new PlanetDto();

        planetDto.setId(planet.getId());
        planetDto.setName(planet.getName());

        return planetDto;
    }
}
