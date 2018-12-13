package com.example.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Player;

public class UserDao extends Dao implements weber.kaden.common.injectedInterfaces.persistence.UserDao {
    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Player> users) {
        clear();
        for (int i = 0; i < users.size(); i++) {
            String sql = "INSERT INTO User(id, password) " +
                    "VALUES(?,?)";
            try {
                PreparedStatement pstmt = getConnection().prepareStatement(sql);

                pstmt.setString(1, users.get(i).getID());
                pstmt.setString(2, users.get(i).getPassword());

                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error while saving user: " + users.get(i).getID());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Player> getUsers() {
        try {
            String sql = "SELECT * FROM User";

            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            List<Player> returnUsers = new ArrayList<>();
            while (rs.next()) {
                String newID = rs.getString("id");
                String newPassword = rs.getString("password");

                Player newUser = new Player(newID, newPassword);
                returnUsers.add(newUser);
            }

            rs.close();
            pstmt.close();

            return returnUsers;
        }
        catch (SQLException e) {
            System.err.println("Error while loading users");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean clear() {
        String sql = "DELETE FROM User";
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
}
