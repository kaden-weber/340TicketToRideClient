package weber.kaden.common.command.CommandData;

import java.util.List;

import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.Route;
import weber.kaden.common.model.TrainCard;
import weber.kaden.common.model.TrainCardType;

public class CommandDataClaimRoute extends CommandData {
    private Route route;
    private TrainCardType cardType;

    public CommandDataClaimRoute(List<String> params, CommandType type, Route route, TrainCardType cardType) {
        super(params, type);
        this.route = route;
        this.cardType = cardType;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public TrainCardType getCardType() {
        return cardType;
    }

    public void setCardType(TrainCardType cardType) {
        this.cardType = cardType;
    }
}
