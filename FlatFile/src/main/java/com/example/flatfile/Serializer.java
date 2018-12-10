package com.example.flatfile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class Serializer {

    public String serialize(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public List<Game> deserializeGames(String serializedData) {
        Gson gson = new Gson();
        try {
            Games games = gson.fromJson(serializedData, Games.class);
            return games.getGames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Player> deserializePlayers(String serializedData) {
        Gson gson = new Gson();
        try {
            Players players = gson.fromJson(serializedData, Players.class);
            return players.getPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
