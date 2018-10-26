package weber.kaden.common.command;

import java.util.List;

public class CommandData {

    private List<String> params;
    private CommandType type;
    private List<Object> data;

    public CommandData(List<String> params, CommandType type) {
        this.params = params;
        this.type = type;
    }

    public CommandData(List<String> params, CommandType type, List<Object> data) {
        this.params = params;
        this.type = type;
        this.data = data;
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
}
