package com.example.sql;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.GameDao;
import weber.kaden.common.model.ChatMessage;
import weber.kaden.common.model.Game;

public class SQLGameDao extends SQLDao implements GameDao {
    public SQLGameDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Game> games) {
        for (int i = 0; i < games.size(); i++) {
            String sql = "INSERT INTO Game(id, gameName, gameState, lastPlayer, currentPlayer, destinationCardsDealt, chat, destinationCardDeck, destinationCardDiscard, trainCardDeck, trainCardDiscard, faceupTrainCardDeck, claimedRoutes, gameHistory) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                String id, gameName, gameState, lastPlayer, chat, destinationCardDeck, destinationCardDiscard, trainCardDeck, trainCardDiscard, faceupTrainCardDeck, claimedRoutes, gameHistory;
                int currentPlayer, destinationCardsDealt;

                Gson gson = new Gson();

                id = games.get(i).getID();
                gameName = games.get(i).getGameName();
                gameState = games.get(i).getGameState().toString();
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
                pstmt.setString(4, lastPlayer);
                pstmt.setInt(5, currentPlayer);
                pstmt.setInt(6, destinationCardsDealt);
                pstmt.setString(7, chat);
                pstmt.setString(8, destinationCardDeck);
                pstmt.setString(9, destinationCardDiscard);
                pstmt.setString(10, trainCardDeck);
                pstmt.setString(11, trainCardDiscard);
                pstmt.setString(12, faceupTrainCardDeck);
                pstmt.setString(13, claimedRoutes);
                pstmt.setString(14, gameHistory);

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
        return null;
    }
}
