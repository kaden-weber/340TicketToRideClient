package weber.kaden.common.command.CommandData;

import java.util.List;

import weber.kaden.common.command.CommandType;
import weber.kaden.common.model.TrainCard;

public class CommandDataDiscardTrainCard extends CommandData {
    private TrainCard card;

    public CommandDataDiscardTrainCard(List<String> params, CommandType type, TrainCard card) {
        super(params, type);
        this.card = card;
    }

    public TrainCard getCard() {
        return card;
    }
}
