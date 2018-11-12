package weber.kaden.common.command.CommandData;

import java.util.List;

import weber.kaden.common.command.CommandType;

public class CommandDataDrawTrainCardFromFaceUp extends CommandData {
    private Integer cardIndex;

    public CommandDataDrawTrainCardFromFaceUp(List<String> params, CommandType type, Integer cardIndex) {
        super(params, type);
        this.cardIndex = cardIndex;
    }

    public Integer getCardIndex() {
        return cardIndex;
    }
}
