package com.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class DaoFactory implements weber.kaden.common.injectedInterfaces.persistence.DaoFactory {
    private Connection createConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:sqlite:ticket_to_ride.db";
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to database established");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public boolean saveUsers(List<Player> users) {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.UserDao userDao = new UserDao(connection);
        boolean success = userDao.save(users);
        closeConnection(connection);
        return success;
    }

    @Override
    public boolean saveGames(List<Game> games) {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.GameDao gameDao = new GameDao(connection);
        boolean success = gameDao.save(games);
        closeConnection(connection);
        return success;
    }

    @Override
    public boolean clearCommandDeltas() {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.CommandDataDao commandDataDao = new CommandDataDao(connection);
        boolean success = commandDataDao.clear();
        closeConnection(connection);
        return success;
    }

    @Override
    public List<Player> getUsers() {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.UserDao userDao = new UserDao(connection);
        List<Player> users = userDao.getUsers();
        closeConnection(connection);
        return users;
    }

    @Override
    public List<Game> getGames() {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.GameDao gameDao = new GameDao(connection);
        List<Game> games = gameDao.getGames();
        closeConnection(connection);
        return games;
    }

    @Override
    public List<CommandData> getCommands() {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.CommandDataDao commandDataDao = new CommandDataDao(connection);
        List<CommandData> commands = commandDataDao.getCommands();
        closeConnection(connection);
        return commands;
    }

    @Override
    public boolean addCommandData(CommandData commandData) {
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.CommandDataDao commandDataDao = new CommandDataDao(connection);
        boolean success = commandDataDao.add(commandData);
        closeConnection(connection);
        return success;
    }
}
