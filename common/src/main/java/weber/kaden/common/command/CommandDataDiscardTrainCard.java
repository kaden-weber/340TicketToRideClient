package weber.kaden.common.command;

import java.util.List;

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
