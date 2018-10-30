package weber.kaden.myapplication.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.myapplication.serverProxy.ServerProxy;
import java.util.UUID;

public class ClientFacade {

    public void login(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.LOGIN);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null) {
        	throw new Exception("Login resulted in null");
        }
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }

	    Model.getInstance().setCurrentUser(username);
        if (Model.getInstance().getPlayer(username) == null) {
            Model.getInstance().addPlayer(new Player(username,password));
        }
    }

    public void register(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.REGISTER);
        executeLocalCommand(commandData);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null) {
        	throw new Exception("Register resulted in null");
        }
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }

        Model.getInstance().setCurrentUser(username);
    }

    private void executeLocalCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getInstance().getCommand(commandData);
        Results results = command.execute();
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }

    public List<Game> getGames(){
        List<String> params = new ArrayList<>(Arrays.asList(Model.getInstance().getCurrentUser()));
        CommandData commandData = new CommandData(params, CommandType.POLLGAMESLIST);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null || results.getData() == null) {
            return new ArrayList<>();
        }
        return (List<Game>) (results.getData());
    }

    public Results createGame(String username, String gameName) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameName, UUID.randomUUID().toString())));
        CommandData commandData = new CommandData(params, CommandType.CREATEGAME);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }
        return results;
    }

    public void joinGame(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.JOINGAME);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
        Model.getInstance().updateGame((Game)results.getData());
        Model.getInstance().setCurrentGame((Game)results.getData());
    }

    public void startGame(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.STARTGAME);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }
    public void setupGame(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.SETUPGAME);

        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }
    public void exitLobby(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.LEAVEGAME);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
        Model.getInstance().updateGame(Model.getInstance().getGame(gameID));
        Model.getInstance().setCurrentGame(null);
    }

    public Game getCurrentGame() {
    	return Model.getInstance().getCurrentGame();
    }

    public String getCurrentUser() {
        return Model.getInstance().getCurrentUser();
    }

    public Game getUpdatedGame(Game game) throws Exception{
        List<String> params = new ArrayList<>();
        params.add(getCurrentUser());
        params.add(game.getID());
        CommandData commandData = new CommandData(params, CommandType.POLLGAME);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
        return (Game) results.getData();
    }

    public void sendMessage(String gameId, String userId, String message) throws Exception{
        List<String> params = new ArrayList<>((Arrays.asList(gameId, userId, message)));
        CommandData commandData = new CommandData(params, CommandType.CHAT);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }
}