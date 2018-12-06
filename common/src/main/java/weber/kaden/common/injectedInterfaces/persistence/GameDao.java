package weber.kaden.common.injectedInterfaces.persistence;

import java.util.List;

import weber.kaden.common.model.Game;

public interface GameDao {
    boolean save(List<Game> games);
    List<Game> getGames();
}
