package com.example.flatfile;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import weber.kaden.common.model.Game;

public class GameDao implements weber.kaden.common.injectedInterfaces.persistence.GameDao {

    private String filePath;
    private File file;

    GameDao(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean save(List<Game> games) {
        // serialize list of games
        Serializer serializer = new Serializer();
        String serializedGames = serializer.serialize(games);
        // write to file
        BufferedWriter writer = null;
        boolean success = false;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(serializedGames);
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
    public List<Game> getGames() {
        Gson gson = new Gson();
        try {
            String serializedGames = new String(Files.readAllBytes(Paths.get(filePath)));
            return gson.fromJson(serializedGames, List.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
