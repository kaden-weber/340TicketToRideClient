package weber.kaden.common.command;

import java.util.List;

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
