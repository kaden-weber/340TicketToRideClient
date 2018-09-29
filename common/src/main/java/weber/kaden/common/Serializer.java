package weber.kaden.common;

import com.google.gson.Gson;

import weber.kaden.common.command.CommandData;

public class Serializer {
    private Gson gson;
    public Serializer() {
        gson = new Gson();
    }

    public String serializeCommandData(CommandData data){
        return gson.toJson(data);
    }

    public CommandData deserializeCommandData(String data) {return gson.fromJson(data, CommandData.class); }

    public Results deserializeResults(String string){
        return gson.fromJson(string, Results.class);
    }

    public String serializeResults(Results r){
        return gson.toJson(r);
    }
}
