package com.example.flatfile;

import java.util.List;

import weber.kaden.common.model.Game;

public class GameDao implements weber.kaden.common.injectedInterfaces.persistence.GameDao {
    @Override
    public boolean save(List<Game> games) {
        return false;
    }

    @Override
    public List<Game> getGames() {
        return null;
    }
}
