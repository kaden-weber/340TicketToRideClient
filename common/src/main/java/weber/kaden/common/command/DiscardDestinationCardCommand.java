package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;

public class DiscardDestinationCardCommand implements Command {
    private String gameID;
    private String playerID;
    private DestinationCard card;

    public DiscardDestinationCardCommand(List<String> params, DestinationCard card) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        this.card = card;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(gameID).PlayerRemoveDestinationCard(this.playerID, this.card)) {
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "DrawTrainCardFromDeckCommand failed");
        }
    }

    @Override
    public boolean hasID() {
        return false;
    }
}
