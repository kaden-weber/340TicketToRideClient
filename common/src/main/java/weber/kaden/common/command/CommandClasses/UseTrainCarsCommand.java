package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Model;

@Deprecated
public class UseTrainCarsCommand implements Command {
    private String gameID;
    private String playerID;

    public UseTrainCarsCommand(List<String> params) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(this.gameID).RemoveTrainCarsFromPlayer(playerID)) {
            return new GenericResults(null, true, null);
        } else {
            return new GenericResults(null, false, "Use train cars command failed");
        }
    }

    @Override
    public boolean hasID() {
        return true;
    }
}
