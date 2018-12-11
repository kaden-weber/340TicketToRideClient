package com.example.flatfile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.command.CommandData.CommandDataClaimRoute;
import weber.kaden.common.command.CommandData.CommandDataDiscardTrainCard;
import weber.kaden.common.command.CommandData.CommandDataDrawDestinationCards;
import weber.kaden.common.command.CommandData.CommandDataDrawTrainCardFromFaceUp;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public class Serializer {

    public String serialize(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public List<Game> deserializeGames(String serializedData) {
        Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
        return new Gson().fromJson(serializedData, listType);
    }

    public List<Player> deserializePlayers(String serializedData) {
        Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
        return new Gson().fromJson(serializedData, listType);
    }

    public List<CommandData> deserializeCommandData(String data) {
        List<CommandData> commandDataList = new ArrayList<>();
        JsonArray array = new JsonParser().parse(data).getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonElement command = array.get(i);
            CommandData commandData = deserializeCommandData(command);
            commandDataList.add(commandData);
        }
        return commandDataList;
    }

    private CommandData deserializeCommandData(JsonElement data) {
        Gson gson = new Gson();
        CommandData tempData = gson.fromJson(data, CommandData.class);
        switch (tempData.getType()) {
            case DRAWDESTINATIONCARDS:
                return gson.fromJson(data, CommandDataDrawDestinationCards.class);
            case CLAIMROUTE:
                return gson.fromJson(data, CommandDataClaimRoute.class);
            case DISCARDTRAINCARD:
                return gson.fromJson(data, CommandDataDiscardTrainCard.class);
            case DRAWTRAINCARDFROMFACEUP:
                return gson.fromJson(data, CommandDataDrawTrainCardFromFaceUp.class);
        }
        return gson.fromJson(data, CommandData.class);
    }

}
