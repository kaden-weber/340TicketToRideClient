package weber.kaden.common.results;

public class GenericResults implements Results {

    private Object data;
    private boolean success;
    private String errorInfo;

    public GenericResults() {}

    public GenericResults(Object data, boolean success, String errorInfo) {
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
