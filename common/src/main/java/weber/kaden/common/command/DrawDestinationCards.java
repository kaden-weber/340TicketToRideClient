package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.model.DestinationCard;

public class DrawDestinationCards implements Command {
    private String gameID;
    private String playerID;
    private List<DestinationCard> cardsKept;
    private List<DestinationCard> cardsDiscarded;

    public DrawDestinationCards(String gameID, String playerID, List<DestinationCard> cardsKept, List<DestinationCard> cardsDiscarded) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.cardsKept = cardsKept;
        this.cardsDiscarded = cardsDiscarded;
    }

    @Override
    public Results execute() {
        return null;
    }
}
