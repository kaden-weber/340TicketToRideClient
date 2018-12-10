package com.example.flatfile;

import java.io.File;
import java.util.List;

import weber.kaden.common.model.Player;

public class PlayerDao implements weber.kaden.common.injectedInterfaces.persistence.PlayerDao {

    private String filePath;
    private File file;

    PlayerDao(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
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
