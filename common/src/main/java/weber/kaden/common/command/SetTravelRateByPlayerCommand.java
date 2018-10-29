package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Model;

public class SetTravelRateByPlayerCommand implements Command {
    private String playerID;
    private String gameID;
    private int TravelRate;

    public SetTravelRateByPlayerCommand(List<String> params, int travelRate) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        TravelRate = travelRate;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(this.gameID).setPlayerTravelRate(this.playerID, this.TravelRate)) {
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "SetTravelRateByPlayerCommand failed");
        }
    }
}
