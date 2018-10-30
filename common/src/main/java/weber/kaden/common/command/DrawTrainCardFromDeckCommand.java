package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;

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
