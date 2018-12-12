package com.example.flatfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import weber.kaden.common.model.Player;

public class PlayerDao implements weber.kaden.common.injectedInterfaces.persistence.PlayerDao {

    private String filePath;
    private File file;
    private Serializer serializer;

    PlayerDao(String filePath) {
        serializer = new Serializer();
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(List<Player> players) {
        String serializedData = serializer.serialize(players);
        // write to file
        BufferedWriter writer = null;
        boolean success = false;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(serializedData);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null)
                    writer.close();
            } catch (Exception e) {
                System.out.println("Error in closing the BufferedWriter" + e);
                success = false;
            }
        }
        return success;
    }

    @Override
    public List<Player> getPlayers() {
        try {
            String serializedData = new String(Files.readAllBytes(Paths.get(filePath)));
            return serializer.deserializePlayers(serializedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
