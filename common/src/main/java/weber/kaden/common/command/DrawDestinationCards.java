package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.Results;
import weber.kaden.common.model.DestinationCard;

public class DrawDestinationCards implements Command {
    private String gameID;
    private String playerID;
    private List<DestinationCard> cardsKept;
    private List<DestinationCard> cardsDiscarded;

    public DrawDestinationCards(List<Object> params) {
        this.gameID = (String)params.get(0);
        this.playerID = (String)params.get(1);
        this.cardsKept = (List<DestinationCard>)params.get(2);
        this.cardsDiscarded = (List<DestinationCard>)params.get(3);
    }

    @Override
    public Results execute() {
        return null;
    }
}
