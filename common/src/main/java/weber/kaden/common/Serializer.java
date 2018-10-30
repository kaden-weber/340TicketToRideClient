package weber.kaden.common;

import com.google.gson.Gson;

import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandType;

public class Serializer {
    private Gson gson;
    public Serializer() {
        gson = new Gson();
    }

    public String serializeCommandData(CommandData data){
        return gson.toJson(data);
    }

    public CommandData deserializeCommandData(String data) {return gson.fromJson(data, CommandData.class); }

    public Results deserializeResults(String string, CommandType type) {
        switch (type) {
            case POLLGAMESLIST:
                return deserializeResultsList(string);
            case CREATEGAME:
                return deserializeResultsGame(string);
            case POLLGAME:
                return deserializeResultsGame(string);
            case JOINGAME:
                return deserializeResultsGame(string);
            case POLLCOMMANDS:
                return deserializeResultsCommandList(string);
            default:
                return deserializeResults(string);
        }
    }

    public Results deserializeResults(String string){
        return gson.fromJson(string, GenericResults.class);
    }

    public Results deserializeResultsList(String string) {
        return gson.fromJson(string, ListResults.class);
    }

    public Results deserializeResultsGame(String string) {
        return gson.fromJson(string, GameResults.class);
    }

    public Results deserializeResultsCommandList(String string) {
        return gson.fromJson(string, CommandListResults.class);
    }

    public String serializeResults(Results r){
        return gson.toJson(r);
    }
}
