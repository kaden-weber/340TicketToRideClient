package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.GameResults;
import weber.kaden.common.GenericResults;
import weber.kaden.common.Results;
import weber.kaden.common.model.DestinationCard;
import weber.kaden.common.model.Model;

public class DrawDestinationCardsCommand implements Command {
    private String gameID;
    private String playerID;
    private List<DestinationCard> cardsKept;
    private List<DestinationCard> cardsDiscarded;

    public DrawDestinationCardsCommand(String gameID, String playerID, List<DestinationCard> cardsKept, List<DestinationCard> cardsDiscarded) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.cardsKept = cardsKept;
        this.cardsDiscarded = cardsDiscarded;
    }

    @Override
    public Results execute() {
        if (Model.getInstance().getGame(gameID).PlayerDrawDestinationCards(playerID, cardsKept) &&
                Model.getInstance().getGame(gameID).DiscardDestionationCards(cardsDiscarded)
                ) {
            return new GameResults(Model.getInstance().getGame(gameID), true, null);
        } else {
            return new GenericResults(null, false, "Error in Draw Destination Cards Command");
        }
    }
}
