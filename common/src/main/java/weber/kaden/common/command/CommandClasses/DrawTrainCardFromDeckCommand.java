package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.results.GameResults;
import weber.kaden.common.results.GenericResults;
import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Model;

public class DrawTrainCardFromDeckCommand implements Command {
    private String gameID;
    private String playerID;

    public DrawTrainCardFromDeckCommand(List<String> params) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(gameID).PlayerDrawTrainCardFromDeck(playerID)) {
            //Model.getInstance().getGame(gameID).finishTurn();
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "DrawTrainCardFromDeckCommand failed");
        }
    }

    @Override
    public boolean hasID() {
        return true;
    }
}
