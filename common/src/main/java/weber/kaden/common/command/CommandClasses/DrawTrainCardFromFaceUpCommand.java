package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.results.GameResults;
import weber.kaden.common.results.GenericResults;
import weber.kaden.common.results.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Model;

public class DrawTrainCardFromFaceUpCommand implements Command {
    private String gameID;
    private String playerID;
    private Integer cardIndex;

    public DrawTrainCardFromFaceUpCommand(List<String> params, Integer cardIndex) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        this.cardIndex = cardIndex;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(gameID).PlayerDrawTrainCardFromFaceUp(this.playerID, this.cardIndex)) {
            //Model.getInstance().getGame(gameID).finishTurn();
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "DrawTrainCardFromFaceUpCommand failed");
        }
    }

    @Override
    public boolean hasID() {
        return true;
    }
}
