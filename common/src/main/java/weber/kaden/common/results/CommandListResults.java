package weber.kaden.common.results;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;

public class CommandListResults implements Results{
    private List<CommandData> data;
    private boolean success;
    private String errorInfo;

    public CommandListResults(List<CommandData> data, boolean success, String errorInfo) {
        this.data = data;
        this.success = success;
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(List<CommandData> data) {
        this.data = data;
    }

    public boolean success() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
