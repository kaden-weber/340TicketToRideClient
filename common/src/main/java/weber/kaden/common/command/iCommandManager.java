package weber.kaden.common.command;

import java.util.List;
import weber.kaden.common.model.Game;

public interface iCommandManager {
    
    List<Game> gamesList();
    
    Game getGameByID(String gameID);

    List<CommandData> getLatestCommands(String gameID, String lastID);
}
