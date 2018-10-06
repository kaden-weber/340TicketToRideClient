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
import weber.kaden.myapplication.ui.LoginPresenter;
import java.util.UUID;

public class ClientFacade {

private LoginPresenter presenter;
    public boolean login(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.LOGIN);
        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }
        return true;
        }
    public boolean register(String username, String password) throws Exception {
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.REGISTER);
        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }
        return true;
    }
    public List<Game> getGames(){
        List<Game> list = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        players.add(new Player("id", "password"));
        list.add(new Game(players, "GAME 1"));
        return list;
    }

    public Results createGame(String username) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(username, UUID.randomUUID().toString())));
        CommandData commandData = new CommandData(params, CommandType.CREATEGAME);
        Command command = CommandFactory.getInstance().getCommand(commandData);
        command.execute();
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
//        List<String> params = new ArrayList<>((Arrays.asList(username, gameID)));
//        CommandData commandData = new CommandData(params, CommandType.LEAVEGAME);
//        Command command = CommandFactory.getInstance().getCommand(commandData);
//        command.execute();
//        Results results = ServerProxy.getInstance().sendCommand(commandData);
//        if(!results.success()) {
//            throw new Exception(results.getErrorInfo());
    }
}