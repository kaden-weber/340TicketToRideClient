package weber.kaden.common.command;

import weber.kaden.common.model.Model;

public class CommandFactory {
    private static CommandFactory commandFactory = null;
    private static iCommandManager commandManager;
    private Integer lastID;

    public static CommandFactory getInstance() {
        if (commandFactory == null) {
            commandFactory = new CommandFactory(0);
        }
        return commandFactory;
    }

    private CommandFactory(Integer lastID) {
        this.lastID = lastID;
    }

    public static iCommandManager getCommandManager() {
        return commandManager;
    }

    public static void setCommandManager(iCommandManager commandManager) {
        commandManager = commandManager;
    }

    public Command getCommand(CommandData data) throws InvalidCommandParamsException {
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
                return new CreateGameCommand(data.getData(), newCommandID());
            case JOINGAME:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new JoinGameCommand(data.getData(), newCommandID());
            case STARTGAME:
                if (data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new StartGameCommand(data.getData(), newCommandID());
            case POLL:
                if (data.getData().size() < 3) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new PollCommand(data.getData(), commandManager);
            default:
                return null;
        }
    }

    private String newCommandID() {
        return Model.getInstance().getCurrentUser() + Integer.toString(this.lastID);
    }
}

