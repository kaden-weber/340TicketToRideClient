package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.Model;
import weber.kaden.common.model.TrainCard;

public class DrawTrainCardsCommand implements Command {
    private String gameID;
    private String playerID;
    private List<TrainCard> cards;

    public DrawTrainCardsCommand(List<String> params, List<TrainCard> cards) {
        this.gameID = params.get(0);
        this.playerID = params.get(1);
        this.cards = cards;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(gameID).PlayerDrawTrainCards(playerID, cards)) {
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "DrawTrainCardsCommand failed");
        }
    }
}
