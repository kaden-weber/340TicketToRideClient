package com.example.sql;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class GameDao extends Dao implements weber.kaden.common.injectedInterfaces.persistence.GameDao {
    public GameDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Game> games) {
        for (int i = 0; i < games.size(); i++) {
            String sql = "INSERT INTO Game(id, gameName, gameState, playerIDs, lastPlayer, currentPlayer, destinationCardsDealt, chat, destinationCardDeck, destinationCardDiscard, trainCardDeck, trainCardDiscard, faceupTrainCardDeck, claimedRoutes, gameHistory) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                String id, gameName, gameState, playersList, lastPlayer, chat, destinationCardDeck, destinationCardDiscard, trainCardDeck, trainCardDiscard, faceupTrainCardDeck, claimedRoutes, gameHistory;
                int currentPlayer, destinationCardsDealt;

                List<Player> players = games.get(i).getPlayers();
                List<String> playerIDs = savePlayersFromGame(players);

                Gson gson = new Gson();

                id = games.get(i).getID();
                gameName = games.get(i).getGameName();
                gameState = games.get(i).getGameState().toString();
                playersList = gson.toJson(playerIDs);
                lastPlayer = games.get(i).getLastPlayer();
                chat = gson.toJson(games.get(i).getChat());
                destinationCardDeck = gson.toJson(games.get(i).getDestinationCardDeck());
                destinationCardDiscard = gson.toJson(games.get(i).getDestinationCardDiscard());
                trainCardDeck = gson.toJson(games.get(i).getTrainCardDeck());
                trainCardDiscard = gson.toJson(games.get(i).getTrainCardDiscard());
                faceupTrainCardDeck = gson.toJson(games.get(i).getFaceUpTrainCardDeck());
                claimedRoutes = gson.toJson(games.get(i).getClaimedRoutes());
                gameHistory = gson.toJson(games.get(i).getGameHistory());

                currentPlayer = games.get(i).getCurrentPlayerIndex();
                destinationCardsDealt = games.get(i).isDestinationCardsDealt() ? 1 : 0;

                PreparedStatement pstmt = getConnection().prepareStatement(sql);

                pstmt.setString(1, id);
                pstmt.setString(2, gameName);
                pstmt.setString(3, gameState);
                pstmt.setString(4, playersList);
                pstmt.setString(5, lastPlayer);
                pstmt.setInt   (6, currentPlayer);
                pstmt.setInt   (7, destinationCardsDealt);
                pstmt.setString(8, chat);
                pstmt.setString(9, destinationCardDeck);
                pstmt.setString(10, destinationCardDiscard);
                pstmt.setString(11, trainCardDeck);
                pstmt.setString(12, trainCardDiscard);
                pstmt.setString(13, faceupTrainCardDeck);
                pstmt.setString(14, claimedRoutes);
                pstmt.setString(15, gameHistory);

                pstmt.executeUpdate();
                pstmt.close();
            }
            catch (SQLException e) {
                System.err.println("Error while saving games");
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Game> getGames() {
        String sql = "SELECT * FROM Game";

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            List<Game> returnGames = new ArrayList<>();
            while (rs.next()) {
                String id, gameName, gameState, playersList, lastPlayer, chat, destinationCardDeck, destinationCardDiscard, trainCardDeck, trainCardDiscard, faceupTrainCardDeck, claimedRoutes, gameHistory;
                int currentPlayer, destinationCardsDealt;

                id = rs.getString("id");
                gameName = rs.getString("gameName");
                gameState = rs.getString("gameState");

                Gson gson = new Gson();

                playersList = rs.getString("playersList");
                lastPlayer = rs.getString("lastPlayer");
                chat = rs.getString("chat");
                destinationCardDeck = rs.getString("destinationCardDeck");
                destinationCardDiscard = rs.getString("destinationCardDiscard");
                trainCardDeck = rs.getString("trainCardDeck");
                trainCardDiscard = rs.getString("trainCardDiscard");
                faceupTrainCardDeck = rs.getString("faceupTrainCardDeck");
                claimedRoutes = rs.getString("claimedRoutes");
                gameHistory = rs.getString("gameHistory");

                currentPlayer = rs.getInt("currentPlayer");
                destinationCardsDealt = rs.getInt("destinationCardsDealt");



            }

            return returnGames;

        }
        catch (SQLException e) {
            System.err.println("Error while saving games");
            return null;
        }
    }

    private List<String> savePlayersFromGame(List<Player> players) {
        PlayerDao playerDao = new PlayerDao(getConnection());
        playerDao.save(players);
        List<String> playerIDs = new ArrayList<>();
        for (int j = 0; j < players.size(); j++) {
            playerIDs.add(players.get(j).getID());
        }
        return playerIDs;
    }

    private List<Player> getPlayersForGame(List<String> playerIDs) {
        PlayerDao playerDao = new PlayerDao(getConnection());
        return playerDao.getPlayersByListOfIDs(playerIDs);
    }
}
