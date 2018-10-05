package weber.kaden.common.command;

import weber.kaden.common.Results;

public interface iCommandManager {
    
    Results gamesList();
    
    Results currentGame(String gameID);
}
