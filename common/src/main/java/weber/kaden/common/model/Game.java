package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import weber.kaden.common.GameResults;
import weber.kaden.common.Results;

public class Game {
    private List<Player> players;
    private String ID;
    private String gameName;
    private boolean started;

    public Game() {
        this.players = new ArrayList<Player>();
        this.ID = UUID.randomUUID().toString();
        this.started = false;
    }

    public Game(List<Player> players, String ID, String gameName) {
        this.players = players;
        this.ID = ID;
        this.gameName = gameName;
        this.started = false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean addPlayer(Player player) {
        return this.players.add(player);
    }

    public boolean removePlayer(Player player) {
        return this.players.remove(player);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean removePlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                this.players.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    public boolean hasPlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(String playerID) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getID().equals(playerID)) {
                return this.players.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return ID == game.ID &&
                Objects.equals(players, game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, ID);
    }

    public Results start() {
        if (this.getPlayers().size() < 2 || this.getPlayers().size() > 5) {
            return new GameResults(null, false, "incorrect number of players in game");
        }
        setStarted(true);
        return new GameResults(null, true, null);
    }
}
