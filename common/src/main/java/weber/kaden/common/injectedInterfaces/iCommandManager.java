package weber.kaden.common.injectedInterfaces;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.model.Game;

public interface iCommandManager {
    
    List<Game> gamesList();
    
    Game getGameByID(String gameID);

    List<CommandData> getLatestCommands(String gameID);
}
