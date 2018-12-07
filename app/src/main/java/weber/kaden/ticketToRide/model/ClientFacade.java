package weber.kaden.ticketToRide.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.command.CommandData.CommandDataClaimRoute;
import weber.kaden.common.command.CommandData.CommandDataDrawDestinationCards;
import weber.kaden.common.command.CommandData.CommandDataDrawTrainCardFromFaceUp;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Player;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;
import weber.kaden.ticketToRide.serverProxy.ClientCommunicator;
import weber.kaden.ticketToRide.serverProxy.ServerCommunicationInfo;
import weber.kaden.ticketToRide.serverProxy.ServerProxy;
import java.util.UUID;

public class ClientFacade {

    public void login(String username, String password, String serverIP, String serverPort) throws Exception {
        ServerCommunicationInfo.setServerIPAddress(serverIP);
        ServerCommunicationInfo.setServerPort(serverPort);
        ClientCommunicator.getInstance().setServerIP(serverIP);
        ClientCommunicator.getInstance().setServerPort(serverPort);
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.LOGIN);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null) {
        	throw new Exception("Login unsuccessful");
        }
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }

	    Model.getInstance().setCurrentUser(username);
        if (Model.getInstance().getPlayer(username) == null) {
            Model.getInstance().addPlayer(new Player(username,password));
        }
    }

    public void register(String username, String password, String serverIP, String serverPort) throws Exception {
        ServerCommunicationInfo.setServerIPAddress(serverIP);
        ServerCommunicationInfo.setServerPort(serverPort);
        ClientCommunicator.getInstance().setServerIP(serverIP);
        ClientCommunicator.getInstance().setServerPort(serverPort);
        List<String> credentials = new ArrayList<>(Arrays.asList(username, password));
        CommandData commandData = new CommandData(credentials, CommandType.REGISTER);
        //executeLocalCommand(commandData);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (results == null) {
        	throw new Exception("Register unsuccessful");
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

    @Deprecated
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

    public String getCurrentUserID() {
        return Model.getInstance().getCurrentUser();
    }

    public Game getUpdatedGame(Game game) throws Exception{
        List<String> params = new ArrayList<>();
        params.add(getCurrentUserID());
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
    public void setTravelRateByPlayer(String gameId, String playerId, String numPlaces) throws Exception{
        List<String> params = new ArrayList<>((Arrays.asList(gameId, playerId, numPlaces)));
        CommandData commandData = new CommandData(params, CommandType.SETTRAVELERRATE);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }

    }

    public List<DestinationCard> getDealtDestinationCardsForCurrentPlayer() {
        return Model.getInstance().getDealtDestinationCards();
    }
    public List<DestinationCard> getDestinationCardsForTurn(){
        return Model.getInstance().getCurrentGame().getTopOfDestinationCardDeck();
    }

    public boolean playerCanClaimRoute(int number, TrainCardType type) {
        return Model.getInstance().PlayerCanClaimRoute(number, type);
    }

    public void claimRoute(String gameId, String userId, Route route, TrainCardType cardType) throws Exception {
        List<String> params = new ArrayList<>((Arrays.asList(gameId, userId)));
        CommandData commandData = new CommandDataClaimRoute(params, CommandType.CLAIMROUTE, route, cardType);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }
    }

    public int getNumberOfPlayersInGame(){
        return Model.getInstance().getNumberOfPlayersInCurrentGame();
    }

    public void sendDestinationCards(String playerId, String gameId, List<DestinationCard> keptCards, List<DestinationCard> discarded) throws Exception{
        List<String> params = new ArrayList<>((Arrays.asList(gameId, playerId)));
        CommandData commandData = new CommandDataDrawDestinationCards(params, CommandType.DRAWDESTINATIONCARDS,
                keptCards, discarded);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if(!results.success()){
            throw new Exception(results.getErrorInfo());
        }
    }

    public void chooseTrainCardFromFaceUpCards(String gameId, String playerId, int cardIndex) throws Exception {
        List<String> params = new ArrayList<>(Arrays.asList(gameId, playerId));
        List<Object> data = new ArrayList<>();
        data.add(cardIndex);
        CommandData commandData = new CommandDataDrawTrainCardFromFaceUp(params,
                CommandType.DRAWTRAINCARDFROMFACEUP, cardIndex);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }

    public void chooseTrainCardFromDeck(String gameId, String playerId) throws Exception {
        List<String> params = new ArrayList<>(Arrays.asList(gameId, playerId));
        CommandData commandData = new CommandData(params, CommandType.DRAWTRAINCARDFROMDECK);
        Results results = ServerProxy.getInstance().sendCommand(commandData);
        if (!results.success()) {
            throw new Exception(results.getErrorInfo());
        }
    }

    public boolean isMyTurn() {
        return Model.getInstance().isCurrentPlayerTheActivePlayer();
    }

    public void finishTurn() {
       List<String> params = new ArrayList<>();
       params.add(Model.getInstance().getCurrentGame().getID());
       CommandData commandData = new CommandData(params, CommandType.FINISHTURN);
       Results results = ServerProxy.getInstance().sendCommand(commandData);
    }

    public String getCurrentTurnPlayer() {
        return Model.getInstance().getCurrentGame().getCurrentPlayer();
    }

    public List<CommandData> getGameHistory() {
        return Model.getInstance().getGameHistory(Model.getInstance().getCurrentGame().getID());
    }

    public List<TrainCard> getCurrentPlayerTrainCardHand() {
        return Model.getInstance().getPlayerTrainCardHand(Model.getInstance().getCurrentUser());
    }
}