package weber.kaden.common.results;

import java.util.List;

import weber.kaden.common.model.Game;

public class ListResults implements Results{

    private List<Game> data;
    private boolean success;
    private String errorInfo;

    public ListResults(List<Game> data, boolean success, String errorInfo) {
        this.data = data;
        this.success = success;
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(List<Game> data) {
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
