package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;

public class CommandFactory {
    private static CommandFactory commandFactory = null;
    private static iCommandManager mCommandManager;
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
        return mCommandManager;
    }

    public static void setCommandManager(iCommandManager commandManager) {
        mCommandManager = commandManager;
    }

    public Command getCommand(CommandData data) throws InvalidCommandParamsException {
        switch (data.getType()) {
            case LOGIN:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new LoginCommand(data.getParams());
            case REGISTER:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new RegisterCommand(data.getParams());
            case CREATEGAME:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new CreateGameCommand(data.getParams(), newCommandID());
            case JOINGAME:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new JoinGameCommand(data.getParams(), newCommandID());
            case STARTGAME:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new StartGameCommand(data.getParams(), newCommandID());
            case POLLGAMESLIST:
                if (data.getParams().size() < 1) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new PollGamesListCommand(data.getParams(), mCommandManager);
	        case POLLGAME:
	        	if (data.getParams().size() < 2) {
	        		throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
		        }
		        return new PollGameCommand(data.getParams(), mCommandManager);
            case LEAVEGAME:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new LeaveGameCommand(data.getParams());
            case CHAT:
                if (data.getParams().size() < 3) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new ChatCommand(data.getParams());
            case DRAWDESTINATIONCARDS:
                if (data.getParams().size() < 2 && data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new DrawDestinationCardsCommand(data.getParams().get(0), data.getParams().get(1), (List<DestinationCard>)data.getData().get(0), (List<DestinationCard>)data.getData().get(1));
            case DRAWTRAINCARDS:
                if (data.getParams().size() < 2 && data.getData().size() < 1){
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new DrawTrainCardsCommand(data.getParams(), (List<TrainCard>) data.getData().get(0));
            default:
                return null;
        }
    }

    private String newCommandID() {
        return Model.getInstance().getCurrentUser() + Integer.toString(this.lastID);
    }
}