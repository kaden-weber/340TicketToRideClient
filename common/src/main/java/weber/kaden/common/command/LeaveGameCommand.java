package weber.kaden.common.command;

import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;

public class LeaveGameCommand implements Command {

    private String playerID;
    private String gameID;

    public LeaveGameCommand(String playerID, String gameID) {
        this.playerID = playerID;
        this.gameID = gameID;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().removePlayerFromGame(playerID, gameID)) {
            return new GenericResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "Player not removed from game");
        }
    }
}
