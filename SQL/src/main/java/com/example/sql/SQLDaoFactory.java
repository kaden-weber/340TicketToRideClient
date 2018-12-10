package com.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.injectedInterfaces.persistence.CommandDataDao;
import weber.kaden.common.injectedInterfaces.persistence.DaoFactory;
import weber.kaden.common.injectedInterfaces.persistence.GameDao;
import weber.kaden.common.injectedInterfaces.persistence.UserDao;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class SQLDaoFactory implements DaoFactory {
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
        UserDao userDao = new SQLUserDao(connection);
        boolean success = userDao.save(users);
        closeConnection(connection);
        return success;
    }

    @Override
    public boolean saveGames(List<Game> games) {
        Connection connection = createConnection();
        GameDao gameDao = new SQLGameDao(connection);
        boolean success = gameDao.save(games);
        closeConnection(connection);
        return success;
    }

    @Override
    public boolean clearCommandDeltas() {
        Connection connection = createConnection();
        CommandDataDao commandDataDao = new SQLCommandDataDao(connection);
        boolean success = commandDataDao.clear();
        closeConnection(connection);
        return success;
    }

    @Override
    public List<Player> getUsers() {
        Connection connection = createConnection();
        UserDao userDao = new SQLUserDao(connection);
        List<Player> users = userDao.getUsers();
        closeConnection(connection);
        return users;
    }

    @Override
    public List<Game> getGames() {
        Connection connection = createConnection();
        GameDao gameDao = new SQLGameDao(connection);
        List<Game> games = gameDao.getGames();
        closeConnection(connection);
        return games;
    }

    @Override
    public List<CommandData> getCommands() {
        Connection connection = createConnection();
        CommandDataDao commandDataDao = new SQLCommandDataDao(connection);
        List<CommandData> commands = commandDataDao.getCommands();
        closeConnection(connection);
        return commands;
    }

    @Override
    public boolean addCommandData(CommandData commandData) {
        Connection connection = createConnection();
        CommandDataDao commandDataDao = new SQLCommandDataDao(connection);
        boolean success = commandDataDao.add(commandData);
        closeConnection(connection);
        return success;
    }
}
