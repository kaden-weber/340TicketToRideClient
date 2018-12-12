package com.example.flatfile;

import java.io.File;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class DaoFactory implements weber.kaden.common.injectedInterfaces.persistence.DaoFactory {

    private String filePath;
    private File file;

    DaoFactory() {}

    DaoFactory(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean saveUsers(List<Player> users) {
        return false;
    }

    @Override
    public boolean saveGames(List<Game> games) {
        return false;
    }

    @Override
    public boolean clearCommandDeltas() {
        return false;
    }

    @Override
    public List<Player> getUsers() {
        return null;
    }

    @Override
    public List<Game> getGames() {
        return null;
    }

    @Override
    public List<CommandData> getCommands() {
        return null;
    }

    @Override
    public boolean addCommandData(CommandData commandData) {
        return false;
    }
}
