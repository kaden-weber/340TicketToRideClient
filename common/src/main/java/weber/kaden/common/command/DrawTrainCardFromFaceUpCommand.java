package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
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
            return new GameResults(null, true, null);
        }
        else {
            return new GenericResults(null, false, "DrawTrainCardFromFaceUpCommand failed");
        }
    }
}
