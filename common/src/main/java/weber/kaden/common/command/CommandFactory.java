package weber.kaden.common.command;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import sun.security.krb5.internal.crypto.Des;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Route;
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

    public Command getCommand(CommandData data) throws InvalidCommandParamsException, Exception {
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
                if (data.getParams().size() < 2 && data.getData().size() < 2) {
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                List<DestinationCard> data1 = new ArrayList<>();
                Iterator iterator = ((ArrayList) data.getData().get(0)).iterator();
                while (iterator.hasNext()) {
                    data1.add((DestinationCard) deserializeData(CommandType.DRAWDESTINATIONCARDS,
                            ((LinkedTreeMap) iterator.next())));
                }
                List<DestinationCard> data2 = new ArrayList<>();
                Iterator iterator2 = ((ArrayList) data.getData().get(1)).iterator();
                while (iterator2.hasNext()) {
                    data1.add((DestinationCard) deserializeData(CommandType.DRAWDESTINATIONCARDS,
                            ((LinkedTreeMap) iterator2.next())));
                }
                return new DrawDestinationCardsCommand(data.getParams().get(0), data.getParams().get(1),
                        data1, data2);
            case DRAWTRAINCARDFROMDECK:
                if (data.getParams().size() < 2){
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new DrawTrainCardFromDeckCommand(data.getParams());
            case DISCARDTRAINCARD:
                if (data.getParams().size() < 2 && data.getData().size() < 1){
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                TrainCard trainCard = (TrainCard) deserializeData(CommandType.DISCARDTRAINCARD, (LinkedTreeMap<Object,Object>)  data.getData().get(0));
                return new DiscardTrainCardCommand(data.getParams(), trainCard);
            case DRAWTRAINCARDFROMFACEUP:
                if (data.getParams().size() < 2 && data.getData().size() < 1){
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                return new DrawTrainCardFromFaceUpCommand(data.getParams(), (Integer)data.getData().get(0));
            case CLAIMROUTE:
                if (data.getParams().size() < 2 && data.getData().size() < 1){
                    throw new InvalidCommandParamsException("Not enough parameters provided to command constructor");
                }
                Route route = (Route) deserializeData(CommandType.CLAIMROUTE, (LinkedTreeMap<Object,Object>)  data.getData().get(0));
                return new ClaimRouteCommand(data.getParams(), route);
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

    public Object deserializeData(CommandType type, LinkedTreeMap<Object, Object> data) throws Exception {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(data).getAsJsonObject();;
        switch (type) {
            case CLAIMROUTE:
                return gson.fromJson(jsonObject, Route.class);
            case DISCARDTRAINCARD:
                return gson.fromJson(jsonObject, TrainCard.class);
            case DRAWDESTINATIONCARDS:
                return gson.fromJson(jsonObject, DestinationCard.class);
            default:
                throw new Exception("dataDeserializer: Not valid object");
        }
    }

}