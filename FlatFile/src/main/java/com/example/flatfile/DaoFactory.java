package com.example.flatfile;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class DaoFactory implements weber.kaden.common.injectedInterfaces.persistence.DaoFactory {

    private String getGamePath() {
        return "games.json";
    }

    private String getUsersPath() {
        return "users.json";
    }

    private String getCommandsPath() {
        return "commands.json";
    }

    @Override
    public boolean saveUsers(List<Player> users) {
        return new UserDao(getUsersPath()).save(users);
    }

    @Override
    public boolean saveGames(List<Game> games) {
        return new GameDao(getGamePath()).save(games);
    }

    @Override
    public boolean clearCommandDeltas() {
        return new CommandDataDao(getCommandsPath()).clear();
    }

    @Override
    public List<Player> getUsers() {
        return new UserDao(getUsersPath()).getUsers();
    }

    @Override
    public List<Game> getGames() {
        return new GameDao(getGamePath()).getGames();
    }

    @Override
    public List<CommandData> getCommands() {
        return new CommandDataDao(getCommandsPath()).getCommands();
    }

    @Override
    public boolean addCommandData(CommandData commandData) {
        return new CommandDataDao(getCommandsPath()).add(commandData);
    }

    @Override
    public boolean clear() {
        return new GameDao(getGamePath()).clear()
                && new UserDao(getUsersPath()).clear()
                && new CommandDataDao(getCommandsPath()).clear();
    }
}
