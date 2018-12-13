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

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Player;
import weber.kaden.common.model.PlayerColors;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;

public class PlayerDao extends Dao implements weber.kaden.common.injectedInterfaces.persistence.PlayerDao {
    public PlayerDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Player> players) {
        clear();
        for (int i = 0; i < players.size(); i++) {
            String sql = "INSERT INTO Player(id, password, dealtDestinationCards, destinationCardHand, trainCards, routesClaimed, trainPieces, score, color, travelRate, destinationCardPoints, destinationCardPointsLost) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                String id, password, dealtDestinationCards, destinationCardHand, trainCards, routesClaimed, color;
                int trainPieces, score, travelRate, hasLongestPath, destinationCardPoints, destinationCardPointsLost;

                //Setup Strings, this is where most of the GSON serialization issues might be
                id = players.get(i).getID();
                password = players.get(i).getPassword();
                Gson gson = new Gson();
                dealtDestinationCards = gson.toJson(players.get(i).getDealtDestinationCards());
                destinationCardHand = gson.toJson(players.get(i).getDestinationCardHand());
                trainCards = gson.toJson(players.get(i).getTrainCards());
                routesClaimed = gson.toJson(players.get(i).getRoutesClaimed());
                color = players.get(i).getColor().toString();

                //Setup integers
                trainPieces = players.get(i).getNumberOfTrains();
                score = players.get(i).getScore();
                travelRate = players.get(i).getTravelRate();
                destinationCardPoints = players.get(i).getDestinationCardPoints();
                destinationCardPointsLost = players.get(i).getDestinationCardPointsLost();

                // Inject Strings and ints
                PreparedStatement pstmt = getConnection().prepareStatement(sql);

                pstmt.setString(1, id);
                pstmt.setString(2, password);
                pstmt.setString(3, dealtDestinationCards);
                pstmt.setString(4, destinationCardHand);
                pstmt.setString(5, trainCards);
                pstmt.setString(6, routesClaimed);
                pstmt.setInt(7, trainPieces);
                pstmt.setInt(8, score);
                pstmt.setString(9, color);
                pstmt.setInt(10, travelRate);
                pstmt.setInt(11, destinationCardPoints);
                pstmt.setInt(12, destinationCardPointsLost);

                // Execute update
                pstmt.executeUpdate();
                pstmt.close();
            }
            catch (SQLException e) {
                System.err.println("Error while saving players");
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Player> getPlayers() {
        try {
            String sql = "SELECT * FROM Player";

            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            List<Player> returnPlayers = new ArrayList<>();
            while(rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                Gson gson = new Gson();

                String dealtDestinationCards = rs.getString("dealtDestinationCards");
                Type destinationCardListType = new TypeToken<ArrayList<DestinationCard>>(){}.getType();
                List<DestinationCard> dealtDestinationCardList = gson.fromJson(dealtDestinationCards, destinationCardListType);

                String destinationCardHand = rs.getString("destinationCardHand");
                List<DestinationCard> destinationCardHandList = gson.fromJson(destinationCardHand, destinationCardListType);

                String trainCards = rs.getString("trainCards");
                Type trainCardListType = new TypeToken<ArrayList<TrainCard>>(){}.getType();
                List<TrainCard> trainCardList = gson.fromJson(trainCards, trainCardListType);

                String routesClaimed = rs.getString("routesClaimed");
                Type routesClaimedListType = new TypeToken<ArrayList<Route>>(){}.getType();
                List<Route> routesClaimedList = gson.fromJson(routesClaimed, routesClaimedListType);

                String color = rs.getString("color");

                int trainPieces = rs.getInt("trainPieces");
                int score = rs.getInt("score");
                int travelRate = rs.getInt("travelRate");
                int destinationCardPoints = rs.getInt("destinationCardPoints");
                int destinationCardPointsLost = rs.getInt("destinationCardPointsLost");

                Player newPlayer = new Player(id, password, dealtDestinationCardList, destinationCardHandList, trainCardList, routesClaimedList, trainPieces, score);
                newPlayer.setColor(PlayerColors.valueOf(color));
                newPlayer.setTravelRate(travelRate);
                newPlayer.setDestinationCardPoints(destinationCardPoints);
                newPlayer.setDestinationCardPointsLost(destinationCardPointsLost);

                returnPlayers.add(newPlayer);
            }

            rs.close();
            pstmt.close();

            return returnPlayers;
        }
        catch (SQLException e) {
            System.err.println("Error while loading players");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean clear() {
        String sql = "DELETE FROM Player";
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

    public List<Player> getPlayersByListOfIDs(List<String> playerIDs) {
        try {
            String sql = "SELECT * FROM Player WHERE ";

            for (int i = 0; i < playerIDs.size(); i++) {
                if (i != playerIDs.size() - 1) {
                    sql += "id='" + playerIDs.get(i) + "' AND ";
                }
                else {
                    sql += "id='" + playerIDs.get(i) + "'";
                }
            }

            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            List<Player> returnPlayers = new ArrayList<>();
            while(rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                Gson gson = new Gson();

                String dealtDestinationCards = rs.getString("dealtDestinationCards");
                Type destinationCardListType = new TypeToken<ArrayList<DestinationCard>>(){}.getType();
                List<DestinationCard> dealtDestinationCardList = gson.fromJson(dealtDestinationCards, destinationCardListType);

                String destinationCardHand = rs.getString("destinationCardHand");
                List<DestinationCard> destinationCardHandList = gson.fromJson(destinationCardHand, destinationCardListType);

                String trainCards = rs.getString("trainCards");
                Type trainCardListType = new TypeToken<ArrayList<TrainCard>>(){}.getType();
                List<TrainCard> trainCardList = gson.fromJson(trainCards, trainCardListType);

                String routesClaimed = rs.getString("routesClaimed");
                Type routesClaimedListType = new TypeToken<ArrayList<Route>>(){}.getType();
                List<Route> routesClaimedList = gson.fromJson(routesClaimed, routesClaimedListType);

                String color = rs.getString("color");

                int trainPieces = rs.getInt("trainPieces");
                int score = rs.getInt("score");
                int travelRate = rs.getInt("travelRate");
                int destinationCardPoints = rs.getInt("destinationCardPoints");
                int destinationCardPointsLost = rs.getInt("destinationCardPointsLost");

                Player newPlayer = new Player(id, password, dealtDestinationCardList, destinationCardHandList, trainCardList, routesClaimedList, trainPieces, score);
                newPlayer.setColor(PlayerColors.valueOf(color));
                newPlayer.setTravelRate(travelRate);
                newPlayer.setDestinationCardPoints(destinationCardPoints);
                newPlayer.setDestinationCardPointsLost(destinationCardPointsLost);

                returnPlayers.add(newPlayer);
            }

            rs.close();
            pstmt.close();

            return returnPlayers;
        }
        catch (SQLException e) {
            System.err.println("Error while loading players");
            e.printStackTrace();
            return null;
        }
    }
}
