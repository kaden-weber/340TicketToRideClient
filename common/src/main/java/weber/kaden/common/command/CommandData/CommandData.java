package weber.kaden.common.command.CommandData;

import java.util.List;

import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Model;

public class CommandData {

    private List<String> params;
    private CommandType type;
    private List<Object> data;
    private String commandID;
    private static int lastID = 0;

    public CommandData(List<String> params, CommandType type) {
        this.params = params;
        this.type = type;
        this.commandID = newCommandID();
    }

    public CommandData(List<String> params, CommandType type, List<Object> data) {
        this.params = params;
        this.type = type;
        this.data = data;
        this.commandID = newCommandID();
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> data) {
        this.params = data;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getCommandID() {
        return commandID;
    }

    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    private String newCommandID() {
        switch (this.type) {
            case CHAT:
            case CLAIMROUTE:
            case DRAWDESTINATIONCARDS:
            case DRAWTRAINCARDFROMFACEUP:
            case DRAWTRAINCARDFROMDECK:
                return Model.getInstance().getCurrentUser() + Integer.toString(lastID++);
            default:
                return null;
        }
    }
}
