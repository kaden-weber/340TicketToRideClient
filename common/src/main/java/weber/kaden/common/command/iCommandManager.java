package weber.kaden.common.command;

import java.util.List;
import weber.kaden.common.model.Game;

public interface iCommandManager {
    
    List<Game> gamesList();
    
    Game currentGame(String gameID);
}
