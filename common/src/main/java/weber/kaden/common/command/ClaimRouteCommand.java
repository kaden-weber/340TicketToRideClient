package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.Route;

public class ClaimRouteCommand implements Command {
    private Route routeClaimed;
    private String gameID;
    private String playerID;

    public ClaimRouteCommand(List<String> params, Route routeClaimed) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        this.routeClaimed = routeClaimed;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(this.gameID).PlayerClaimRoute(this.playerID, this.routeClaimed)) {
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "ClaimRouteCommand failed");
        }
    }
}
