package weber.kaden.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import weber.kaden.common.Results;

public class Game {
    private List<Player> players;
    private String ID;

    public Game() {
        this.players = new ArrayList<Player>();
        this.ID = UUID.randomUUID().toString();
    }

    public Game(List<Player> players, String ID) {
        this.players = players;
        this.ID = ID;
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

    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
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
        return new Results(null, true, null);
    }
}
