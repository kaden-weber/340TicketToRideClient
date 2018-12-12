package com.example.sql;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.ChatMessage;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;

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
                String id, gameName, gameState, lastPlayer;
                int currentPlayer;
                boolean destinationCardsDealt;

                id = rs.getString("id");
                gameName = rs.getString("gameName");
                gameState = rs.getString("gameState");

                Gson gson = new Gson();

                String playersString = rs.getString("playersList");
                Type playerIDsListType = new TypeToken<ArrayList<String>>(){}.getType();
                List<String> playerIDsList = gson.fromJson(playersString, playerIDsListType);
                List<Player> players = getPlayersByIDs(playerIDsList);

                lastPlayer = rs.getString("lastPlayer");
                String chatString = rs.getString("chat");
                Type chatListType = new TypeToken<ArrayList<ChatMessage>>(){}.getType();
                List<ChatMessage> chat = gson.fromJson(chatString, chatListType);

                String destinationCardDeckString = rs.getString("destinationCardDeck");
                Type destinationCardDeckListType = new TypeToken<ArrayList<DestinationCard>>(){}.getType();
                List<DestinationCard> destinationCardDeck = gson.fromJson(destinationCardDeckString, destinationCardDeckListType);

                String destinationCardDiscardString = rs.getString("destinationCardDiscard");
                List<DestinationCard> destinationCardDiscard = gson.fromJson(destinationCardDiscardString, destinationCardDeckListType);

                String trainCardDeckString = rs.getString("trainCardDeck");
                Type trainCardDeckListType = new TypeToken<ArrayList<TrainCard>>(){}.getType();
                List<TrainCard> trainCardDeck = gson.fromJson(trainCardDeckString, trainCardDeckListType);

                String trainCardDiscardString = rs.getString("trainCardDiscard");
                List<TrainCard> trainCardDiscard = gson.fromJson(trainCardDiscardString, trainCardDeckListType);

                String faceupTrainCardDeckString = rs.getString("faceupTrainCardDeck");
                List<TrainCard> faceupTrainCardDeck = gson.fromJson(faceupTrainCardDeckString, trainCardDeckListType);

                String claimedRoutesString = rs.getString("claimedRoutes");
                Type routeListType = new TypeToken<ArrayList<Route>>(){}.getType();
                List<Route> claimedRoutes = gson.fromJson(claimedRoutesString, routeListType);

                String gameHistoryString = rs.getString("gameHistory");
                Type gameHistoryListType = new TypeToken<ArrayList<CommandData>>(){}.getType();
                List<CommandData> gameHistory = gson.fromJson(gameHistoryString, gameHistoryListType);

                currentPlayer = rs.getInt("currentPlayer");
                destinationCardsDealt = rs.getInt("destinationCardsDealt") == 1;


                Game newGame = new Game(players, id, gameName);
                newGame.setGameState(new Game.GameState(gameState));
                newGame.setLastPlayer(lastPlayer);
                newGame.setChat(chat);
                newGame.setDestinationCardDeck(destinationCardDeck);
                newGame.setDestinationCardDiscard(destinationCardDiscard);
                newGame.setTrainCardDeck(trainCardDeck);
                newGame.setTrainCardDiscard(trainCardDiscard);
                newGame.setFaceupTrainCardDeck(faceupTrainCardDeck);
                newGame.setClaimedRoutes(claimedRoutes);
                newGame.setGameHistory(gameHistory);
                newGame.setCurrentPlayer(currentPlayer);
                newGame.setDestinationCardsDealt(destinationCardsDealt);
            }

            return returnGames;

        }
        catch (SQLException e) {
            System.err.println("Error while saving games");
            return null;
        }
    }

    @Override
    public boolean clear() {
        String sql = "DELETE FROM Game";
        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    private List<Player> getPlayersByIDs(List<String> playerIDs) {
        PlayerDao playerDao = new PlayerDao(getConnection());
        return playerDao.getPlayersByListOfIDs(playerIDs);
    }
}
