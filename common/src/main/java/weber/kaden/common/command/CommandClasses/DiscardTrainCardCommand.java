package weber.kaden.common.command.CommandClasses;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.command.Command;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;

public class DiscardTrainCardCommand implements Command {
    private String gameID;
    private String playerID;
    private TrainCard card;

    public DiscardTrainCardCommand(List<String> params, TrainCard card) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        this.card = card;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(gameID).PlayerDiscardTrainCard(this.playerID, this.card)) {
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
