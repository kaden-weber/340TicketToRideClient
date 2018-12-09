package com.example.sql;

import java.sql.Connection;
import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.PlayerDao;
import weber.kaden.common.model.Player;

public class SQLPlayerDao extends SQLDao implements PlayerDao {
    public SQLPlayerDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Player> players) {
        return false;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }
}
