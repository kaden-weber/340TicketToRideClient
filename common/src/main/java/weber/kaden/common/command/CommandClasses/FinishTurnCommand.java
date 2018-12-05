package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Model;


public class FinishTurnCommand implements Command {
    private String gameID;

    public FinishTurnCommand(List<String> params) {
        this.gameID = params.get(0);
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(this.gameID).finishTurn()) {
            return new GenericResults(null, true, null);
        } else {
            return new GenericResults(null, false, "Finish Turn Command failed");
        }
    }

    @Override
    public boolean hasID() {
        return true;
    }
}
