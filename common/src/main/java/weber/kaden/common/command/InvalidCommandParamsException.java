package weber.kaden.common.command;

public class InvalidCommandParamsException extends Exception {
    InvalidCommandParamsException(String message) {
        super(message);
    }

    public InvalidCommandParamsException() {
        super();
    }
}
