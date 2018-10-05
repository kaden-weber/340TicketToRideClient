package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {
    private static Model model = null;
    private List<Player> players;
    private List<Game> games;

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

    public boolean removePlayerFromGame(Player player, Game game) {
        if (!game.hasPlayer(player) || !this.players.contains(player)) {
            return false;
        }
        if(this.games.get(this.games.indexOf(game)).removePlayer(player)) {
            notifyObservers(this.games);
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
