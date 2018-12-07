package com.example.flatfile;

import java.util.List;

import weber.kaden.common.model.Player;

public class PlayerDao implements weber.kaden.common.injectedInterfaces.persistence.PlayerDao {

    @Override
    public boolean save(List<Player> players) {
        return false;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }
}
