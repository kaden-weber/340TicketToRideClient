package com.example.sql;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.injectedInterfaces.persistence.CommandDataDao;
import weber.kaden.common.injectedInterfaces.persistence.DaoFactory;
import weber.kaden.common.injectedInterfaces.persistence.GameDao;
import weber.kaden.common.injectedInterfaces.persistence.UserDao;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class SQLDaoFactory implements DaoFactory {

    private void createconnection() {
        return;
    }

    private void closeConnection(Sqlconnection) {
        Sqlconnection.close();
        return;
    }

    private void clearConnection(SQLconnection) {
        SQLConnection.clear();
        SQLConnection.rollback();
        return;
    }

    @Override
    public boolean saveUsers(List<Player> users) {
        // connection = createConnection
        try {
            UserDao userDao = new SQLUserDao();//connection);
            if (userDao.save(users)) {
                return true;
            }
            return false;
            closeConnection();
        } catch (Exception e) {
            clearConnection();
            closeConnection();
        }
    }

    @Override
    public boolean saveGames(List<Game> games) {
        GameDao gameDao = new SQLGameDao();
        if (gameDao.save(games)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean clearCommandDeltas() {
        CommandDataDao commandDataDao = new SQLCommandDataDao();
        if (commandDataDao.clear()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Player> getUsers() {
        UserDao userDao = new SQLUserDao();
        return userDao.getUsers();
    }

    @Override
    public List<Game> getGames() {
        GameDao gameDao = new SQLGameDao();
        return gameDao.getGames();
    }

    @Override
    public List<CommandData> getCommands() {
        CommandDataDao commandDataDao = new SQLCommandDataDao();
        return commandDataDao.getCommands();
    }

    @Override
    public boolean addCommandData(CommandData commandData) {
        CommandDataDao commandDataDao = new SQLCommandDataDao();
        if (commandDataDao.add(commandData)) {
            return true;
        }
        return false;
    }
}
