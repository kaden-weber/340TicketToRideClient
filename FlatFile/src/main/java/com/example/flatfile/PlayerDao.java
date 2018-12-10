package com.example.flatfile;

import com.google.gson.Gson;

import java.io.File;
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

        }
    }

    @Override
    public boolean save(List<Player> players) {
        //String serializer.serialize(players);
        return false;
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
