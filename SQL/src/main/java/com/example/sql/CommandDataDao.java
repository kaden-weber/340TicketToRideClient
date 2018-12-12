package com.example.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.Serializer;
import weber.kaden.common.command.CommandData.CommandData;

public class CommandDataDao extends Dao implements weber.kaden.common.injectedInterfaces.persistence.CommandDataDao {
    public CommandDataDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<CommandData> data) {
        Serializer serializer = new Serializer();
        String sql = "INSERT INTO Command(data) VALUES (?)";
        try {
            for (int i = 0; i < data.size(); i++) {
                String commandData = serializer.serializeCommandData(data.get(i));
                PreparedStatement pstmt = getConnection().prepareStatement(sql);
                pstmt.setString(1, commandData);
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean clear() {
        String sql = "DELETE FROM Command";
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

    @Override
    public boolean add(CommandData data) {
        return false;
    }

    @Override
    public List<CommandData> getCommands() {
        Serializer serializer = new Serializer();
        try {
            String sql = "SELECT * FROM Command";

            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            List<CommandData> returnCommands = new ArrayList<>();
            while(rs.next()) {
                String data = rs.getString("data");
                CommandData commandData = serializer.deserializeCommandData(data);
                returnCommands.add(commandData);
            }
            rs.close();
            pstmt.close();
            return returnCommands;
        }
        catch (SQLException e) {
            System.err.println("Error while loading players");
            return new ArrayList<CommandData>();
        }
    }
}

