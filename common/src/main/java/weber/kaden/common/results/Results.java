package weber.kaden.common.results;

public interface Results {

    public Object getData();

    public boolean success();

    public void setSuccess(boolean success);

    public String getErrorInfo();

    public void setErrorInfo(String errorInfo);
}
