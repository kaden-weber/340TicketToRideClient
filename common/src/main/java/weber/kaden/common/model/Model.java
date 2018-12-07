package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import weber.kaden.common.command.CommandData.CommandData;

public class Model extends Observable {
    private final static Model model = new Model();
    private List<Player> players;
    private List<Game> games;
    private String currentUser;
    private Game currentGame;
    private int deltaValue;
    private int deltaCount;
    private List<CommandData> deltaCommandData;

    public static Model getInstance() {
        return model;
    }

    private Model() {
        this.players = new ArrayList<Player>();
        this.games = new ArrayList<Game>();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
        setChanged();
        notifyObservers(this.games);
    }

    public boolean addPlayer(Player player) {
        if (this.players.contains(player)) {
            return false;
        }
        if (this.players.add(player)) {
            setChanged();
            notifyObservers(this.games);
            return true;
        }
        return false;

    }

    public boolean hasPlayer(Player player) {return this.players.contains(player); }

    public boolean hasPlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(String username) {
        Player toReturn = null;
        for (int i = 0; i < this.players.size(); i++) {
            if (username.equals(this.players.get(i).getID())) {
                toReturn = this.players.get(i);
            }
        }
        return toReturn;
    }

    public boolean removePlayer(Player player) {
        if (!this.players.contains(player)) {
            return false;
        }
        this.removePlayerFromGames(player);
        if (this.players.remove(player)) {
            setChanged();
            notifyObservers(this.games);
            return true;
        }
        return false;
    }

    public boolean addPlayerToGame(Player player, Game game) {
        if (!this.players.contains(player)) {
            return false;
        }
        if (game.getPlayers().size() >=  5 || (game.isSetup() || game.isStarted())) {
            return false;
        }
        if (this.games.get(this.games.indexOf(game)).addPlayer(new Player(player))) {
            setChanged();
            notifyObservers(this.games);
            return true;
        }
        return false;
    }

    public boolean removePlayerFromGame(String playerID, String gameID) {
        if (!this.getGame(gameID).hasPlayer(playerID) || !this.hasPlayer(playerID)) {
            return false;
        }
        if(this.getGame(gameID).removePlayer(playerID)) {
            setChanged();
            notifyObservers(this.games);
            if (this.getGame(gameID).getPlayers().size() == 0) {
                this.removeGame(this.getGame(gameID));
                setChanged();
                notifyObservers(this.games);
            }
            return true;
        }
        return false;
    }

    public boolean removePlayerFromGames(Player player) {
        if (!this.players.contains(player)) {
            return false;
        }
        for (Game game : games) {
            if (game.hasPlayer(player)) {
                game.removePlayer(player);
            }
        }
        setChanged();
        notifyObservers(this.games);
        return true;
    }

    public boolean addGame(Game game) {
        if (this.games.contains(game)) {
            return false;
        }
        if (this.games.add(game)) {
            setChanged();
            notifyObservers(this.games);
            return true;
        }
        return false;
    }

    public boolean removeGame(Game game) {
        if (!this.games.contains(game)) {
            return false;
        }
        if (this.games.remove(game)) {
            setChanged();
            notifyObservers(this.games);
            return true;
        }
        return false;
    }

    public Game getGame(String gameID) {
        Game toReturn = null;
        for (int i = 0; i < this.getGames().size(); i++) {
            if (this.games.get(i).getID().equals(gameID)) {
                toReturn = this.games.get(i);
            }
        }
        return toReturn;
    }

    public Game updateGame(Game game) {
        if (this.currentGame.getID().equals(game.getID())) {
            if (!currentGame.equals(game)) {
                for (int i = 0; i < this.games.size(); i++) {
                    if (this.games.get(i).getID().equals(game.getID())) {
                        this.games.set(i, game);
                    }
                }
                currentGame = game;
                currentGame.updateGameState();
                setChanged();
                notifyObservers(game);
                return game;
            }
        }
        return null;
    }

    public Game startGame(String gameID) {
        Game game = Model.getInstance().getGame(gameID);
        if (game.start()) {
            return game;
        } else {
            return null;
        }
    }

    public Game setUpGame(String gameID) {
        Game game = Model.getInstance().getGame(gameID);
        if (game.setUp()) {
            return game;
        } else {
            return null;
        }
    }

    public boolean PlayerCanClaimRoute(int number, TrainCardType type) {
        return this.currentGame.getPlayer(currentUser).hasTrainCards(number, type);
    }

    public boolean isCurrentPlayerTheActivePlayer() {
        return (this.currentUser.equals(currentGame.getCurrentPlayer()));
    }

    public List<DestinationCard> getDealtDestinationCards() {
        return this.currentGame.getPlayer(currentUser).getDealtDestinationCards();
    }

    public int getNumberOfPlayersInCurrentGame() {
        return this.currentGame.getPlayers().size();
    }

    public void addGameHistoryToGame(String gameID, CommandData history){
        this.getGame(gameID).addHistory(history);
    }

    public List<CommandData> getGameHistory(String gameID) {
        return this.getGame(gameID).getGameHistory();
    }

    public List<TrainCard> getPlayerTrainCardHand(String currentUser) {
        return this.currentGame.getPlayer(currentUser).getTrainCards();
    }

    public void update(CommandData data) {
        this.deltaCount++;
        if (this.deltaCommandData == null) {
            this.deltaCommandData = new ArrayList<CommandData>();
        }
        this.deltaCommandData.add(data);
        if (this.deltaCount == this.deltaValue) {
            this.saveToDb();
        }
    }

    public void saveToDb() {
        this.deltaCount = 0;
        this.deltaCommandData.clear();
    }
}
