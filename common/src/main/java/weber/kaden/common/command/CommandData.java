package main.java.weber.kaden.common.command;

import java.util.List;

public class CommandData {

    private List<String> data;
    private CommandType type;

    public CommandData(List<String> data, CommandType type) {
        this.data = data;
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
