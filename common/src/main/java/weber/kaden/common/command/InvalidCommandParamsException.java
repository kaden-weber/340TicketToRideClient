package weber.kaden.common.command;

public class InvalidCommandParamsException extends Exception {
    public InvalidCommandParamsException(String message) {
        super(message);
    }

    public InvalidCommandParamsException() {
        super();
    }
}
