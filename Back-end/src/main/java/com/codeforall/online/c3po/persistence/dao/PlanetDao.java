package com.codeforall.online.c3po.persistence.dao;

import com.codeforall.online.c3po.model.Planet;

public interface PlanetDao extends Dao<Planet>{

    Planet findByName(String name);
}
