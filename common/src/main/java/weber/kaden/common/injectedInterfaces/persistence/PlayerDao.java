package weber.kaden.common.injectedInterfaces.persistence;

import java.util.List;

import weber.kaden.common.model.Player;

public interface PlayerDao {
    boolean save(List<Player> players);
    List<Player> getPlayers();
    boolean clear();
}
