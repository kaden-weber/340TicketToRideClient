package main.java.weber.kaden.common.command;

public class CommandFactory {
    private static CommandFactory commandFactory = null;

    public static CommandFactory getInstance() {
        if (commandFactory == null) {
            commandFactory = new CommandFactory();
        }
        return commandFactory;
    }

    private CommandFactory() {}

    public static Command getCommand(CommandData data) throws InvalidCommandParamsException {
        switch (data.getType()) {
            case LOGIN:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new LoginCommand(data.getData());
            case REGISTER:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new RegisterCommand(data.getData());
            case CREATEGAME:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new CreateGameCommand(data.getData());
            case JOINGAME:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new JoinGameCommand(data.getData());
            case STARTGAME:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new StartGameCommand(data.getData());
            default:
                return null;
        }
    }
}

