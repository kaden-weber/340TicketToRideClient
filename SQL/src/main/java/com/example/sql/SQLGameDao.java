package com.example.sql;

import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.GameDao;
import weber.kaden.common.model.Game;

public class SQLGameDao implements GameDao {
    @Override
    public boolean save(List<Game> games) {
        return false;
    }

    @Override
    public List<Game> getGames() {
        return null;
    }
}
