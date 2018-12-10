package com.example.flatfile;

import java.io.File;
import java.util.List;

import weber.kaden.common.model.Game;

public class GameDao implements weber.kaden.common.injectedInterfaces.persistence.GameDao {

    private String filePath;
    private File file;

    GameDao(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
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
