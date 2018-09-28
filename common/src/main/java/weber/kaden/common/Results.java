package main.java.weber.kaden.common;

public class Results {

    private Object data;
    private boolean success;
    private String errorInfo;

    public Results(Object data, boolean success, String errorInfo) {
        this.data = data;
        this.success = success;
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
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
