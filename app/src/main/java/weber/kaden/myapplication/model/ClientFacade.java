package weber.kaden.myapplication.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.myapplication.serverProxy.ServerProxy;
import weber.kaden.myapplication.ui.LoginPresenter;
import java.util.UUID;

public class ClientFacade {

	private LoginPresenter presenter;

    public boolean login(String username, String password) throws Exception {
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

        return true;
    }

    public boolean register(String username, String password) throws Exception {
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

        return true;
    }

    public boolean executeLocalCommand(CommandData commandData) throws Exception {
        Command command = CommandFactory.getInstance().getCommand(commandData);
        Results results = command.execute();
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
        }

        return true;

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
//        Command command = CommandFactory.getInstance().getCommand(commandData);
//        String gameID = ((Game)command.execute().getData()).getID();
//        params.add(gameID);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }
        return results;
    }

    public void joinGame(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.JOINGAME);
        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }

        Model.getInstance().setCurrentGame((Game)results.getData());
    }

    public void startGame(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.STARTGAME);
        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }

    public void exitLobby(String username, String gameID) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
        CommandData commandData = new CommandData(params, CommandType.LEAVEGAME);
        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()) {
            throw new Exception(results.getErrorInfo());
        }

        Model.getInstance().setCurrentGame(null);
    }

    public Game getCurrentGame() {
    	return Model.getInstance().getCurrentGame();
    }

    public String getCurrentUser() {
        return Model.getInstance().getCurrentUser();
    }

    public Game getUpdatedGame(Game game) throws Exception{
        List<String> params = new ArrayList<String>();
        params.add(getCurrentUser());
        params.add(game.getID());
        CommandData commandData = new CommandData(params, CommandType.POLLGAME);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
        return (Game) results.getData();
    }
}