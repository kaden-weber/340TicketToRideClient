package com.example.sql;

import java.sql.Connection;
import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.GameDao;
import weber.kaden.common.model.Game;

public class SQLGameDao extends SQLDao implements GameDao {
    public SQLGameDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Game> games) {
        return false;
    }

    @Override
    public List<Game> getGames() {
        return null;
    }
}
