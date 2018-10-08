package weber.kaden.common;

import java.util.List;

import weber.kaden.common.model.Game;

public class GameResults implements Results{

    private Game data;
    private boolean success;
    private String errorInfo;

    public GameResults(Game data, boolean success, String errorInfo) {
        this.data = data;
        this.success = success;
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Game data) {
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
