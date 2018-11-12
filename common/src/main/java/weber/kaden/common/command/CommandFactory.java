package weber.kaden.common.command;

import weber.kaden.common.command.CommandClasses.ChatCommand;
import weber.kaden.common.command.CommandClasses.ClaimRouteCommand;
import weber.kaden.common.command.CommandClasses.CreateGameCommand;
import weber.kaden.common.command.CommandClasses.DiscardTrainCardCommand;
import weber.kaden.common.command.CommandClasses.DrawDestinationCardsCommand;
import weber.kaden.common.command.CommandClasses.DrawTrainCardFromDeckCommand;
import weber.kaden.common.command.CommandClasses.DrawTrainCardFromFaceUpCommand;
import weber.kaden.common.command.CommandClasses.FinishTurnCommand;
import weber.kaden.common.command.CommandClasses.JoinGameCommand;
import weber.kaden.common.command.CommandClasses.LeaveGameCommand;
import weber.kaden.common.command.CommandClasses.LoginCommand;
import weber.kaden.common.command.CommandClasses.PollCommandsCommand;
import weber.kaden.common.command.CommandClasses.PollGameCommand;
import weber.kaden.common.command.CommandClasses.PollGamesListCommand;
import weber.kaden.common.command.CommandClasses.RegisterCommand;
import weber.kaden.common.command.CommandClasses.SetTravelRateByPlayerCommand;
import weber.kaden.common.command.CommandClasses.SetUpGameCommand;
import weber.kaden.common.command.CommandClasses.StartGameCommand;
import weber.kaden.common.command.CommandClasses.UseTrainCarsCommand;
import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.command.CommandData.CommandDataClaimRoute;
import weber.kaden.common.command.CommandData.CommandDataDiscardTrainCard;
import weber.kaden.common.command.CommandData.CommandDataDrawDestinationCards;
import weber.kaden.common.command.CommandData.CommandDataDrawTrainCardFromFaceUp;

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
            case SETTRAVELERRATE:
                if(data.getParams().size() < 3){
                    throw new InvalidCommandParamsException("Not enough params provided");
                }
                return new SetTravelRateByPlayerCommand(data.getParams());
            case SETUPGAME:
                if(data.getParams().size() < 2){
                    throw new InvalidCommandParamsException("Not enough params provided");
                }
                return new SetUpGameCommand(data.getParams());
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
                return new CreateGameCommand(data.getParams());
            case JOINGAME:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new JoinGameCommand(data.getParams());
            case STARTGAME:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new StartGameCommand(data.getParams());
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
            case POLLCOMMANDS:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new PollCommandsCommand(data.getParams(), mCommandManager);
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
                return new DrawDestinationCardsCommand(data.getParams(),
                        ((CommandDataDrawDestinationCards) data).getCardsKept(),
                        ((CommandDataDrawDestinationCards) data).getCardsDiscarded());
            case DRAWTRAINCARDFROMDECK:
                if (data.getParams().size() < 2){
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new DrawTrainCardFromDeckCommand(data.getParams());
            case DISCARDTRAINCARD:
                return new DiscardTrainCardCommand(data.getParams(),
                        ((CommandDataDiscardTrainCard) data).getCard());
            case DRAWTRAINCARDFROMFACEUP:
                return new DrawTrainCardFromFaceUpCommand(data.getParams(),
                        ((CommandDataDrawTrainCardFromFaceUp) data).getCardIndex());
            case CLAIMROUTE:
                return new ClaimRouteCommand(data.getParams(),
                        ((CommandDataClaimRoute) data). getRoute());
            case FINISHTURN:
                if (data.getParams().size() < 1) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new FinishTurnCommand(data.getParams());
            case USETRAINCARS:
                if (data.getParams().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new UseTrainCarsCommand(data.getParams());
            default:
                return null;
        }
    }
}