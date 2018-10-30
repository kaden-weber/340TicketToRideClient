package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Game;
import weber.kaden.common.model.Model;

public class LeaveGameCommand implements Command {

    private String playerID;
    private String gameID;

    public LeaveGameCommand(List<String> params) {
        this.playerID = params.get(0);
        this.gameID = params.get(0);
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

    @Override
    public boolean hasID() {
        return false;
    }
}
