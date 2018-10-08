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
        if (type.equals(CommandType.POLLGAMESLIST)) {
            return deserializeResultsList(string);
        } else if (type.equals(CommandType.CREATEGAME)) {
            return deserializeResultsGame(string);
        }
        return deserializeResults(string);
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

    public String serializeResults(Results r){
        return gson.toJson(r);
    }
}
