package com.example.sql;

import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.PlayerDao;
import weber.kaden.common.model.Player;

public class SQLPlayerDao implements PlayerDao {
    @Override
    public boolean save(List<Player> players) {
        return false;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }
}
