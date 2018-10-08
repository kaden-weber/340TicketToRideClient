package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {
    private static Model model = null;
    private List<Player> players;
    private List<Game> games;
    private String currentUser;

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
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
    }

    public boolean addPlayer(Player player) {
        if (this.players.contains(player)) {
            return false;
        }
        if (this.players.add(player)) {
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
            notifyObservers(this.games);
            return true;
        }
        return false;
    }

    public boolean addPlayerToGame(Player player, Game game) {
        if (!this.players.contains(player)) {
            return false;
        }
        if (game.hasPlayer(player)) {
            return false;
        }
        if (game.getPlayers().size() >=  5) {
            return false;
        }
        if (this.games.get(this.games.indexOf(game)).addPlayer(player)) {
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
            notifyObservers(this.games);
            if (this.getGame(gameID).getPlayers().size() == 0) {
                this.removeGame(this.getGame(gameID));
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
        notifyObservers(this.games);
        return true;
    }

    public boolean addGame(Game game) {
        if (this.games.contains(game)) {
            return false;
        }
        if (this.games.add(game)) {
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
}
