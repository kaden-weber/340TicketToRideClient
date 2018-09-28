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
        return this.players.add(player);
    }

    public boolean removePlayer(Player player) {
        this.removePlayerFromGames(player);
        return this.players.remove(player);
    }

    public boolean addPlayerToGame(Player player, Game game) {
        return this.games.get(this.games.indexOf(game)).addPlayer(player);
    }

    public boolean removePlayerFromGame(Player player, Game game) {
        return this.games.get(this.games.indexOf(game)).removePlayer(player);
    }

    public void removePlayerFromGames(Player player) {
        for (Game game : games) {
            if (game.hasPlayer(player)) {
                game.removePlayer(player);
            }
        }
    }

    public boolean addGame(Game game) {
        return this.games.add(game);
    }

    public boolean removeGame(Game game) {
        return this.games.remove(game);
    }
}
