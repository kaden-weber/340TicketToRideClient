package weber.kaden.common.injectedInterfaces.persistence;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Player;

public interface DaoFactory {
    boolean saveUsers(List<Player> users);
    boolean saveGames(List<Game> games);
    boolean clearCommandDeltas();

    List<Player> getUsers();
    List<Game> getGames();
    List<CommandData> getCommands();

    boolean addCommandData(CommandData commandData);
    boolean clear();
}
