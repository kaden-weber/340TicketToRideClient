package com.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

            String initialUserSQL = "CREATE TABLE IF NOT EXISTS `User` (\n" +
                                    "\t`id`\tTEXT NOT NULL UNIQUE,\n" +
                                    "\t`password`\tTEXT NOT NULL\n" +
                                    ");";
            String initialGameSQL = "CREATE TABLE IF NOT EXISTS `Game` (\n" +
                                    "\t`id`\tTEXT NOT NULL UNIQUE,\n" +
                                    "\t`gameName`\tTEXT NOT NULL,\n" +
                                    "\t`gameState`\tTEXT,\n" +
                                    "\t`playerIDs`\tTEXT,\n" +
                                    "\t`lastPlayer`\tTEXT,\n" +
                                    "\t`currentPlayer`\tINTEGER,\n" +
                                    "\t`destinationCardsDealt`\tINTEGER,\n" +
                                    "\t`chat`\tTEXT,\n" +
                                    "\t`destinationCardDeck`\tTEXT,\n" +
                                    "\t`destinationCardDiscard`\tTEXT,\n" +
                                    "\t`trainCardDeck`\tTEXT,\n" +
                                    "\t`trainCardDiscard`\tTEXT,\n" +
                                    "\t`faceupTrainCardDeck`\tTEXT,\n" +
                                    "\t`claimedRoutes`\tTEXT,\n" +
                                    "\t`gameHistory`\tTEXT\n" +
                                    ");";
            String initialPlayerSQL = "CREATE TABLE IF NOT EXISTS `Player` (\n" +
                                    "\t`id`\tTEXT NOT NULL UNIQUE,\n" +
                                    "\t`password`\tTEXT NOT NULL,\n" +
                                    "\t`dealtDestinationCards`\tTEXT,\n" +
                                    "\t`destinationCardHand`\tTEXT,\n" +
                                    "\t`trainCards`\tTEXT,\n" +
                                    "\t`routesClaimed`\tTEXT,\n" +
                                    "\t`trainPieces`\tINTEGER,\n" +
                                    "\t`score`\tINTEGER,\n" +
                                    "\t`color`\tTEXT,\n" +
                                    "\t`travelRate`\tINTEGER,\n" +
                                    "\t`destinationCardPoints`\tINTEGER,\n" +
                                    "\t`destinationCardPointsLost`\tINTEGER\n" +
                                    ");";
            String initialCommandSQL = "CREATE TABLE IF NOT EXISTS `Command` (\n" +
                                    "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                    "\t`data`\tTEXT\n" +
                                    ");";

            Statement stmt = connection.createStatement();
            stmt.execute(initialUserSQL);
            stmt.execute(initialGameSQL);
            stmt.execute(initialPlayerSQL);
            stmt.execute(initialCommandSQL);
            stmt.close();
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
        System.out.println("Saving users");
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.UserDao userDao = new UserDao(connection);
        boolean success = userDao.save(users);
        closeConnection(connection);
        return success;
    }

    @Override
    public boolean saveGames(List<Game> games) {
        System.out.println("Saving games");
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.GameDao gameDao = new GameDao(connection);
        boolean success = gameDao.save(games);
        closeConnection(connection);
        return success;
    }

    @Override
    public boolean clearCommandDeltas() {
        System.out.println("Clearing command deltas");
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.CommandDataDao commandDataDao = new CommandDataDao(connection);
        boolean success = commandDataDao.clear();
        closeConnection(connection);
        return success;
    }

    @Override
    public List<Player> getUsers() {
        System.out.println("Loading users");
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.UserDao userDao = new UserDao(connection);
        List<Player> users = userDao.getUsers();
        closeConnection(connection);
        return users;
    }

    @Override
    public List<Game> getGames() {
        System.out.println("Loading games");
        Connection connection = createConnection();
        weber.kaden.common.injectedInterfaces.persistence.GameDao gameDao = new GameDao(connection);
        List<Game> games = gameDao.getGames();
        closeConnection(connection);
        return games;
    }

    @Override
    public List<CommandData> getCommands() {
        System.out.println("Loading commands");
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

    @Override
    public boolean clear() {
        System.out.println("Clearing database");
        Connection connection = createConnection();
        return new GameDao(connection).clear() &&
                new PlayerDao(connection).clear() &&
                new UserDao(connection).clear() &&
                new CommandDataDao(connection).clear();
    }
}
