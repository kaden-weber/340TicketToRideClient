package weber.kaden.common.command;

import java.util.List;

import weber.kaden.common.model.DestinationCard;

public class CommandDataDrawDestinationCards extends CommandData {
    private List<DestinationCard> cardsKept;
    private List<DestinationCard> cardsDiscarded;

    public CommandDataDrawDestinationCards(List<String> params, CommandType type, List<DestinationCard> cardsKept, List<DestinationCard> cardsDiscarded) {
        super(params, type);
        this.cardsKept = cardsKept;
        this.cardsDiscarded = cardsDiscarded;
    }

    public List<DestinationCard> getCardsKept() {
        return cardsKept;
    }

    public List<DestinationCard> getCardsDiscarded() {
        return cardsDiscarded;
    }
}
